package com.dkohut.iconomi.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;
import org.web3j.protocol.http.HttpService;

import com.dkohut.iconomi.common.dao.TokenDAOService;
import com.dkohut.iconomi.common.entity.TransferEvent;
import com.dkohut.iconomi.common.interfaces.ITokenController;
import com.dkohut.iconomi.common.statistics.FileGenerator;
import com.dkohut.iconomi.wrappers.IconomiToken;

public class TokenController implements ITokenController {
	
	private static final String HOST = "http://localhost:8545";
	private static final String CONTRACT_ADDRESS = "0x888666CA69E0f178DED6D75b5726Cee99A87D698".toLowerCase();
	private static final String PASSWORD = "";
	private static final String PATH_TO_WALLET_FILE = "";
	private static final Logger logger = Logger.getLogger(TokenController.class.getName());
	
	private static final long FIRST_BLOCK = 4224657L;
	private static final long LAST_BLOCK = 4725330L;
	
	private Web3j web3j;
	private Credentials credentials;
	private IconomiToken token;	
	
	private static TokenDAOService tokenDAOService = new TokenDAOService();
	
	
	public void setWeb3j() {
		web3j = Web3j.build(new HttpService(HOST));
	}
	
	public void setCredentials() {
		try {
			credentials = WalletUtils.loadCredentials(PASSWORD, PATH_TO_WALLET_FILE);
		} catch (IOException | CipherException e) {
			logger.log(Level.SEVERE, "ERROR! Credentials was not loaded !\n" + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	
	public void loadContract() {
		token = IconomiToken.load(CONTRACT_ADDRESS, web3j, credentials,	IconomiToken.GAS_PRICE, IconomiToken.GAS_LIMIT);
	}
	
	public void loadTransactions() {
		logger.info("Start of transactions downloading");
		
		web3j.replayBlocksObservable(
				new DefaultBlockParameterNumber(FIRST_BLOCK), 
				new DefaultBlockParameterNumber(LAST_BLOCK),
				true)
			.doOnError(TokenController::logError)
			.doOnCompleted(TokenController::generateFile)
			.subscribe(block -> {
				block.getBlock()
					.getTransactions()
					.stream()
					.map(transaction -> TransactionObject.class.cast(transaction))
					.filter(transaction -> CONTRACT_ADDRESS.equals(transaction.getTo()) || CONTRACT_ADDRESS.equals(transaction.getFrom()))
					.forEach(transaction -> {
						logger.info("Remain blocks: " + (LAST_BLOCK - transaction.getBlockNumber().longValue()));
						web3j.ethGetTransactionReceipt(transaction.getHash())
							.sendAsync()
							.thenAccept(transactionReceipt -> {
								token.getTransferEvents(transactionReceipt.getResult())
									.forEach(event -> {
										transactionReceipt.getResult().setContractAddress(CONTRACT_ADDRESS);
										TransferEvent transferEvent = new TransferEvent();
										transferEvent.setReceiver(event._to.getValue().toString());
										transferEvent.setSender(event._from.getValue().toString());
										transferEvent.setValue(event._value.getValue().doubleValue());
										transferEvent.setBlockNumber(transactionReceipt.getResult().getBlockNumber().longValue());
										transferEvent.setTransactionHash(transactionReceipt.getResult().getTransactionHash());
										transferEvent.setContractAddress(transactionReceipt.getResult().getContractAddress());
										transferEvent.setCreationDate(new Timestamp(block.getBlock().getTimestamp().longValue() * 1000));
										tokenDAOService.insert(transferEvent);
									});
							});
					});
			});
	}
	
	public static void generateFile() {	
		FileGenerator fileGenerator = new FileGenerator();
		boolean response = fileGenerator.generateFile(tokenDAOService.getAll());
		
		if(response == true) {
			logger.info("File generated.");
		}
	}
	
	public static void logError(Throwable throwable) {
		logger.log(Level.SEVERE, "Error during transactions loading\n" + throwable.getMessage());
		throwable.getStackTrace();
	}	
	
}
