package com.interviewstreet.pairs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Solution {
  class IncrementableLong {
    private long value;
    public IncrementableLong(long v) {
      value = v;
    }
    public void increment() {
      value ++;
    }
    public long value() {
      return value;
    }
  }
  
  public static void main(String[] args) {
    try {
      // open stdin as reader and create and instance of current class for instantiating subproblems
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      
      // read the length of the sequence and the difference
      String[] splited = in.readLine().split("\\s+");
      final long N = Long.parseLong(splited[0]);
      final long K = Long.parseLong(splited[1]);
      
      // read and process the sequence
      Solution s = new Solution();  // for instantiation IncrementableLong
      HashMap<Long,IncrementableLong> m = new HashMap<Long, IncrementableLong>();
      splited = in.readLine().split("\\s+");
      long c = 0; // count the number of pairs
      
      for (int i = 0; i < N && i < splited.length; i++) {
        long v = Long.parseLong(splited[i]);
        
        // number of occurrences of values q for which v - q = K
        IncrementableLong o  = m.get(v - K);
        c += (o != null) ? o.value() : 0;
        
        // number of occurrences of values q for which q - v = K
        o = m.get(v + K);
        c += (o != null) ? o.value : 0;
        
        // add the current value
        o = m.get(v);
        if (o != null) {
          o.increment();
        } else {
          m.put(v, s.new IncrementableLong(1));
        }
      }
      
      // show output
      System.out.print(c);
      
      // close input
      in.close();
    } catch (Exception e) {
      throw new RuntimeException(e); // dirty exception handling
    }
  }
}