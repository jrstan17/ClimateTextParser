package com.jrstan17.climate.etc;

import java.util.Scanner;

import com.jrstan17.climate.datacreator.ClimateData;

public class Parser {
   public static final String INPUT_DIRECTORY = "";
   public static final String INPUT_FILE = "input.txt";
   
   public static final String OUTPUT_DIRECTORY = "C:\\users\\jrstan17\\Desktop\\ClimateIO\\output\\";
   public static final char TAB = '\t';
   public static final char NEW_LINE = '\n';
   private Scanner scr = null;
   private StringBuilder sb = new StringBuilder();
   private int currentYear = 0;
   private ClimateData cd = new ClimateData();

   public Parser() {
      scr = FileHandler.openFileToRead(INPUT_DIRECTORY + INPUT_FILE);
      parse(scr);
   }

   public void parse(Scanner scr) {
      addColumnHeaders();

      while (scr.hasNextLine()) {
         parseDataToSB();
      }

      FileHandler.writeToFile(sb, OUTPUT_DIRECTORY, "output.txt");
   }

   private void parseDataToSB() {
      String line = scr.nextLine();
      currentYear = parseCurrentYear(line);

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
         parseArrayToSB(pieces);
      }
   }

   private void parseArrayToSB(String[] pieces) {
      sb.append(validatePiece(pieces[0]));
      sb.append(TAB);
      sb.append(validatePiece(pieces[1]));
      sb.append(TAB);
      sb.append(validatePiece(pieces[2]));
      sb.append(TAB);
      sb.append(validatePiece(pieces[4]));
      sb.append(TAB);
      sb.append(validatePiece(pieces[5]));
      sb.append(TAB);
      sb.append(validatePiece(pieces[6]));
      sb.append(TAB);
      sb.append(validatePiece(pieces[7]));
      sb.append(TAB);
      sb.append(validatePiece(pieces[8]));
      sb.append(NEW_LINE);
   }

   private String validatePiece(String piece) {
      if (piece.equals("-")) {
         return "";
      } else if (piece.equals("Trace")) {
         return "0";
      }

      return piece;
   }

   private int parseCurrentYear(String line) {
      String[] pieces = line.split(" ");
      return Integer.parseInt(pieces[pieces.length - 1]);
   }

   private void addColumnHeaders() {
      sb.append("Date");
      sb.append(TAB);
      sb.append("Max Temp");
      sb.append(TAB);
      sb.append("Min Temp");
      sb.append(TAB);
      sb.append("Avg Temp");
      sb.append(TAB);
      sb.append("Temp Depart");
      sb.append(TAB);
      sb.append("Precip");
      sb.append(TAB);
      sb.append("Snowfall");
      sb.append(TAB);
      sb.append("Snow Depth");
      sb.append(NEW_LINE);
   }
}
