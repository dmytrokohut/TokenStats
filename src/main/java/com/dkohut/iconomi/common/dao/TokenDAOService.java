package com.dkohut.iconomi.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jtemplate.sql.Parameters;

import com.dkohut.iconomi.common.entity.TransferEvent;
import com.dkohut.iconomi.common.interfaces.ITokenDAOService;
import java.sql.Statement;

public class TokenDAOService implements ITokenDAOService {
	
	private static final String QUERY_SELECT_ALL = 
			"SELECT id, receiver, sender, value, block_number, transaction_hash, contract_address, creation_date " +
			"FROM transfer_events";
	private static final String QUERY_INSERT =
			"INSERT INTO transfer_events(receiver, sender, value, block_number, transaction_hash, contract_address, creation_date) " +
			"VALUES(:receiver, :sender, :value, :blockNumber, :transactionHash, :contractAddress, :creationDate)";
	
	private static final String URL = "jdbc:mysql://localhost:3306/iconomi_token";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final Logger logger = Logger.getLogger(TokenDAOService.class.getName());
	
	private static Connection connection;	
	
	
	public TokenDAOService() {
		setConnection();
	}
	
	private static void setConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "ERROR! Connection to DB failed\n" + e.getMessage());
			e.getStackTrace();
		}
	}
	
	@Override
	public List<TransferEvent> getAll() {
		try {
			List<TransferEvent> transferEventsList = new ArrayList<>();
			PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_ALL);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				transferEventsList.add(TransferEvent.getTransferEvent(resultSet));
			}
			return transferEventsList; 
			
		} catch(SQLException e) {
			logger.log(Level.SEVERE, "Error during getting data from DB\n" + e.getMessage());
			e.getStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Integer insert(TransferEvent transferEvent) {
		try {
			Parameters parameters = Parameters.parse(QUERY_INSERT);
			PreparedStatement statement = connection.prepareStatement(parameters.getSQL(), Statement.RETURN_GENERATED_KEYS);
			
			parameters.put("receiver", transferEvent.getReceiver());
			parameters.put("sender", transferEvent.getSender());
			parameters.put("value", transferEvent.getValue());
			parameters.put("blockNumber", transferEvent.getBlockNumber());
			parameters.put("transactionHash", transferEvent.getTransactionHash());
			parameters.put("contractAddress", transferEvent.getContractAddress());
			parameters.put("creationDate", transferEvent.getCreationDate());
			parameters.apply(statement);
			statement.execute();
			
			ResultSet generatedId = statement.getGeneratedKeys();
			generatedId.next();
			
			return generatedId.getInt(1);
			
		} catch(SQLException e) {
			logger.log(Level.SEVERE, "ERROR! Transfer event was not inserted\n" + e.getMessage());
			e.getStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
}
