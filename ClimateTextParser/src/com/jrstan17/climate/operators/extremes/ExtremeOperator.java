package com.jrstan17.climate.operators.extremes;

import java.util.ArrayList;

import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.operators.Operator;

public abstract class ExtremeOperator implements Operator {
   protected Entry extreme;
   
   public abstract void calculate(ArrayList<Entry> entries, StatIndex statIndex);
   
   public Entry getExtreme(){
      return extreme;
   }
}
