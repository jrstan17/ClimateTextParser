package com.jrstan17.climate.operators;

import java.util.ArrayList;

import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.StatIndex;

public abstract class NumberOperator extends Operator {
   protected Double result = 0.0;

   public abstract void calculate(ArrayList<Entry> entries, StatIndex statIndex);
   
   public Double getResult(){
      return result;
   }
}
