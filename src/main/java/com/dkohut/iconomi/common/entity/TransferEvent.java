package com.dkohut.iconomi.common.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransferEvent {
	
	private Long id;
	private String receiver;
	private String sender;
	private Long value;
	private Long blockNumber;
	private String transactionHash;
	private String contractAddress;
	private Timestamp creationDate;
	
	
	public TransferEvent() {
		// default constructor 
	}
	
	public TransferEvent(
			Long id,
			String receiver, 
			String sender, 
			Long value, 
			Long blockNumber, 
			String transactionHash, 
			String contractAddress, 
			Timestamp creationDate 			
	)	{		
		this.id = id;
		this.receiver = receiver;
		this.sender = sender;
		this.value = value;
		this.blockNumber = blockNumber;
		this.transactionHash = transactionHash;
		this.contractAddress = contractAddress;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Long getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(Long blockNumber) {
		this.blockNumber = blockNumber;
	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public static TransferEvent getTransferEvent(ResultSet resultSet) throws SQLException {
		return new TransferEvent(
				resultSet.getLong("id"), 
				resultSet.getString("receiver"), 
				resultSet.getString("sender"),
				resultSet.getLong("value"), 
				resultSet.getLong("block_number"), 
				resultSet.getString("transaction_hash"),
				resultSet.getString("contract_address"), 
				resultSet.getTimestamp("creation_date")
				);
	}
	
}