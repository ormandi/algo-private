package com.interviewstreet.evernote.multiplyExceptSelf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
  public static void main(String[] args) {
    try {
      // open stdin as reader
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
      // read the number of terms
      String line = in.readLine();
      final int N = Integer.parseInt(line);
      
      // allocate arrays
      long[] x = new long[N];
      long[] p = new long[N];
      
      // read and process terms
      line = in.readLine();
      for (int i = 0; i < N && line != null; i ++) {
        // read xi from input
        x[i] = Long.parseLong(line);
        
        // compute 'backward' products
        p[i] = (i > 0) ? x[i-1] * p[i-1] : 1;
        
        // read the next line
        line = in.readLine();
      }
      
      // now p[i] contains the products from x[0] until x[i-1] => compute the forward part as well
      for (int i = N - 1; i >= 0; i --) {
        // compute 'forward' products into x i.e. x[i] contains the products from x[i] until x[N-1] 
        x[i] *= (i + 1 < N) ? x[i+1] : 1;
        p[i] *= (i + 1 < N) ? x[i+1] : 1; 
      }
      
      // show output
      StringBuffer buffer = new StringBuffer();
      for (int i = 0; i < N - 1; i ++) {
        buffer.append(p[i]).append('\n');
      }
      buffer.append(p[N-1]);
      System.out.print(buffer.toString());
      
      // close input
      //in.close();
    } catch (IOException e) {
      throw new RuntimeException(e); // dirty solution
    }
  }
}