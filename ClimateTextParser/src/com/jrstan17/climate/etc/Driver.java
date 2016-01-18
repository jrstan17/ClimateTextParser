package com.jrstan17.climate.etc;

import java.util.ArrayList;
import java.util.Calendar;

import com.jrstan17.climate.datacreator.ClimateData;
import com.jrstan17.climate.datacreator.ClimateDataCreator;
import com.jrstan17.climate.date.ClimateDate;
import com.jrstan17.climate.entry.Entry;
import com.jrstan17.climate.operators.math.Sum;
import com.jrstan17.climate.operators.normals.NormalSinceDate;

public class Driver {

   public static void main(String[] args) {
      ClimateDataCreator cdc = new ClimateDataCreator();
      ClimateData cd = cdc.getClimateData();

      ClimateDate one = new ClimateDate(0, Calendar.JULY, 1);
      ClimateDate two = new ClimateDate(0, Calendar.JANUARY, 17);

      NormalSinceDate nsd = new NormalSinceDate();
      double result = nsd.calculate(cd.getAllEntries(), one, two,
            StatIndex.SNOWFALL);

      ClimateDate today = new ClimateDate();
      ClimateDate julyFirst = new ClimateDate(today.get(Calendar.YEAR) - 1,
            Calendar.JULY, 1);

      ArrayList<Entry> list = cd.getEntries(julyFirst, today);
      Sum sum = new Sum();
      sum.calculate(list, StatIndex.SNOWFALL);

      System.out.println("Normal snowfall since July 1: \n" + result);
      System.out.println("Actual snowfall since July 1: \n" + sum.getResult());
   }
}
