package com.jrstan17.climate.operators.math.averages;

import java.util.ArrayList;

import com.jrstan17.climate.etc.Entry;
import com.jrstan17.climate.etc.StatIndex;
import com.jrstan17.climate.operators.math.MathOperator;

public abstract class Average extends MathOperator {

   @Override
   public abstract void calculate(ArrayList<Entry> entries, StatIndex statIndex);
}
