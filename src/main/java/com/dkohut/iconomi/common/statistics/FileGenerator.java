package com.dkohut.iconomi.common.statistics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dkohut.iconomi.common.entity.TransferEvent;
import com.dkohut.iconomi.common.interfaces.IFileGenerator;

public class FileGenerator implements IFileGenerator {

	private static final Logger logger = Logger.getLogger(FileGenerator.class.getName());
	
	private XSSFWorkbook workbook = new XSSFWorkbook();
	private int rowCount = 0;
	
	public boolean generateFile(List<TransferEvent> transferEventsList) {
		
		try (FileOutputStream out = new FileOutputStream(new File("ICONOMI.xls"))) {			
			XSSFSheet sheet = workbook.createSheet("Total Info");			
						
			initializeColumns(sheet.createRow(rowCount++));
			
			for(TransferEvent transferEvent : transferEventsList) {
				Row row = sheet.createRow(rowCount++);
				writeRow(transferEvent, row);				
			}
			
			workbook.write(out);			
			return true;
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "ERROR! File generating was failed\n" + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void initializeColumns(Row row) {
		Cell cell = row.createCell(0);
		cell.setCellValue("Receiver");
		
		cell = row.createCell(1);
		cell.setCellValue("Sender");
		
		cell = row.createCell(2);
		cell.setCellValue("Value");
		
		cell = row.createCell(3);
		cell.setCellValue("Block Number");
		
		cell = row.createCell(4);
		cell.setCellValue("Transaction Hash");
		
		cell = row.createCell(5);
		cell.setCellValue("Contract Address");
		
		cell = row.createCell(6);
		cell.setCellValue("Creation Date");
	}
	
	private void writeRow(TransferEvent transferEvent, Row row) {
		Cell cell = row.createCell(0);
		cell.setCellValue(transferEvent.getReceiver());
		
		cell = row.createCell(1);
		cell.setCellValue(transferEvent.getSender());
		
		cell = row.createCell(2);
		cell.setCellValue(transferEvent.getValue());
		
		cell = row.createCell(3);
		cell.setCellValue(transferEvent.getBlockNumber());
		
		cell = row.createCell(4);
		cell.setCellValue(transferEvent.getTransactionHash());
		
		cell = row.createCell(5);
		cell.setCellValue(transferEvent.getContractAddress());
		
		cell = row.createCell(6);
		cell.setCellValue(transferEvent.getCreationDate());
	}
	
}
