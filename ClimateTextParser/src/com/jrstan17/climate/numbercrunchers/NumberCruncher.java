package com.jrstan17.climate.numbercrunchers;
import java.text.DecimalFormat;

import org.odftoolkit.simple.SpreadsheetDocument;

public abstract class NumberCruncher {

	public static final String DATE_FORMAT_STR = "MMM d";
	public static final String NO_DECIMAL_FORMAT_STR = "0";
	public static final String ONE_DECIMAL_FORMAT_STR = "0.0";
	public static final String TWO_DECIMAL_FORMAT_STR = "0.00";
	
	public static final DecimalFormat DATE_FORMAT = new DecimalFormat(DATE_FORMAT_STR);
	public static final DecimalFormat NO_DECIMAL_FORMAT = new DecimalFormat(NO_DECIMAL_FORMAT_STR);
	public static final DecimalFormat ONE_DECIMAL_FORMAT = new DecimalFormat(ONE_DECIMAL_FORMAT_STR);
	public static final DecimalFormat TWO_DECIMAL_FORMAT = new DecimalFormat(TWO_DECIMAL_FORMAT_STR);

	public static final int DAYS_IN_LEAP_YEAR = 366;
	protected String[][] dataArray;
	protected SpreadsheetDocument workbook;

	public abstract void calculate();

	public abstract void fillSpreadsheet();

	public abstract void formatSpreadsheet();

	public abstract void outputToFile();

	protected int[] initialize(int[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = 999;
		}

		return numbers;
	}

	protected double[] initialize(double[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = 999.99;
		}

		return numbers;
	}
}
