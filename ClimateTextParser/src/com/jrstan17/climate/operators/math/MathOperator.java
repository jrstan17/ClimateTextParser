package com.jrstan17.climate.operators.math;

import java.util.ArrayList;

import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.operators.NumberOperator;

public abstract class MathOperator extends NumberOperator {
   public abstract void calculate(ArrayList<Entry> entries, StatIndex statIndex);
}
