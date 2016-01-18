package com.jrstan17.climate.operators.normals;

import java.util.ArrayList;
import java.util.Calendar;

import com.jrstan17.climate.date.ClimateDate;
import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.etc.StatIndex;

public class NormalSinceDate {
   NormalSet firstNS;
   NormalSet lastNS;

   public double calculate(ArrayList<Entry> rawEntries, ClimateDate start,
         ClimateDate stop, StatIndex statIndex) {
      if (start.compareTo(stop) > 0) {
         ClimateDate lastDay = new ClimateDate(0, Calendar.DECEMBER, 31);
         ClimateDate firstDay = new ClimateDate(0, Calendar.JANUARY, 1);

         firstNS = new NormalSet(rawEntries, start, lastDay);
         lastNS = new NormalSet(rawEntries, firstDay, stop);

         firstNS.calculate(statIndex);
         lastNS.calculate(statIndex);

         if (statIndex == StatIndex.PRECIP || statIndex == StatIndex.SNOWFALL) {
            return firstNS.getResult() + lastNS.getResult();
         } else {
            return (firstNS.getResult() + lastNS.getResult()) / 2;
         }
      } else {
         firstNS = new NormalSet(rawEntries, start, stop);
         firstNS.calculate(statIndex);
         return firstNS.getResult();
      }
   }
}
