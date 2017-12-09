package com.dkohut.iconomi;

import com.dkohut.iconomi.common.dao.TokenDAOService;
import com.dkohut.iconomi.common.statistics.FileGenerator;
import com.dkohut.iconomi.controller.GeneralController;
import com.dkohut.iconomi.controller.TokenController;

public class Main {
	
	public static void main(String[] args) {		
		System.out.println(greeting());

		GeneralController controller = new GeneralController(
				new TokenController(),
				new FileGenerator(),
				new TokenDAOService()
				); 
		
		controller.loadTransactions();
		controller.uploadTransactions();
		controller.generateFile();
	}
	
	private static String greeting() {
		return 
			"####################################################################\n" +
			"##                                                                ##\n" +
			"##     @   @@@@@@@   @@@@@@   @      @   @@@@@@   @      @  @     ##\n" +
			"##     @  @         @      @  @@     @  @      @  @@    @@  @     ##\n" +
			"##     @  @         @      @  @ @    @  @      @  @ @  @ @  @     ##\n" +
			"##     @  @         @      @  @  @   @  @      @  @  @@  @  @     ##\n" +
			"##     @  @         @      @  @   @  @  @      @  @      @  @     ##\n" +
			"##     @  @         @      @  @    @ @  @      @  @      @  @     ##\n" +
			"##     @  @         @      @  @     @@  @      @  @      @  @     ##\n" +
			"##     @   @@@@@@@   @@@@@@   @      @   @@@@@@   @      @  @     ##\n" +
			"##                                                                ##\n" +
			"####################################################################\n";
	}

}
