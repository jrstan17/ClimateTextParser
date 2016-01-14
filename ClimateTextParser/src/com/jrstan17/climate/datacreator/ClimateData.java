package com.jrstan17.climate.datacreator;

import java.util.ArrayList;
import java.util.Calendar;

import com.jrstan17.climate.date.ClimateDate;
import com.jrstan17.climate.etc.Entry;

public class ClimateData {
   private ArrayList<Entry> entries = new ArrayList<>();

   public void add(String[] pieces) {
      entries.add(new Entry(pieces));
   }

   public ArrayList<Entry> getAllEntries() {
      return entries;
   }

   public Entry getEntry(ClimateDate date) {
      for (Entry e : entries) {
         if (e.equals(date)) {
            return e;
         }
      }

      return null;
   }

   public ArrayList<Entry> getEntries(ClimateDate start, ClimateDate stop) {
      ArrayList<Entry> toReturn = new ArrayList<>();

      for (Entry e : entries) {
         ClimateDate eDate = e.date();
         if (eDate.compareTo(start) >= 0 && eDate.compareTo(stop) <= 0) {
            toReturn.add(e);
         }
      }

      return toReturn;
   }
}
