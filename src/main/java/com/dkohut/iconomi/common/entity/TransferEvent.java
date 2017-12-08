package com.dkohut.iconomi.common.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransferEvent {
	
	private Integer id;
	private String receiver;
	private String sender;
	private Integer value;
	private Integer blockNumber;
	private String transactionHash;
	private String contractAddress;
	private Timestamp creationDate;
	
	
	public TransferEvent() {
		// default constructor 
	}
	
	public TransferEvent(
			Integer id,
			String receiver, 
			String sender, 
			Integer value, 
			Integer blockNumber, 
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(Integer blockNumber) {
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
				resultSet.getInt("id"), 
				resultSet.getString("receiver"), 
				resultSet.getString("sender"),
				resultSet.getInt("value"), 
				resultSet.getInt("block_number"), 
				resultSet.getString("transaction_hash"),
				resultSet.getString("contract_address"), 
				resultSet.getTimestamp("creation_date")
				);
	}
	
}