package com.jrstan17.climate.operators.math;

import java.util.ArrayList;

import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.StatIndex;

public class Sum extends MathOperator {

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
