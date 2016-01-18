package com.jrstan17.climate.operators.math.averages;

import java.util.ArrayList;

import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.operators.math.Sum;

public class PlainAverage extends Average {
   @Override
   public void calculate(ArrayList<Entry> entries, StatIndex statIndex) {
      Sum sumObj = new Sum();   
      sumObj.calculate(entries, statIndex);      
      
      result = sumObj.getResult() / sumObj.getNumOfEntries();
   }
}
