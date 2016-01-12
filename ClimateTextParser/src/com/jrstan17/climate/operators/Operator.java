package com.jrstan17.climate.operators;

import java.util.ArrayList;

import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.StatIndex;

public interface Operator {
   public void calculate(ArrayList<Entry> entries, StatIndex statIndex);
}
