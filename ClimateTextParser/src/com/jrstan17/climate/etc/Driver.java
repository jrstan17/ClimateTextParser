package com.jrstan17.climate.etc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.jrstan17.climate.datacreator.ClimateData;
import com.jrstan17.climate.datacreator.ClimateDataCreator;
import com.jrstan17.climate.date.ClimateDate;
import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.operators.math.MathOperator;
import com.jrstan17.climate.operators.math.averages.PlainAverage;

public class Driver {
   
   String ok = "ok";

   public static void main(String[] args) {
      StatIndex stat = StatIndex.SNOWFALL;
      DecimalFormat df = new DecimalFormat("0.0");

      ClimateDataCreator cdc = new ClimateDataCreator();
      ClimateData cd = cdc.getClimateData();

      for (int year = 1893; year <= 2016; year++) {
         ClimateDate start = new ClimateDate(year, Calendar.JANUARY, 1);
         ClimateDate stop = new ClimateDate();
         stop.set(Calendar.YEAR, year);
//         stop.set(Calendar.MONTH, Calendar.JULY);
//         stop.set(Calendar.DATE, 1);

         ArrayList<Entry> entries = cd.getEntries(start, stop);
         MathOperator operator = new PlainAverage(entries, stat);

         if (operator.getResult() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Average " + StatIndex.getLabel(stat) + " for ");
            sb.append(start);
            sb.append(" through ");
            sb.append(stop);
            sb.append(":\t");
            sb.append(df.format(operator.getResult()));

            System.out.println(sb.toString());
         }
      }
   }
}
