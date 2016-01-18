package com.jrstan17.climate.operators.extremes;

import java.util.ArrayList;

import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.operators.EntryOperator;

public abstract class ExtremeOperator extends EntryOperator {
   
   public abstract void calculate(ArrayList<Entry> entries, StatIndex statIndex);

}
