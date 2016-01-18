package com.jrstan17.climate.numbercrunchers;

import java.text.SimpleDateFormat;

import com.jrstan17.climate.datacreator.ClimateData;
import com.jrstan17.climate.datacreator.ClimateDataCreator;
import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.etc.FileHandler;
import com.jrstan17.climate.etc.Parser;

public class AllData extends NumberCruncher {

   @Override
   public void calculate() {
      // finds the extremes of each day of the year for all time

      ClimateDataCreator cdc = new ClimateDataCreator();
      ClimateData cd = cdc.getClimateData();

      SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

      StringBuilder sb = new StringBuilder();

      sb.append("Date");
      sb.append(Parser.TAB);
      sb.append("Max Temp");
      sb.append(Parser.TAB);
      sb.append("Min Temp");
      sb.append(Parser.TAB);
      sb.append("Avg Temp");
      sb.append(Parser.TAB);
      sb.append("Avg Temp Depart");
      sb.append(Parser.TAB);
      sb.append("Precip");
      sb.append(Parser.TAB);
      sb.append("Snowfall");
      sb.append(Parser.TAB);
      sb.append("Snow Depth");
      sb.append(Parser.NEW_LINE);

      for (Entry e : cd.getAllEntries()) {
         sdf.setCalendar(e.date());
         sb.append(sdf.format(e.date().getTime()));
         sb.append(Parser.TAB);

         if (e.maxTemp() != null) {
            sb.append(e.maxTemp());
         }

         sb.append(Parser.TAB);

         if (e.minTemp() != null) {
            sb.append(e.minTemp());
         }

         sb.append(Parser.TAB);

         if (e.avgTemp() != null) {
            sb.append(e.avgTemp());
         }

         sb.append(Parser.TAB);

         if (e.departTemp() != null) {
            sb.append(e.departTemp());
         }

         sb.append(Parser.TAB);

         if (e.precip() != null) {
            sb.append(e.precip());
         }

         sb.append(Parser.TAB);

         if (e.snowfall() != null) {
            sb.append(e.snowfall());
         }

         sb.append(Parser.TAB);

         if (e.snowDepth() != null) {
            sb.append(e.snowDepth());
         }

         sb.append(Parser.NEW_LINE);
      }

      FileHandler.writeToFile(sb, Parser.OUTPUT_DIRECTORY, "output.txt");
   }

   @Override
   public void fillSpreadsheet() {
      // TODO Auto-generated method stub

   }

   @Override
   public void formatSpreadsheet() {
      // TODO Auto-generated method stub

   }

   @Override
   public void outputToFile() {
      // TODO Auto-generated method stub

   }
}
