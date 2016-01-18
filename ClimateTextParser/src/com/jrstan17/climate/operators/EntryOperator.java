package com.jrstan17.climate.operators;

import java.util.ArrayList;

import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.etc.StatIndex;

public abstract class EntryOperator extends Operator {

   protected Entry result;

   public abstract void calculate(ArrayList<Entry> entries, StatIndex statIndex);
   
   public Entry getResult(){
      return result;
   }
}
