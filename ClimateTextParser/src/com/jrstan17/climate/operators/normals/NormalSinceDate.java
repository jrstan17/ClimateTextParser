package com.jrstan17.climate.operators.normals;

import java.util.ArrayList;
import java.util.Calendar;

import com.jrstan17.climate.date.ClimateDate;
import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.operators.math.MathOperator;

public class NormalSinceDate{
   private NormalSet firstNS;
   private NormalSet lastNS;
   private double result;

   public NormalSinceDate() {
   }

   public NormalSinceDate(ArrayList<Entry> rawEntries, ClimateDate start,
         ClimateDate stop, StatIndex statIndex) {
      calculate(rawEntries, start, stop, statIndex);
   }

   public void calculate(ArrayList<Entry> rawEntries, ClimateDate start,
         ClimateDate stop, StatIndex statIndex) {
      if (start.compareTo(stop) > 0) {
         ClimateDate lastDay = new ClimateDate(0, Calendar.DECEMBER, 31);
         ClimateDate firstDay = new ClimateDate(0, Calendar.JANUARY, 1);

         firstNS = new NormalSet(rawEntries, start, lastDay);
         lastNS = new NormalSet(rawEntries, firstDay, stop);

         firstNS.calculate(statIndex);
         lastNS.calculate(statIndex);

         if (statIndex == StatIndex.PRECIP || statIndex == StatIndex.SNOWFALL) {
            result = firstNS.getResult() + lastNS.getResult();
         } else {
            result = (firstNS.getResult() + lastNS.getResult()) / 2;
         }
      } else {
         firstNS = new NormalSet(rawEntries, start, stop);
         firstNS.calculate(statIndex);
         result = firstNS.getResult();
      }
   }
   
   public double getResult(){
      return result;
   }
}
