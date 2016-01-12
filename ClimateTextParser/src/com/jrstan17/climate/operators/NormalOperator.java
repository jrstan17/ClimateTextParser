package com.jrstan17.climate.operators;

import java.util.ArrayList;

import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.StatIndex;

public abstract class NormalOperator implements Operator {
   protected int numOfEntries = 0;
   protected Double result = 0.0;

   public abstract void calculate(ArrayList<Entry> entries, StatIndex statIndex);
   
   public Double getResult(){
      return result;
   }
   
   public int getNumOfEntries(){
      return numOfEntries;
   }
}
