package com.jrstan17.climate.operators.math.averages;

import java.util.ArrayList;
import java.util.Calendar;

import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.entry.EntryGroup;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.operators.math.Sum;
import com.jrstan17.climate.operators.normals.NormalSet;

public class PrecipAverage extends Average {

   @Override
   public void calculate(ArrayList<Entry> entries, StatIndex statIndex) {
      EntryGroup entryGroup = new EntryGroup();

      for (Entry e : entries) {
         entryGroup.add(e.year(), e);
      }
      
      double totalSum = 0.0;

      for (int i = entryGroup.getSmallestKey(); i <= entryGroup
            .getLargestKey(); i++) {
         ArrayList<Entry> list = entryGroup.getEntries(i);
         
         Sum sum = new Sum();
         sum.calculate(list, statIndex);
         
         totalSum += sum.getResult();
      }
      
      result = totalSum / entryGroup.size();
   }

//   public int monthsSpanned(ArrayList<Entry> entries) {
//      int currentMonth = -1;
//      int currentYear = -1;
//      int monthCount = 0;
//
//      for (Entry e : entries) {
//         int eMonth = e.date().get(Calendar.MONTH);
//         int eYear = e.date().get(Calendar.YEAR);
//
//         if (eMonth != currentMonth || eYear != currentYear) {
//            currentMonth = eMonth;
//            currentYear = eYear;
//            monthCount++;
//         }
//      }
//
//      return monthCount;
//   }
}
