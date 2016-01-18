package com.jrstan17.climate.etc;

import java.util.Calendar;

import com.jrstan17.climate.datacreator.ClimateData;
import com.jrstan17.climate.datacreator.ClimateDataCreator;
import com.jrstan17.climate.date.ClimateDate;
import com.jrstan17.climate.operators.math.Sum;
import com.jrstan17.climate.operators.normals.NormalSet;
import com.jrstan17.climate.operators.normals.NormalSinceDate;

public class Driver {

   public static void main(String[] args) {
      ClimateDataCreator cdc = new ClimateDataCreator();
      ClimateData cd = cdc.getClimateData();

      ClimateDate one = new ClimateDate(0, Calendar.APRIL, 1);
      ClimateDate two = new ClimateDate(0, Calendar.APRIL, 2);

      NormalSinceDate nsd = new NormalSinceDate();
      double result = nsd.calculate(cd.getAllEntries(), one, two,
            StatIndex.PRECIP);

      System.out.println(result);
   }
}
