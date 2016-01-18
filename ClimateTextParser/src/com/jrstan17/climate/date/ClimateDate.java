package com.jrstan17.climate.date;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

@SuppressWarnings("serial")
public class ClimateDate extends GregorianCalendar {

   public ClimateDate() {

   }

   public ClimateDate(int year, int month, int date) {
      set(year, month, date);
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
      StringBuilder sb = new StringBuilder();

      sb.append(getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US));
      sb.append(" ");
      sb.append(get(Calendar.DATE));
      sb.append(", ");
      sb.append(get(Calendar.YEAR));

      return sb.toString();
   }

   public ClimateDate getLastJulyFirst() {
      ClimateDate julyFirst = new ClimateDate(get(Calendar.YEAR), Calendar.JULY,
            1);

      if (this.compareTo(julyFirst) == 1) {
         julyFirst.add(Calendar.YEAR, -1);
      }

      return julyFirst;
   }
}
