package com.dkohut.iconomi.common.interfaces;

import java.util.List;

import com.dkohut.iconomi.common.entity.TransferEvent;

public interface ITokenController {

	void setWeb3j();
	
	void setCredentials();
	
	void loadContract();
	
	boolean loadTransactions();
	
	List<TransferEvent> getTransferEventsList();
}