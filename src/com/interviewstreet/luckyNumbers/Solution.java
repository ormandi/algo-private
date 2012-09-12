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
  
  private static void printdl(long[][][] dl, int l) {
    long digitSum = 0, digitSquareSum = 0;
    for (int k = 0; k < dl.length; k ++) {
      for (int s = 0; s < dl[k].length; s ++) {
        for (int q = 0; q < dl[k][s].length; q ++) {
          if (dl[k][s][q] != 0) {
            System.err.println("d["+l+"]["+k+"]["+s+"]["+q+"] = " + dl[k][s][q]);
            digitSum += dl[k][s][q] * s;
            digitSquareSum += dl[k][s][q] * q;
          }
        }
      }
    }
    System.err.println("DigitSum = " + digitSum);
    System.err.println("DigitSquareSum = " + digitSquareSum);
    System.err.println("----------");
  }
  
  private static long numberOfLuckyNumbers(long num, long[][][][] d, boolean[] isPrime) {
    long numberOfLuckyNumbers = 0;
    
    int mod, k = 0, l = 0;
    int[] c = new int[NUMBER_LENGTH];
    while ((mod = (int)(num % 10)) > 0) {
      c[l ++] = mod;
      num /= 10;
    }
    
    int kk = 0;
    int digitSumPrev = 0, digitSquareSumPrev = 0; // accumulators
    for (; 0 < l; l --) {
      k = c[l-1]; kk = k*k;
      
      System.err.println(k);
      
      for (int j = 0; j < k; j++) {
        int jj = j*j;
        for (int s = 0; s < DIGIT_SUM_SIZE - digitSumPrev - j; s ++) { // better bound
          for (int q = 0; q < DIGIT_SQUARE_SUM_SIZE - digitSquareSumPrev - jj; q ++) {
            if (0 < d[l][j][s + j + digitSumPrev][q + jj + digitSquareSumPrev] && isPrime[s + j + digitSquareSumPrev] && isPrime[q + jj + digitSquareSumPrev]) {
              System.err.println(l + "\t" + j + "\t" + (s + j + digitSumPrev) + "\t" + (q + jj + digitSquareSumPrev) + "\t" + d[l][j][s + j + digitSumPrev][q + jj + digitSquareSumPrev] );
              numberOfLuckyNumbers += d[l][j][s + j + digitSumPrev][q + jj + digitSquareSumPrev];
            }
          }
        }        
      }
      
      digitSumPrev += k;
      digitSquareSumPrev += kk;
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
      long[][][][] d = new long[NUMBER_LENGTH][NUMBER_OF_DIGITS][DIGIT_SUM_SIZE][DIGIT_SQUARE_SUM_SIZE];
      
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
      
      System.err.println(Solution.numberOfLuckyNumbers(25, d, isPrime));
          
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
        long numberOfLuckyNumbers = 0;//numberOfLuckyNumbers(B, c) - numberOfLuckyNumbers(A-1, c);
        
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
      throw new RuntimeException(e); // dirty exception handling
    }
  }
}