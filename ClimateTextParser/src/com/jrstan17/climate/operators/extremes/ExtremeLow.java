package com.jrstan17.climate.operators.extremes;

import java.util.ArrayList;

import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.StatIndex;

public class ExtremeLow extends ExtremeOperator {

   @Override
   public void calculate(ArrayList<Entry> entries, StatIndex statIndex) {
      double extremeValue = Double.MAX_VALUE;

      for (Entry e : entries) {
         if (e.get(statIndex) != null) {
            double currentValue = e.get(statIndex);
            if (currentValue <= extremeValue) {
               extremeValue = currentValue;
               result = e;
            }
         }
      }
   }
}