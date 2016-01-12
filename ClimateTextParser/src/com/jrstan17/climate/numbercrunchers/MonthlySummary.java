package com.jrstan17.climate.numbercrunchers;

import java.util.ArrayList;

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

import com.jrstan17.climate.datacreator.ClimateData;
import com.jrstan17.climate.datacreator.ClimateDataCreator;
import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.Parser;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.etc.StatisticFactory;
import com.jrstan17.climate.etc.Utility;

public class MonthlySummary extends NumberCruncher {

   public static final int ROWS_FOR_OTHER_DATA = 5;
   public static final int COLUMNS = 8;
   public static final int ENTRY_ROW_START = 3;
   public static final int NUM_STAT_ROWS = 4;

   private int month = 0;
   private int year = 0;
   private int statRowStart = 0;
   private ClimateDataCreator cdc;
   private ClimateData cd;
   private ArrayList<Entry> entries = new ArrayList<Entry>();
   private Table sheet = null;

   public MonthlySummary() {
      cdc = new ClimateDataCreator();
      cd = cdc.getClimateData();
   }

   @Override
   public void calculate() {
      createEntries();
      calculateStatRowStart();
      fillSpreadsheet();
      formatSpreadsheet();
      outputToFile();
   }

   private void calculateStatRowStart() {
      statRowStart = ENTRY_ROW_START + entries.size();
   }

   private void createEntries() {
      entries.clear();

      for (Entry e : cd.getEntries()) {
         if (e.month() == month && e.year() == year) {
            entries.add(e);
         }
      }
   }

   @Override
   public void fillSpreadsheet() {
      try {
         workbook = SpreadsheetDocument.newSpreadsheetDocument();
         sheet = workbook.getSheetByIndex(0);
      } catch (Exception e) {
         e.printStackTrace();
      }

      Cell currentCell = sheet.getCellByPosition("A2");
      String title = "Ithaca Cornell University, NY - " + Utility.getFullMonthName(month) + " " + year;
      currentCell.setStringValue(title);

      String[] colHeaders = new String[] { "Date", "Maximum Temperature", "Minimum Temperature", "Average Temperature",
            "Average Temperature Departure", "Precipitation", "Snowfall", "Snow Depth" };
      for (int col = 0; col < COLUMNS; col++) {
         currentCell = sheet.getCellByPosition(col, 2);
         currentCell.setStringValue(colHeaders[col]);
      }

      int row = ENTRY_ROW_START;

      for (; row < entries.size() + ENTRY_ROW_START; row++) {
         Entry e = entries.get(row - ENTRY_ROW_START);

         if (e.date() != null) {
            currentCell = sheet.getCellByPosition(0, row);
            currentCell.setDateTimeValue(e.date());
         }

         if (e.maxTemp() != null) {
            currentCell = sheet.getCellByPosition(1, row);
            currentCell.setDoubleValue(e.maxTemp());
         }

         if (e.minTemp() != null) {
            currentCell = sheet.getCellByPosition(2, row);
            currentCell.setDoubleValue(e.minTemp());
         }

         if (e.avgTemp() != null) {
            currentCell = sheet.getCellByPosition(3, row);
            currentCell.setDoubleValue(e.avgTemp());
         }

         if (e.departTemp() != null) {
            currentCell = sheet.getCellByPosition(4, row);
            currentCell.setDoubleValue(e.departTemp());
         }

         if (e.precip() != null) {
            currentCell = sheet.getCellByPosition(5, row);
            currentCell.setDoubleValue(e.precip());
         }

         if (e.snowfall() != null) {
            currentCell = sheet.getCellByPosition(6, row);
            currentCell.setDoubleValue(e.snowfall());
         }

         if (e.snowDepth() != null) {
            currentCell = sheet.getCellByPosition(7, row);
            currentCell.setDoubleValue(e.snowDepth());
         } else {
            currentCell = sheet.getCellByPosition(7, row);
            currentCell.setStringValue("");
         }
      }

      row = statRowStart;

      sheet.getCellByPosition(0, row).setStringValue("Sum");
      sheet.getCellByPosition(1, row).setDoubleValue(sumColumnData(3, entries.size(), 1));
      sheet.getCellByPosition(2, row).setDoubleValue(sumColumnData(3, entries.size(), 2));
      double precipSum = sumColumnData(3, entries.size(), 5);
      sheet.getCellByPosition(5, row).setDoubleValue(precipSum);
      double snowfallSum = sumColumnData(3, entries.size(), 6);
      sheet.getCellByPosition(6, row).setDoubleValue(snowfallSum);

      row++;

      sheet.getCellByPosition(0, row).setStringValue("Average");
      double maxTempAvg = avgColumnData(3, entries.size(), 1);
      sheet.getCellByPosition(1, row).setDoubleValue(maxTempAvg);
      double minTempAvg = avgColumnData(3, entries.size(), 2);
      sheet.getCellByPosition(2, row).setDoubleValue(minTempAvg);
      double avgTempAvg = avgColumnData(3, entries.size(), 3);
      sheet.getCellByPosition(3, row).setDoubleValue(avgTempAvg);
      double departTempAvg = avgColumnData(3, entries.size(), 4);
      sheet.getCellByPosition(4, row).setDoubleValue(departTempAvg);
      double snowDepthAvg = avgColumnData(3, entries.size(), 7);
      sheet.getCellByPosition(7, row).setDoubleValue(snowDepthAvg);

      row++;

      StatisticFactory sf = new StatisticFactory();

      sheet.getCellByPosition(0, row).setStringValue("Normal");
      sheet.getCellByPosition(1, row).setDoubleValue(sf.getNormalForMonth(month, StatIndex.MAX_TEMP));
      sheet.getCellByPosition(2, row).setDoubleValue(sf.getNormalForMonth(month, StatIndex.MIN_TEMP));
      sheet.getCellByPosition(3, row).setDoubleValue(sf.getNormalForMonth(month, StatIndex.AVG_TEMP));
      sheet.getCellByPosition(5, row).setDoubleValue(sf.getNormalForMonth(month, StatIndex.PRECIP));
      sheet.getCellByPosition(6, row).setDoubleValue(sf.getNormalForMonth(month, StatIndex.SNOWFALL));

      row++;

      sheet.getCellByPosition(0, row).setStringValue("Departure");
      sheet.getCellByPosition(1, row).setDoubleValue(maxTempAvg - sf.getNormalForMonth(month, StatIndex.MAX_TEMP));
      sheet.getCellByPosition(2, row).setDoubleValue(minTempAvg - sf.getNormalForMonth(month, StatIndex.MIN_TEMP));
      sheet.getCellByPosition(3, row).setDoubleValue(avgTempAvg - sf.getNormalForMonth(month, StatIndex.AVG_TEMP));
      sheet.getCellByPosition(5, row).setDoubleValue(precipSum - sf.getNormalForMonth(month, StatIndex.PRECIP));
      sheet.getCellByPosition(6, row).setDoubleValue(snowfallSum - sf.getNormalForMonth(month, StatIndex.SNOWFALL));
   }

   @Override
   public void outputToFile() {
      try {
         workbook.save(getFileName());
         System.out.println(year + "-" + (month + 1) + ".ods created");
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
      setColorExtremes(true, 1, Color.RED);
      setColorExtremes(false, 1, Color.BLUE);
      setColorExtremes(false, 2, Color.BLUE);
      setColorExtremes(true, 2, Color.RED);
      setColorExtremes(true, 3, Color.RED);
      setColorExtremes(false, 3, Color.BLUE);
      setColorExtremes(true, 4, Color.RED);
      setColorExtremes(false, 4, Color.BLUE);
      setColorExtremes(true, 5, Color.GREEN);
      setColorExtremes(true, 6, Color.GREEN);
      setColorExtremes(true, 7, Color.GREEN);
   }

   public void setColorExtremes(boolean isMaximum, int columnIndex, Color color) {
      double recordValue;

      if (isMaximum) {
         recordValue = -9999;
      } else {
         recordValue = 9999;
      }

      for (int row = ENTRY_ROW_START; row < ENTRY_ROW_START + entries.size(); row++) {
         Cell cell = sheet.getCellByPosition(columnIndex, row);

         if (!cell.getStringValue().equals("")) {
            double current = cell.getDoubleValue();

            if (isMaximum && current > recordValue) {
               recordValue = current;
            } else if (!isMaximum && current < recordValue) {
               recordValue = current;
            }
         }
      }

      for (int row = ENTRY_ROW_START; row < ENTRY_ROW_START + entries.size(); row++) {
         Cell cell = sheet.getCellByPosition(columnIndex, row);

         if (!cell.getStringValue().equals("")) {
            if (cell.getDoubleValue() == recordValue) {
               if (recordValue != 0 || (columnIndex != 6 && columnIndex != 7)) {
                  Font font = cell.getFont();
                  font.setColor(color);
                  font.setSize(10);
                  cell.setFont(font);
               }
            }
         }
      }
   }

   private void setColumnWidths() {
      int[] widths = new int[] { 20, 24, 24, 24, 24, 24, 19, 15 };

      for (int i = 0; i < widths.length; i++) {
         Column column = sheet.getColumnByIndex(i);
         column.setWidth(widths[i]);
      }
   }

   private void formatStatCells() {
      // Sums
      sheet.getCellByPosition(1, statRowStart).setFormatString(NO_DECIMAL_FORMAT_STR);
      sheet.getCellByPosition(2, statRowStart).setFormatString(NO_DECIMAL_FORMAT_STR);
      sheet.getCellByPosition(5, statRowStart).setFormatString(TWO_DECIMAL_FORMAT_STR);
      sheet.getCellByPosition(6, statRowStart).setFormatString(ONE_DECIMAL_FORMAT_STR);

      // Averages
      int[] colNums = new int[] { 1, 2, 3, 4, 7 };
      for (int i = 0; i < colNums.length; i++) {
         sheet.getCellByPosition(colNums[i], statRowStart + 1).setFormatString(ONE_DECIMAL_FORMAT_STR);
      }

      // Normals
      colNums = new int[] { 1, 2, 3, 5, 6 };
      for (int i = 0; i < colNums.length; i++) {
         if (colNums[i] != 5) {
            sheet.getCellByPosition(colNums[i], statRowStart + 2).setFormatString(ONE_DECIMAL_FORMAT_STR);
         } else {
            sheet.getCellByPosition(colNums[i], statRowStart + 2).setFormatString(TWO_DECIMAL_FORMAT_STR);
         }
      }

      // Departures
      colNums = new int[] { 1, 2, 3, 5, 6 };
      for (int i = 0; i < colNums.length; i++) {
         if (colNums[i] != 5) {
            sheet.getCellByPosition(colNums[i], statRowStart + 3).setFormatString(ONE_DECIMAL_FORMAT_STR);
         } else {
            sheet.getCellByPosition(colNums[i], statRowStart + 3).setFormatString(TWO_DECIMAL_FORMAT_STR);
         }
      }

      // Center Align
      // Add Border
      for (int row = statRowStart; row < statRowStart + NUM_STAT_ROWS; row++) {
         for (int col = 0; col < COLUMNS; col++) {
            sheet.getCellByPosition(col, row).setHorizontalAlignment(HorizontalAlignmentType.CENTER);

            if (row == statRowStart) {
               Border line = new Border(Color.BLACK, 1, SupportedLinearMeasure.PT);
               sheet.getCellByPosition(col, row).setBorders(CellBordersType.TOP, line);
            }
         }
      }
   }

   private void formatEntries() {
      for (int row = ENTRY_ROW_START; row < entries.size() + ENTRY_ROW_START; row++) {
         for (int col = 0; col < COLUMNS; col++) {
            Cell cell = sheet.getCellByPosition(col, row);
            formatEntryCell(cell);
         }
      }
   }

   private void formatEntryCell(Cell cell) {
      cell.setHorizontalAlignment(HorizontalAlignmentType.CENTER);

      if (!cell.getStringValue().equals("")) {
         switch (cell.getColumnIndex()) {
         case 0:
            cell.setFormatString(DATE_FORMAT_STR);
            break;
         case 1:
            cell.setFormatString(NO_DECIMAL_FORMAT_STR);
            break;
         case 2:
            cell.setFormatString(NO_DECIMAL_FORMAT_STR);
            break;
         case 3:
            cell.setFormatString(ONE_DECIMAL_FORMAT_STR);
            break;
         case 4:
            cell.setFormatString(ONE_DECIMAL_FORMAT_STR);
            break;
         case 5:
            cell.setFormatString(TWO_DECIMAL_FORMAT_STR);
            break;
         case 6:
            cell.setFormatString(ONE_DECIMAL_FORMAT_STR);
            break;
         case 7:
            cell.setFormatString(NO_DECIMAL_FORMAT_STR);
            break;
         }
      }
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
      sb.append(year);
      sb.append('-');
      sb.append(month + 1);
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
      this.month = month;
   }

   public void setYear(int year) {
      this.year = year;
   }

   public void setDate(int month, int year) {
      this.month = month;
      this.year = year;
   }
}
