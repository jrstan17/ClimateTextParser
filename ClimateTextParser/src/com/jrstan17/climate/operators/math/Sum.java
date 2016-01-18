package com.jrstan17.climate.operators.math;

import java.util.ArrayList;

import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.etc.StatIndex;

public class Sum extends MathOperator {

   public Sum() {
   }

   public Sum(ArrayList<Entry> entries, StatIndex statIndex) {
      calculate(entries, statIndex);
   }

   @Override
   public void calculate(ArrayList<Entry> entries, StatIndex statIndex) {
      if (entries != null) {
         for (Entry e : entries) {
            if (e.get(statIndex) != null) {
               result += e.get(statIndex);
               numOfEntries++;
            }
         }
      }
   }
}
