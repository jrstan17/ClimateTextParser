package com.jrstan17.climate.etc;

import java.util.Calendar;

import com.jrstan17.climate.datacreator.ClimateData;
import com.jrstan17.climate.datacreator.ClimateDataCreator;
import com.jrstan17.climate.date.ClimateDate;
import com.jrstan17.climate.operators.extremes.NormalSet;

public class Driver {

   public static void main(String[] args) {
      ClimateDataCreator cdc = new ClimateDataCreator();
      ClimateData cd = cdc.getClimateData();

      ClimateDate firstOfMonth = new ClimateDate(0, Calendar.JANUARY, 1);
      ClimateDate today = new ClimateDate(0, Calendar.JANUARY, 31);
      
      NormalSet ns = new NormalSet(cd.getAllEntries(), firstOfMonth, today);
      ns.calculate(StatIndex.PRECIP);
      
      System.out.println(ns.getResult());
   }
}
