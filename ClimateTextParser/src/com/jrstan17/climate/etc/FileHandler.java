package com.jrstan17.climate.etc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
   public static Scanner openFileToRead(String file) {
      File newFile = new File(file);
      Scanner scr = null;

      try {
         scr = new Scanner(newFile);
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }

      return scr;
   }

   public static void writeToFile(StringBuilder sb, String directory, String fileName) {
      byte[] byteStream = sb.toString().getBytes();
      FileOutputStream out;

      try {
         out = new FileOutputStream(directory + fileName);
         out.write(byteStream);
         out.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
