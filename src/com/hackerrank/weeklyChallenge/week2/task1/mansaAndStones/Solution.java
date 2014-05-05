package com.hackerrank.weeklyChallenge.week2.task1.mansaAndStones;

import java.io.*;


public class Solution {

  public static void main(String[] args) throws Exception {
      final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      final int N = Integer.parseInt(in.readLine());
      for (int testCase = 0; testCase < N; testCase++) {
        final int n = Integer.parseInt(in.readLine());
        final int nm1 = n - 1;
        final int tmpA = Integer.parseInt(in.readLine());
        final int tmpB = Integer.parseInt(in.readLine());
        final int a = (tmpA < tmpB) ? tmpA : tmpB;
        final int b = (tmpA < tmpB) ? tmpB : tmpA;

        if (n == 1) {
          System.out.println(0);
        } else if (a == b) {
          System.out.println(nm1 * a);
        } else {
          for (int j = 0; j < n; j++) {
            final int i = nm1 - j;
            System.out.print((i * a + j * b) + " ");
          }
          System.out.println();
        }
      }
      in.close();
  }
}
