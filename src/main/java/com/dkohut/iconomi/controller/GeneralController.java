package com.dkohut.iconomi.controller;

import java.util.List;
import java.util.logging.Logger;

import com.dkohut.iconomi.common.dao.TokenDAOService;
import com.dkohut.iconomi.common.entity.TransferEvent;
import com.dkohut.iconomi.common.interfaces.IFileGenerator;
import com.dkohut.iconomi.common.interfaces.ITokenController;
import com.dkohut.iconomi.common.interfaces.ITokenDAOService;

public class GeneralController {
	
	private static final Logger logger = Logger.getLogger(GeneralController.class.getName());

	private ITokenController tokenController;
	private IFileGenerator fileGenerator;
	private ITokenDAOService tokenDAOService;

	
	public GeneralController(
			ITokenController tokenController, 
			IFileGenerator fileGenerator, 
			ITokenDAOService tokenDAOService
	) 	{
		this.tokenController = tokenController;
		this.fileGenerator = fileGenerator;
		this.tokenDAOService = tokenDAOService;
	}
	
	public void loadTransactions() {
		tokenController.setWeb3j();
		tokenController.setCredentials();
		tokenController.loadContract();
		
		boolean response = tokenController.loadTransactions();
		if(response == true) {
			logger.info("Transactions loaded.");
		}
	}
	
	public void uploadTransactions() {
		TokenDAOService.setConnection();
		List<TransferEvent> transferEventsList = tokenController.getTransferEventsList();
		transferEventsList.forEach(transferEvent -> {
			tokenDAOService.insert(transferEvent);
		});
	}
	
	public void generateFile() {		
		boolean response = fileGenerator.generateFile(tokenDAOService.getAll());
		if(response == true) {
			logger.info("File generated.");
		}
	}
	
}
