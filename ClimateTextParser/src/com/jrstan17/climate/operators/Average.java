package com.jrstan17.climate.operators;

import java.util.ArrayList;

import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.StatIndex;

public class Average extends NormalOperator {

   @Override
   public void calculate(ArrayList<Entry> entries, StatIndex statIndex) {
      Sum sumObj = new Sum();   
      sumObj.calculate(entries, statIndex);      
      
      result = sumObj.result / sumObj.getNumOfEntries();
   }
}
