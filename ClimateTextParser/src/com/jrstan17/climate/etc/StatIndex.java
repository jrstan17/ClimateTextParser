package com.jrstan17.climate.etc;

public enum StatIndex {
   DATE, MAX_TEMP, MIN_TEMP, AVG_TEMP, AVG_TEMP_DEPART, PRECIP, SNOWFALL, SNOW_DEPTH;

   public static int getIndex(StatIndex statIndex) {
      if (statIndex == StatIndex.DATE) {
         return 0;
      } else if (statIndex == StatIndex.MAX_TEMP) {
         return 1;
      } else if (statIndex == StatIndex.MIN_TEMP) {
         return 2;
      } else if (statIndex == StatIndex.AVG_TEMP) {
         return 3;
      } else if (statIndex == StatIndex.AVG_TEMP_DEPART) {
         return 4;
      } else if (statIndex == StatIndex.PRECIP) {
         return 5;
      } else if (statIndex == StatIndex.SNOWFALL) {
         return 6;
      } else if (statIndex == StatIndex.SNOW_DEPTH) {
         return 7;
      } else {
         return -1;
      }
   }

   public static StatIndex getStatIndex(int index) {
      switch (index) {
      case 0:
         return DATE;
      case 1:
         return MAX_TEMP;
      case 2:
         return MIN_TEMP;
      case 3:
         return AVG_TEMP;
      case 4:
         return AVG_TEMP_DEPART;
      case 5:
         return PRECIP;
      case 6:
         return SNOWFALL;
      case 7:
         return SNOW_DEPTH;
      default:
         return null;
      }
   }
}
