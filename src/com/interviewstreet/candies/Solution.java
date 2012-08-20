package com.interviewstreet.candies;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
  public static void main(String[] args) {
    try {
      // open stdin as reader and create and instance of current class for instantiating subproblems
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      
      // number of children
      final int N = Integer.parseInt(in.readLine());
      
      // allocate memory for ranks and computation
      int[] rank = new int[N];
      long[] forward = new long[N], backward = new long[N];
      
      // read ranks of children line-by-line
      int i = 0;
      String line = in.readLine();
      while (line != null) {
        rank[i] = Integer.parseInt(line);
        forward[i] = (i > 0 && rank[i] > rank[i-1]) ? forward[i-1] + 1 : 1;
        
        // read the next line
        i ++;
        line = in.readLine();
      }
      
      // perform backward computation and sum up maximal values
      long sum = 0;
      for (i=N-1; i >= 0; i--) {
        backward[i] = (i+1 < N && rank[i] > rank[i+1]) ? backward[i+1] + 1 : 1;
        sum += Math.max(forward[i], backward[i]);
      }
      System.out.print(sum);
      
      // close input
      in.close();
    } catch (Exception e) {
      throw new RuntimeException(e); // dirty exception handling
    }
  }
}