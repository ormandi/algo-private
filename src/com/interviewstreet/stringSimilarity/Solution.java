package com.interviewstreet.stringSimilarity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution {
  public static void main(String[] args) {
    try {
      // open stdin as reader and create and instance of current class for instantiating subproblems
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      
      // number of test cases
      final int N = Integer.parseInt(in.readLine());
      
      // read each test case line-by-line and process it
      String text = in.readLine();
      int testID = 0;
      while (text != null) {
        // process text
        final int length = text.length();
        long sum = 0;
        
        // s[i] stores the length of the proper substring ended in i which matches the prefix of the text (KMP)
        int[] s = new int[length];
        // initialize the base case
        s[0] = 0;
        
        System.out.println(text + "\ns[" + 0 + "] = " + s[0]);
        
        // similar processing than that was proposed in the KMP algorithm
        for (int i = 1; i < length; i ++) {
          int x = i - 1;
          
          while (0 < x && text.charAt(s[x]) != text.charAt(i)) {
            x = s[x] - 1;
          }
          s[i] = ( 0 <= x && text.charAt(s[x]) == text.charAt(i)) ? s[x] + 1 : 0;
          
          System.out.println("s[" + i + "] = " + s[i]);
          
          sum += s[i];
        }
        
        // show output
        if (++ testID < N) {
          System.out.println(sum);
        } else {
          System.out.print(sum);
        }
        
        // read the next line
        text = in.readLine();
      }
      
      // close input
      in.close();
    } catch (Exception e) {
      throw new RuntimeException(e); // dirty exception handling
    }
  }
}