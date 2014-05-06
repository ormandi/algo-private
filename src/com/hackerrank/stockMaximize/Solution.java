package com.hackerrank.stockMaximize;

import java.io.*;


public class Solution {
  private static class Node {
    private final int opt;
    private final int sum;
    private Node(int opt, int sum) {
      this.opt = opt;
      this.sum = sum;
    }
  }

  public static void main(String[] args) throws Exception {
    final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    final int TESTS = Integer.parseInt(in.readLine());
    final int N = 12000;
    final Node[][] solution = new Node[N][];
    for (int i = 0; i < N; i++) {
      solution[i] = new Node[i + 1];  // i<=j solution[j][i] = opt[i..j]
    }

    for (int testCase = 0; testCase < TESTS; testCase++) {
      // reads the current testcase
      final int n = Integer.parseInt(in.readLine());
      final String[] line = in.readLine().split("\\s+");
      for (int i = 0; i < n; i++) {
        solution[i][i] = new Node(0, Integer.parseInt(line[i]));
      }

      // computes the dynamic table
      for (int diff = 1; diff < n; diff++) {
        for (int i = 0; i + diff < n; i++) {
          // computes opt[i..i+diff]
          int max = solution[i + diff][i + diff].sum * diff - solution[i + diff - 1][i].sum;
          for (int k = i; k + 1 <= i + diff; k++) {
            // examines opt[i..k] + opt[k+1..i+diff]
            final int value = solution[k][i].opt + solution[i + diff][k + 1].opt;
            if (value > max) {
              max = value;
            }
          }
          solution[i + diff][i] = new Node(max, solution[i + diff - 1][i].sum + solution[i + diff][i + diff].sum);
        }
      }

      // shows the optimal solution
      System.out.println(solution[n - 1][0].opt);
    }
    in.close();
  }
}
