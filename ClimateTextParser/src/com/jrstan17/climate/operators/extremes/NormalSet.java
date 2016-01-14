package com.jrstan17.climate.operators.extremes;

import java.util.ArrayList;
import java.util.Calendar;

import com.jrstan17.climate.datacreator.ClimateData;
import com.jrstan17.climate.datacreator.ClimateDataCreator;
import com.jrstan17.climate.date.ClimateDate;
import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.operators.math.MathOperator;
import com.jrstan17.climate.operators.math.averages.DailyAverage;
import com.jrstan17.climate.operators.math.averages.MonthlyAverage;

public class NormalSet {
   public static final ClimateDate CLIMATE_START_DATE = new ClimateDate(1981,
         Calendar.JANUARY, 1);
   public static final ClimateDate CLIMATE_STOP_DATE = new ClimateDate(2010,
         Calendar.DECEMBER, 31);

   private Double result = 0.0;
   private ArrayList<Entry> entries = new ArrayList<>();

   public ArrayList<Entry> getEntries() {
      return entries;
   }

   public NormalSet(ArrayList<Entry> rawEntries, ClimateDate dayMonthStart,
         ClimateDate dayMonthStop) {

      for (Entry e : rawEntries) {
         if (e.date().compareTo(CLIMATE_START_DATE) >= 0
               && e.date().compareTo(CLIMATE_STOP_DATE) <= 0
               && isBetween(e.date(), dayMonthStart, dayMonthStop)) {
            this.entries.add(e);
         }
      }
   }

   public void calculate(StatIndex statIndex) {
      MathOperator operator;

      if (statIndex == StatIndex.PRECIP || statIndex == StatIndex.SNOWFALL) {
         operator = new MonthlyAverage();
      } else {
         operator = new DailyAverage();
      }

      operator.calculate(entries, statIndex);
      result = operator.getResult();
   }

   
   private boolean isBetween(ClimateDate test, ClimateDate start,
         ClimateDate stop) {
      int testMonth = test.get(Calendar.MONTH);
      int testDay = test.get(Calendar.DATE);

      int startMonth = start.get(Calendar.MONTH);
      int startDay = start.get(Calendar.DATE);

      int stopMonth = stop.get(Calendar.MONTH);
      int stopDay = stop.get(Calendar.DATE);

      if (testMonth > startMonth && testMonth < stopMonth) {
         return true;
      } else if (testMonth == startMonth && testMonth == stopMonth) {
         if (testDay >= startDay && testDay <= stopDay) {
            return true;
         }
      } else if (testMonth == startMonth && testMonth != stopMonth) {
         if (testDay >= startDay) {
            return true;
         }
      } else if (testMonth != startMonth && testMonth == stopMonth) {
         if (testDay <= stopDay) {
            return true;
         }
      }

      return false;
   }

   public double getResult() {
      return result;
   }
   
   
   
   public static void main(String[] args) {
      ClimateDataCreator cdc = new ClimateDataCreator();
      ClimateData cd = cdc.getClimateData();

      ClimateDate firstOfMonth = new ClimateDate(0, Calendar.JANUARY, 1);
      ClimateDate today = new ClimateDate(0, Calendar.JANUARY, 31);
      
      NormalSet ns = new NormalSet(cd.getAllEntries(), firstOfMonth, today);
      ns.calculate(StatIndex.PRECIP);
      
      ArrayList<Entry> list = ns.getEntries();
      
      for (Entry e : list){
         System.out.println(e.date());
      }
      
      System.out.println(ns.getResult());
   }
}
