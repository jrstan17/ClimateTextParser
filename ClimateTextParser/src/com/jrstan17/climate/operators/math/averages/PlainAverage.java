package com.jrstan17.climate.operators.math.averages;

import java.util.ArrayList;

import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.operators.math.Sum;

public class PlainAverage extends Average {

   public PlainAverage() {

   }

   public PlainAverage(ArrayList<Entry> entries, StatIndex statIndex) {
      calculate(entries, statIndex);
   }

   @Override
   public void calculate(ArrayList<Entry> entries, StatIndex statIndex) {
      Sum sumObj = new Sum();
      sumObj.calculate(entries, statIndex);

      if (sumObj.getResult() != null) {
         result = sumObj.getResult() / sumObj.getNumOfEntries();
      }
   }
}
