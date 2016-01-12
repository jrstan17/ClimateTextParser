package com.jrstan17.climate.datacreator;

import java.util.Scanner;

import com.jrstan17.climate.etc.FileHandler;

public class ClimateDataCreator {
   public static final String INPUT_FILE = "C:\\users\\jrstan17\\Desktop\\ClimateIO\\input\\input.txt";
   public static final String OUTPUT_FILE = "/home/jrstan17/Desktop/output.txt";
   public static final char TAB = '\t';
   public static final char NEW_LINE = '\n';
   private Scanner scr = null;
   private ClimateData cd = new ClimateData();

   public ClimateDataCreator() {
      scr = FileHandler.openFileToRead(INPUT_FILE);

      while (scr.hasNextLine()) {
         parseData();
      }
   }

   private void parseData() {
      String line = scr.nextLine();
      int currentYear = parseCurrentYear(line);

      if (currentYear != -1) {
         scr.nextLine();

         while (true) {
            line = scr.nextLine();
            String[] pieces = line.split("\t");

            if (pieces[0].equals("Sum")) {
               // Sum line
               scr.nextLine(); // Average line
               scr.nextLine(); // Normal line
               scr.nextLine(); // More Info line
               break;
            }

            // Add current year
            pieces[0] = pieces[0] + " " + String.valueOf(currentYear);

            cd.add(pieces);
         }
      } else {
         System.out.println("Error: An entry could not be parsed.");
      }
   }

   private int parseCurrentYear(String line) {
      String[] pieces = line.split(" ");

      try {
         return Integer.parseInt(pieces[pieces.length - 1]);
      } catch (NumberFormatException e) {
         return -1;
      }
   }

   public ClimateData getClimateData() {
      return cd;
   }
}
