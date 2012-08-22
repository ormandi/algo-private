package com.interviewstreet.luckyNumbers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
      
      // read the the number of test cases
      final int N = Integer.parseInt(in.readLine());
      
      // read and solve each test cases
      for (int i = 0; i < N; i ++) {
        String[] splited = in.readLine().split("\\s+");
        final long A = Long.parseLong(splited[0]);
        final long B = Long.parseLong(splited[1]);
      }
      
      // close input
      in.close();
    } catch (Exception e) {
      throw new RuntimeException(e); // dirty exception handling
    }
  }
}