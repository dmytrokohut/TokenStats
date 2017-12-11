package com.dkohut.iconomi;

import com.dkohut.iconomi.controller.TokenController;

public class Main {	
	
	public static void main(String[] args) {		
		System.out.println(greeting());

		TokenController controller = new TokenController();
		controller.setWeb3j();
		controller.setCredentials();
		controller.loadContract();
		controller.loadTransactions();
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
