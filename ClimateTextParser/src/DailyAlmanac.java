import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.odftoolkit.odfdom.type.Color;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.style.Border;
import org.odftoolkit.simple.style.Font;
import org.odftoolkit.simple.style.StyleTypeDefinitions.CellBordersType;
import org.odftoolkit.simple.style.StyleTypeDefinitions.FontStyle;
import org.odftoolkit.simple.style.StyleTypeDefinitions.HorizontalAlignmentType;
import org.odftoolkit.simple.style.StyleTypeDefinitions.SupportedLinearMeasure;
import org.odftoolkit.simple.style.StyleTypeDefinitions.VerticalAlignmentType;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.CellRange;
import org.odftoolkit.simple.table.Column;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

public class DailyAlmanac extends NumberCruncher {

	public static final int ROWS_FOR_OTHER_DATA = 5;
	public static final int COLUMNS = 5;
	public static final int ENTRY_ROW_START = 3;
	public static final int NUM_STAT_ROWS = 4;

	private GregorianCalendar date = new GregorianCalendar();
	private int statRowStart = 0;
	private ClimateDataCreator cdc;
	private ClimateData cd;
	private Entry entry;
	private Table sheet = null;

	public DailyAlmanac() {
		cdc = new ClimateDataCreator();
		cd = cdc.getClimateData();
	}

	@Override
	public void calculate() {
		fillSpreadsheet();
//		formatSpreadsheet();
		outputToFile();
	}

	@Override
	public void fillSpreadsheet() {
		entry = cd.getEntry(date);

		try {
			workbook = SpreadsheetDocument.newSpreadsheetDocument();
			sheet = workbook.getSheetByIndex(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, YYYY");		
		
		StatisticFactory sf = new StatisticFactory();
		GregorianCalendar start = new GregorianCalendar(1, Calendar.JANUARY, 1);
		GregorianCalendar stop = new GregorianCalendar(9999, Calendar.DECEMBER, 31);
		Entry extreme;
		int row = 1;

		Cell currentCell = sheet.getCellByPosition(0, row);
		String title = "Ithaca Cornell University, NY - " + sdf.format(date.getTime());
		currentCell.setStringValue(title);

		row++; // 2
		
		String[] colHeaders = new String[] { "", "Observed", "Normal", "Record Highest", "Record Lowest" };
		for (int col = 0; col < COLUMNS; col++) {
			currentCell = sheet.getCellByPosition(col, row);
			currentCell.setStringValue(colHeaders[col]);
		}

		row++; // 3
		
		currentCell = sheet.getCellByPosition(0, row);
		currentCell.setStringValue("Daily Data");
		
		row++; 

		sheet.getCellByPosition(0, row).setStringValue("Maximum Temperature");
		Cell cell = sheet.getCellByPosition(1, row);
		if (entry.maxTemp() != null) {
			cell.setDoubleValue(entry.maxTemp());

			cell = sheet.getCellByPosition(2, row);
			cell.setDoubleValue(sf.getNormalForDate(date, StatIndex.MAX_TEMP));

			extreme = sf.getExtremeForDate(date, StatIndex.MAX_TEMP, true);
			cell = sheet.getCellByPosition(3, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.maxTemp()) + " in " + extreme.year());
			
			extreme = sf.getExtremeForDate(date, StatIndex.MAX_TEMP, false);
			cell = sheet.getCellByPosition(4, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.maxTemp()) + " in " + extreme.year());
		}
		
		row++; 
		
		sheet.getCellByPosition(0, row).setStringValue("Minimum Temperature");
		cell = sheet.getCellByPosition(1, row);
		if (entry.minTemp() != null) {
			cell.setDoubleValue(entry.minTemp());

			cell = sheet.getCellByPosition(2, row);
			cell.setDoubleValue(sf.getNormalForDate(date, StatIndex.MIN_TEMP));

			extreme = sf.getExtremeForDate(date, StatIndex.MIN_TEMP, true);
			cell = sheet.getCellByPosition(3, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.minTemp()) + " in " + extreme.year());
			
			extreme = sf.getExtremeForDate(date, StatIndex.MIN_TEMP, false);
			cell = sheet.getCellByPosition(4, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.minTemp()) + " in " + extreme.year());
		}
		
		row++;
		
		sheet.getCellByPosition(0, row).setStringValue("Average Temperature");
		cell = sheet.getCellByPosition(1, row);
		if (entry.avgTemp() != null) {
			cell.setDoubleValue(entry.avgTemp());

			cell = sheet.getCellByPosition(2, row);
			cell.setDoubleValue(sf.getNormalForDate(date, StatIndex.AVG_TEMP));

			extreme = sf.getExtremeForDate(date, StatIndex.AVG_TEMP, true);
			cell = sheet.getCellByPosition(3, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.avgTemp()) + " in " + extreme.year());
			
			extreme = sf.getExtremeForDate(date, StatIndex.AVG_TEMP, false);
			cell = sheet.getCellByPosition(4, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.avgTemp()) + " in " + extreme.year());
		}
		
		row++; 
		
		sheet.getCellByPosition(0, row).setStringValue("Precipitation");
		cell = sheet.getCellByPosition(1, row);
		if (entry.precip() != null) {
			cell.setDoubleValue(entry.precip());

			cell = sheet.getCellByPosition(2, row);
			cell.setDoubleValue(sf.getNormalForDate(date, StatIndex.PRECIP));

			extreme = sf.getExtremeForDate(date, StatIndex.PRECIP, true);
			cell = sheet.getCellByPosition(3, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.precip()) + " in " + extreme.year());
			
			extreme = sf.getExtremeForDate(date, StatIndex.PRECIP, false);
			cell = sheet.getCellByPosition(4, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.precip()) + " in " + extreme.year());
		}
		
		row++;
		
		sheet.getCellByPosition(0, row).setStringValue("Snowfall");
		cell = sheet.getCellByPosition(1, row);
		if (entry.snowfall() != null) {
			cell.setDoubleValue(entry.snowfall());

			cell = sheet.getCellByPosition(2, row);
			cell.setDoubleValue(sf.getNormalForDate(date, StatIndex.SNOWFALL));

			extreme = sf.getExtremeForDate(date, StatIndex.SNOWFALL, true);
			cell = sheet.getCellByPosition(3, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.snowfall()) + " in " + extreme.year());
			
			extreme = sf.getExtremeForDate(date, StatIndex.SNOWFALL, false);
			cell = sheet.getCellByPosition(4, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.snowfall()) + " in " + extreme.year());
		}
		
		row++; 
		
		sheet.getCellByPosition(0, row).setStringValue("Snow Depth");
		cell = sheet.getCellByPosition(1, row);
		if (entry.snowDepth() != null) {
			cell.setDoubleValue(entry.snowDepth());

			cell = sheet.getCellByPosition(2, row);
			cell.setDoubleValue(sf.getNormalForDate(date, StatIndex.SNOW_DEPTH));

			extreme = sf.getExtremeForDate(date, StatIndex.SNOW_DEPTH, true);
			cell = sheet.getCellByPosition(3, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.snowDepth()) + " in " + extreme.year());
			
			extreme = sf.getExtremeForDate(date, StatIndex.SNOW_DEPTH, false);
			cell = sheet.getCellByPosition(4, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.snowDepth()) + " in " + extreme.year());
		}
		
		row += 2;
		
		sheet.getCellByPosition(0, row).setStringValue("Month-to-Date");
		
		row++;
		
		sheet.getCellByPosition(0, row).setStringValue("Average Temperature");
		cell = sheet.getCellByPosition(1, row);
		if (entry.avgTemp() != null) {
			cell.setDoubleValue(sf.getAverageMonthToDate(date, StatIndex.AVG_TEMP));

			cell = sheet.getCellByPosition(2, row);
			cell.setDoubleValue(sf.getNormalMonthToDate(date, StatIndex.AVG_TEMP));

			extreme = sf.getExtremeForDate(date, StatIndex.AVG_TEMP, true);
			cell = sheet.getCellByPosition(3, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.avgTemp()) + " in " + extreme.year());
			
			extreme = sf.getExtremeForDate(date, StatIndex.AVG_TEMP, false);
			cell = sheet.getCellByPosition(4, row);
			cell.setStringValue(NO_DECIMAL_FORMAT.format(extreme.avgTemp()) + " in " + extreme.year());
		}
	}

	@Override
	public void outputToFile() {
		try {
			workbook.save(getFileName());
			System.out.println(date.get(Calendar.YEAR) + "-" + (date.get(Calendar.MONTH) + 1) + ".ods created");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void formatSpreadsheet() {
		formatTitle();
		formatColumnHeaders();
		setColumnWidths();
		formatEntries();
		formatStatCells();
	}

	private void setColumnWidths() {
		int[] widths = new int[] { 20, 24, 24, 24, 24, 24, 19, 15 };

		for (int i = 0; i < widths.length; i++) {
			Column column = sheet.getColumnByIndex(i);
			column.setWidth(widths[i]);
		}
	}

	private void formatStatCells() {
//		// Sums
//		sheet.getCellByPosition(1, statRowStart).setFormatString(NO_DECIMAL_FORMAT);
//		sheet.getCellByPosition(2, statRowStart).setFormatString(NO_DECIMAL_FORMAT);
//		sheet.getCellByPosition(5, statRowStart).setFormatString(TWO_DECIMAL_FORMAT);
//		sheet.getCellByPosition(6, statRowStart).setFormatString(ONE_DECIMAL_FORMAT);
//
//		// Averages
//		int[] colNums = new int[] { 1, 2, 3, 4, 7 };
//		for (int i = 0; i < colNums.length; i++) {
//			sheet.getCellByPosition(colNums[i], statRowStart + 1).setFormatString(ONE_DECIMAL_FORMAT);
//		}
//
//		// Normals
//		colNums = new int[] { 1, 2, 3, 5, 6 };
//		for (int i = 0; i < colNums.length; i++) {
//			if (colNums[i] != 5) {
//				sheet.getCellByPosition(colNums[i], statRowStart + 2).setFormatString(ONE_DECIMAL_FORMAT);
//			} else {
//				sheet.getCellByPosition(colNums[i], statRowStart + 2).setFormatString(TWO_DECIMAL_FORMAT);
//			}
//		}
//
//		// Departures
//		colNums = new int[] { 1, 2, 3, 5, 6 };
//		for (int i = 0; i < colNums.length; i++) {
//			if (colNums[i] != 5) {
//				sheet.getCellByPosition(colNums[i], statRowStart + 3).setFormatString(ONE_DECIMAL_FORMAT);
//			} else {
//				sheet.getCellByPosition(colNums[i], statRowStart + 3).setFormatString(TWO_DECIMAL_FORMAT);
//			}
//		}
//
//		// Center Align
//		// Add Border
//		for (int row = statRowStart; row < statRowStart + NUM_STAT_ROWS; row++) {
//			for (int col = 0; col < COLUMNS; col++) {
//				sheet.getCellByPosition(col, row).setHorizontalAlignment(HorizontalAlignmentType.CENTER);
//
//				if (row == statRowStart) {
//					Border line = new Border(Color.BLACK, 1, SupportedLinearMeasure.PT);
//					sheet.getCellByPosition(col, row).setBorders(CellBordersType.TOP, line);
//				}
//			}
//		}
	}

	private void formatEntries() {
		// for (int row = ENTRY_ROW_START; row < entries.size() +
		// ENTRY_ROW_START; row++) {
		// for (int col = 0; col < COLUMNS; col++) {
		// Cell cell = sheet.getCellByPosition(col, row);
		// formatEntryCell(cell);
		// }
		// }
	}

	private void formatEntryCell(Cell cell) {
//		cell.setHorizontalAlignment(HorizontalAlignmentType.CENTER);
//
//		if (!cell.getStringValue().equals("")) {
//			switch (cell.getColumnIndex()) {
//			case 0:
//				cell.setFormatString(DATE_FORMAT);
//				break;
//			case 1:
//				cell.setFormatString(NO_DECIMAL_FORMAT);
//				break;
//			case 2:
//				cell.setFormatString(NO_DECIMAL_FORMAT);
//				break;
//			case 3:
//				cell.setFormatString(ONE_DECIMAL_FORMAT);
//				break;
//			case 4:
//				cell.setFormatString(ONE_DECIMAL_FORMAT);
//				break;
//			case 5:
//				cell.setFormatString(TWO_DECIMAL_FORMAT);
//				break;
//			case 6:
//				cell.setFormatString(ONE_DECIMAL_FORMAT);
//				break;
//			case 7:
//				cell.setFormatString(NO_DECIMAL_FORMAT);
//				break;
//			}
//		}
	}

	private void formatColumnHeaders() {
		for (int col = 0; col < COLUMNS; col++) {
			Cell cell = sheet.getCellByPosition(col, 2);
			Font font = cell.getFont();
			font.setSize(8);
			font.setFontStyle(FontStyle.BOLD);
			cell.setFont(font);
			cell.setHorizontalAlignment(HorizontalAlignmentType.CENTER);
			cell.setTextWrapped(true);
		}

		Row row = sheet.getRowByIndex(2);
		row.setHeight(13, false);
	}

	private void formatTitle() {
		CellRange range = sheet.getCellRangeByPosition("A2", "H2");
		range.merge();

		Cell cell = sheet.getCellByPosition("A2");
		Font font = cell.getFont();
		font.setSize(14);
		font.setFontStyle(FontStyle.ITALIC);
		cell.setFont(font);
		cell.setVerticalAlignment(VerticalAlignmentType.TOP);

		Row row = sheet.getRowByIndex(1);
		row.setHeight(7, false);
	}

	private String getFileName() {
		StringBuilder sb = new StringBuilder();

		sb.append(Parser.OUTPUT_DIRECTORY);
		sb.append(date.get(Calendar.YEAR));
		sb.append('-');
		sb.append(date.get(Calendar.MONTH) + 1);
		sb.append(".ods");

		return sb.toString();
	}

	public double sumColumnData(int startRow, int forNumRows, int columnNumber) {
		int stopRow = startRow + forNumRows - 1;
		double sum = 0;

		for (int i = startRow; i <= stopRow; i++) {
			Cell cell = sheet.getCellByPosition(columnNumber, i);

			if (!cell.getStringValue().equals("")) {
				sum += sheet.getCellByPosition(columnNumber, i).getDoubleValue();
			}
		}

		return sum;
	}

	public double avgColumnData(int startRow, int forNumRows, int columnNumber) {
		// get a count of days without missing data
		int count = 0;
		for (int i = startRow; i < startRow + forNumRows; i++) {
			Cell cell = sheet.getCellByPosition(columnNumber, i);
			if (!cell.getStringValue().equals("")) {
				count++;
			}
		}

		double sum = sumColumnData(startRow, forNumRows, columnNumber);

		return sum / count;
	}

	public void setMonth(int month) {
		date.set(Calendar.MONTH, month);
	}

	public void setYear(int year) {
		date.set(Calendar.YEAR, year);
	}

	public void setDate(Calendar date) {
		this.date.set(Calendar.MONTH, date.get(Calendar.MONTH));
		this.date.set(Calendar.DATE, date.get(Calendar.DATE));
		this.date.set(Calendar.YEAR, date.get(Calendar.YEAR));
	}
}
