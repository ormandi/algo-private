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
        
        // s[i] stores the length of the proper substring ended in position i which matches the prefix of the text
        int[] s = new int[length];  // weak KMP failure function
        s[0] = 0;
        int[] ss = new int[length]; // strong KMP failure function i.e. it's also required t[i+1] != t[s[i]]+1
        ss[0] = 0;
        int[] z = new int[length];  // z-values
        z[0] = length;
        
        // computing failure function of weak and strong KMP, then the corresponding z-values
        for (int i = 1; i < length; i ++) {
          // weak KMP computation
          int x = i - 1;
          while (0 < x && text.charAt(s[x]) != text.charAt(i)) {
            x = s[x] - 1;
          }
          s[i] = ( 0 <= x && text.charAt(s[x]) == text.charAt(i) ) ? s[x] + 1 : 0;
          
          // strong KMP computation
          x = i;
          while (0 < x && i+1 < length && text.charAt(s[x]) == text.charAt(i+1)) {
            x = s[x] - 1;
          }
          ss[i] = (0 <= x && (length-1 == i || text.charAt(s[x]) != text.charAt(i+1))) ? s[x]: 0;
          
          // compute z-values
          final int j = i - ss[i] + 1;
          z[j] = ss[i];
          
          // DEBUG:
          System.out.println("s[" + i + "]=" + s[i] + "\tss[" + i + "] = " + ss[i] + "\tz[" + j + "]=" + z[j]);
        }
        
        // compute final solution
        long sum = 0;
        for (int i = 0; i < length; i++) {
          // sum up z-values
          sum += z[i];
          
          // DEBUG
          System.out.println("z[" + i + "]=" + z[i]);
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