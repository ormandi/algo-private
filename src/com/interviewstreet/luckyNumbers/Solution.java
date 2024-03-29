package com.interviewstreet.luckyNumbers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {
  public static final int PROBLEM_SIZE = 18;
  public static final int NUMBER_LENGTH = PROBLEM_SIZE + 2;
  public static final int NUMBER_OF_DIGITS = 10;
  public static final int DIGIT_SUM_SIZE = PROBLEM_SIZE * 9 + 1;
  public static final int DIGIT_SQUARE_SUM_SIZE = PROBLEM_SIZE * 81 + 1;
  
  // initialization for sieving
  public static final int SIEVE_SIZE = DIGIT_SQUARE_SUM_SIZE;
  public static final int SQRT_SIEVE_SIZE = (int) Math.sqrt(SIEVE_SIZE);
   
  private static long numberOfLuckyNumbers(long num, int[][][][] d, boolean[] isPrime) {
    if (num < 10) {
      return 0;
    }
    
    long numberOfLuckyNumbers = 0;
    
    int mod, k = 0, l = 0;
    int[] c = new int[NUMBER_LENGTH];
    while (num / 10 != 0 || num % 10 != 0) {
      mod = (int)(num % 10);
      c[l ++] = mod;
      num /= 10;
    }
    
    int kk = 0;
    int digitSumPrev = 0, digitSquareSumPrev = 0; // accumulators
    int origLength = l, firstDigit = c[origLength - 1];
    for (; 0 < l; l --) {
      k = c[l-1]; kk = k*k;
      for (int j = 0; j < k; j++) {
        for (int s = 0; s <= l * 9; s ++) {
          for (int q = 0; q <= l * 81; q ++) {
            if (0 < d[l][j][s][q] && isPrime[s + digitSumPrev] && isPrime[q + digitSquareSumPrev]) {
              numberOfLuckyNumbers += d[l][j][s][q];
            }
          }
        }        
      }
      
      digitSumPrev += k;
      digitSquareSumPrev += kk;
    }
    if (isPrime[digitSumPrev] && isPrime[digitSquareSumPrev]) {
      numberOfLuckyNumbers ++;
    }
    
    return numberOfLuckyNumbers;
  }
  
  public static void main(String[] args) {
    try {
      final boolean isPrime[] = new boolean[SIEVE_SIZE];
      Arrays.fill(isPrime, true);
      isPrime[0] = isPrime[1] = false;
      
      // sieving
      for (int i = 2; i < SQRT_SIEVE_SIZE; i++) {
        if (isPrime[i]) {
          for (int j = i*i; j < SIEVE_SIZE; j += i) {
            isPrime[j] = false;
          }
        }
      }
      
      // initialization for DP
      // d[l][k][s][q] = number of numbers which
      //                   has a length of l,
      //                   the digit on position l is k,
      //                   the sum of its digits is s,
      //                   the sum of square of its digits is q
      // DP relation: d[l][k][s][q] = sum of each 0<=j<=9: d[l-1][s-j][q-j*j] 
      final int[][][][] d = new int[NUMBER_LENGTH][NUMBER_OF_DIGITS][DIGIT_SUM_SIZE][DIGIT_SQUARE_SUM_SIZE];
      
      // computing the base case i.e. when l is equal to 1 into table dlm1
      int jj = 0;
      for (int j = 0; j < NUMBER_OF_DIGITS; j ++) {
        jj = j*j;
        d[1][j][j][jj] = 1;
      }
      
      // dynamic computation of d[l] tables where 2 <= l
      for (int l = 2; l < NUMBER_LENGTH; l++) {
        // summing through each possible extension digit j
        for (int j = 0; j < NUMBER_OF_DIGITS; j ++) {
          jj = j*j;
          for (int s = 0; s < DIGIT_SUM_SIZE; s ++) {
            for (int q = 0; q < DIGIT_SQUARE_SUM_SIZE; q ++) {
              for (int k = 0; k < NUMBER_OF_DIGITS; k++) {
                d[l][j][s][q] += (0 <= s-j && 0 <= q-jj) ? d[l-1][k][s-j][q-jj] : 0;
              }
            }
          }
        }
      }
      
      //System.err.println(Solution.numberOfLuckyNumbers(25, d, isPrime));
          
      // open stdin as reader and create and instance of current class for instantiating subproblems
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      
      // read the the number of test cases
      final int N = Integer.parseInt(in.readLine());
      
      // read and solve each test cases
      for (int i = 0; i < N; i ++) {
        // read next line
        String[] splited = in.readLine().split("\\s+");
        
        // parse A and B
        final long A = Long.parseLong(splited[0]);
        final long B = Long.parseLong(splited[1]);
        
        // check number between A and B
        long numberOfLuckyNumbers = numberOfLuckyNumbers(B, d, isPrime) - numberOfLuckyNumbers(A-1, d, isPrime);
        
        // show output
        if (i < N-1) {
          System.out.println(numberOfLuckyNumbers);
        } else {
          System.out.print(numberOfLuckyNumbers);
        }
      }
      
      // close input
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
      //throw new RuntimeException(e); // dirty exception handling
    }
  }
}
