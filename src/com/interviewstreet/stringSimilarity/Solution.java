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
        
        
        int[] z = new int[length];  // z-values
        z[0] = length;
        int l = 0, r = 0;
        long sum = length;
        
        // computing z-values and their sum
        for (int i = 1; i < length; i ++) {
          if (i == 1 || r < i) {
            // computing z[i] applying naive comparison
            for (int j = 0; j < length - i; j ++) {
              if (text.charAt(j) == text.charAt(i + j)) {
                z[i] ++;
              } else {
                break;
              }
            }
            r = i + z[i] - 1;
            l = i;
          } else {
            // compute z[i] based on the previously computed z-values
            int pI = i - l;        // "parent" of i
            int b = r - i + 1;     // length of t[i..r]=t[i'..r-l]
            int c = z[pI];         // length of t[pI..z[pI]]
            z[i] = c;
            
            if (b <= c) {
              z[i] = b;
              for (int j = 1; j < length - r; j++) {
                if (text.charAt(r+j) == text.charAt(r-l+j)) {
                  z[i] ++;
                } else {
                  l = i;
                  r = r + j - 1;
                  break;
                }
              }
            }
          }
          
          // sum up z-values
          sum += z[i];
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