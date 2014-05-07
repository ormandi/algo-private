package com.hackerrank.stockMaximize;

import java.io.*;


public class Solution {
  private static class Node {
    private final int opt;
    private final int sum;
    private final int counter;
    private Node(int opt, int sum, int counter) {
      this.opt = opt;
      this.sum = sum;
      this.counter = counter;
    }
  }

  public static void main(String[] args) throws Exception {
    final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    final int TESTS = Integer.parseInt(in.readLine());
    final int N = 50000;
    final Node[] solution = new Node[N];
    final int[] input = new int[N];

    for (int testCase = 0; testCase < TESTS; testCase++) {
      // reads the current testcase
      final int n = Integer.parseInt(in.readLine());
      final String[] line = in.readLine().split("\\s+");
      for (int i = 0; i < n; i++) {
        input[i] = Integer.parseInt(line[i]);
      }

      // computes the dynamic table
      solution[0] = new Node(0, input[0], 1);
      for (int i = 1;, i < n; i++) {

      }
    }
    in.close();
  }
}
