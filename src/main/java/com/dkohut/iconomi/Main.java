package com.dkohut.iconomi;

public class Main {
	
	public static void main(String[] args) {		
		System.out.println(greeting());	
		exit();
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
	
	private static void exit() {
		Runtime.getRuntime().exit(0);
	}	

}
