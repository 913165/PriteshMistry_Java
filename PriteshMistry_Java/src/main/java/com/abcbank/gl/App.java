package com.abcbank.gl;

public class App {
	public static void main(String[] args) throws Exception {
		EndOfDayProcessor processor = new EndOfDayProcessor();
		String positionPath = "D://gls//Input_StartOfDay_Positions.txt";
		String transactionPath = "D://gls//Input_Transactions.txt";
		String endOfDayPositionPath = "D://gls//out.txt";
		processor.process(positionPath, transactionPath, endOfDayPositionPath);
	}
}
