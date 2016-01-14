package com.jrstan17.climate.operators.math.averages;

import java.util.ArrayList;
import java.util.Calendar;

import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.operators.math.Sum;

public class MonthlyAverage extends Average {
   
   @Override
   public void calculate(ArrayList<Entry> entries, StatIndex statIndex) {
      Sum sumObj = new Sum();   
      sumObj.calculate(entries, statIndex);      
      
      result = sumObj.getResult() / monthsSpanned(entries);
   }
   
   public int monthsSpanned(ArrayList<Entry> entries){
      int currentMonth = -1;
      int currentYear = -1;
      int monthCount = 0;
      
      for (Entry e : entries){
         int eMonth = e.date().get(Calendar.MONTH);
         int eYear = e.date().get(Calendar.YEAR);
         
         if (eMonth != currentMonth || eYear != currentYear){
            currentMonth = eMonth;
            currentYear = eYear;
            monthCount++;
         }
      }
      
      return monthCount;
   }
}
