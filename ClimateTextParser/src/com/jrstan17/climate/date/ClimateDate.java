package com.jrstan17.climate.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SuppressWarnings("serial")
public class ClimateDate extends GregorianCalendar {

   public ClimateDate() {

   }

   public ClimateDate(int year, int month, int date) {
      set(Calendar.YEAR, year);
      set(Calendar.MONTH, month);
      set(Calendar.DATE, date);
   }

   public final boolean equals(final Object other) {
      if (other == null) {
         return false;
      } // end of if ()
      else if (this.getClass() != other.getClass()) {
         return false;
      } // end of if ()
      else {
         GregorianCalendar gc = (GregorianCalendar) other;
         return (compareTo(gc) == 0);
      } // end of else
   }

   public int compareTo(Calendar date) {
      if (get(Calendar.YEAR) < date.get(Calendar.YEAR)) {
         return -1;
      } else if (get(Calendar.YEAR) > date.get(Calendar.YEAR)) {
         return 1;
      } else {
         if (get(Calendar.MONTH) < date.get(Calendar.MONTH)) {
            return -1;
         } else if (get(Calendar.MONTH) > date.get(Calendar.MONTH)) {
            return 1;
         } else {
            if (get(Calendar.DATE) < date.get(Calendar.DATE)) {
               return -1;
            } else if (get(Calendar.DATE) > date.get(Calendar.DATE)) {
               return 1;
            } else {
               return 0;
            }
         }
      }
   }

   public String toString() {
      SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, YYYY");
      return sdf.format(this.getTime());
   }

   public String toString(String pattern) {
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      return sdf.format(this.getTime());
   }
}
