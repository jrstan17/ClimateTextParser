package com.jrstan17.climate.entry;

import java.util.ArrayList;
import java.util.HashMap;

public class EntryGroup {
   private HashMap<Integer, ArrayList<Entry>> map = new HashMap<>();
   private int smallestKey = Integer.MAX_VALUE;
   private int largestKey = Integer.MIN_VALUE;

   public HashMap<Integer, ArrayList<Entry>> getMap() {
      return map;
   }

   public void add(Integer key, Entry entry) {

      ArrayList<Entry> values = map.get(key);

      if (values == null) {
         map.put(key, new ArrayList<Entry>());
         values = map.get(key);
      }
      
      values.add(entry);

      if (key < smallestKey){
         smallestKey = key;
      } else if (key > largestKey){
         largestKey = key;
      }
   }
   
   public int size(){
      return map.size();
   }

   public ArrayList<Entry> getEntries(int key) {
      return map.get(key);
   }

   public int getSmallestKey() {
      return smallestKey;
   }

   public int getLargestKey() {
      return largestKey;
   }   
}
