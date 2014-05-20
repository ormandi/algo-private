package com.hackerrank.weeklyChallenge.week3.task1.loveLetterMystery;

import java.io.*;

public class Solution {
  public static void main(String[] args) throws Exception {
    final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    final int N = Integer.parseInt(in.readLine());
    for (int testCase = 0; testCase < N; testCase++) {
      long sum = 0;
      final String input = in.readLine();
      final int len = input.length();
      final int lenPer2 = len / 2;
      for (int i = 0; i < lenPer2; i++) {
        final int j = len - 1 - i;
        sum += Math.abs(input.charAt(i) - input.charAt(j));
      }
      System.out.println(sum);
    }
    in.close();
  }
}
