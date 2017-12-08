package com.dkohut.iconomi.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;
import org.web3j.protocol.http.HttpService;

import com.dkohut.iconomi.common.entity.TransferEvent;
import com.dkohut.iconomi.wrappers.IconomiToken;

public class TokenController {

	private static final String CONTRACT_ADDRESS = "0x888666CA69E0f178DED6D75b5726Cee99A87D698";
	private static final String PASSWORD = "";
	private static final String PATH_TO_WALLET_FILE = "";
	private static final Logger logger = Logger.getLogger(TokenController.class.getName());
	
	private static long SEPTEMBER_START_BLOCK = 4221144L;
	private static long SEPTEMBER_END_BLOCK = 4325717L;
	
	private Web3j web3j;
	private Credentials credentials;
	private IconomiToken token;
	private List<TransferEvent> transferEventsList = new ArrayList<>();
	
	
	public boolean setWeb3j() {
		web3j = Web3j.build(new HttpService("http://localhost:8545"));
		return true;
	}
	
	public boolean setCredentials() {
		try {
			credentials = WalletUtils.loadCredentials(PASSWORD, PATH_TO_WALLET_FILE);
			return true;
		} catch (IOException | CipherException e) {
			logger.log(Level.SEVERE, "ERROR! Credentials was not loaded !\n" + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	
	public boolean loadContract() {
		token = IconomiToken.load(CONTRACT_ADDRESS, web3j, credentials, IconomiToken.GAS_PRICE, IconomiToken.GAS_LIMIT);
		return true;
	}
	
	public void loadTransactions() {
		web3j.replayBlocksObservable(
				new DefaultBlockParameterNumber(SEPTEMBER_START_BLOCK), 
				new DefaultBlockParameterNumber(SEPTEMBER_END_BLOCK), 
				true)
			.subscribe(block -> {
				block.getBlock()
					.getTransactions()
					.stream()
					.map(transaction -> TransactionObject.class.cast(transaction))
					.filter(transaction -> {
						return transaction.getTo().equals(CONTRACT_ADDRESS) || transaction.getFrom().equals(CONTRACT_ADDRESS);
					})
					.forEach(transaction -> {
						logger.info("Block number = " + transaction.getBlockNumber().intValue() + 
								"\t\tBlock hash = " + transaction.getBlockHash());
						web3j.ethGetTransactionReceipt(transaction.getHash())
							.sendAsync()
							.thenAccept(transactionReceipt -> {
								logger.info("Contract address = " + transactionReceipt.getResult().getContractAddress());
								token.getTransferEvents(transactionReceipt.getResult())
									.forEach(event -> {
										TransferEvent transferEvent = new TransferEvent();
										transferEvent.setReceiver(event._to.toString());
										transferEvent.setSender(event._from.toString());
										transferEvent.setValue(event._value.getValue().longValue());
										transferEvent.setBlockNumber(transactionReceipt.getResult().getBlockNumber().longValue());
										transferEvent.setTransactionHash(transactionReceipt.getResult().getTransactionHash());
										transferEvent.setContractAddress(transactionReceipt.getResult().getContractAddress());
										transferEvent.setCreationDate(new Timestamp(block.getBlock().getTimestamp().longValue()));
										transferEventsList.add(transferEvent);
									});
								
							});
					});
			});
	}
	
}