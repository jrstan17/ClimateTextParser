package com.jrstan17.climate.operators;

import java.util.ArrayList;

import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.etc.StatIndex;

public abstract class Operator {
   protected int numOfEntries = 0;

   public abstract void calculate(ArrayList<Entry> entries, StatIndex statIndex);
   
   public int getNumOfEntries(){
      return numOfEntries;
   }
}