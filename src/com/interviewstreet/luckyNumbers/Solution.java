package com.interviewstreet.luckyNumbers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {
  
  private static void printdl(long[][] dl, int l) {
    long digitSum = 0, digitSquareSum = 0;
    for (int s = 0; s < dl.length; s ++) {
      for (int q = 0; q < dl[s].length; q ++) {
        if (dl[s][q] != 0) {
          System.err.println("d["+l+"]["+s+"]["+q+"] = " + dl[s][q]);
          digitSum += dl[s][q] * s;
          digitSquareSum += dl[s][q] * q;
        }
      }
    }
    System.err.println("DigitSum = " + digitSum);
    System.err.println("DigitSquareSum = " + digitSquareSum);
    System.err.println("----------");
  }
  
  private static long numberOfLuckyNumbers(long num, long[][] c) {
    long numberOfLuckyNumbers = 0;
    
    long prev = 0, cur = 0, prevDiv = 1, div = 10;
    int k = 0, l=0;
    while (cur < num) {
      cur = num % div;
      k = (int) ((cur - prev) / prevDiv);
      numberOfLuckyNumbers += c[++ l][k];
      prev = cur;
      prevDiv = div;
      div *= 10;
    }
    
    return numberOfLuckyNumbers;
  }
  
  public static void main(String[] args) {
    try {
      final int PROBLEM_SIZE = 18;
      final int NUMBER_LENGTH = PROBLEM_SIZE + 2;
      final int NUMBER_OF_DIGITS = 10;
      final int DIGIT_SUM_SIZE = PROBLEM_SIZE * 9 + 1;
      final int DIGIT_SQUARE_SUM_SIZE = PROBLEM_SIZE * 81 + 1;
      
      // initialization for sieving
      final int SIEVE_SIZE = DIGIT_SQUARE_SUM_SIZE;
      final int SQRT_SIEVE_SIZE = (int) Math.sqrt(SIEVE_SIZE);
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
      // d[l][s][q] = number of numbers which
      //                   has a length of l,
      //                   the sum of its digits is s,
      //                   the sum of square of its digits is q
      // DP relation: d[l][s][q] = sum of each 0<=j<=9: d[l-1][s-j][q-j*j] 
      long[][] dl = null;   // d[l]   
      long[][] dlm1 = new long[DIGIT_SUM_SIZE][DIGIT_SQUARE_SUM_SIZE]; // d[l-1]
      // lucky[l][k] = number of lucky numbers which are less than or equal to k*10^(l-1)
      // i.e. number
      long[][] lucky = new long[NUMBER_LENGTH][NUMBER_OF_DIGITS];
      
      // computing the base case i.e. when l is equal to 1 into table dlm1
      int jj = 0;
      for (int j = 0; j < NUMBER_OF_DIGITS; j ++) {
        jj = j*j;
        dlm1[j][jj] = 1;
      }
      
      // dynamic computation of d[l] tables where 2 <= l
      for (int l = 2; l < NUMBER_LENGTH; l++) {
        dl = new long[DIGIT_SUM_SIZE][DIGIT_SQUARE_SUM_SIZE];
        for (int s = 0; s < DIGIT_SUM_SIZE; s ++) {
          for (int q = 0; q < DIGIT_SQUARE_SUM_SIZE; q ++) {
            // summing through each possible extension digit j
            for (int j = 0; j < NUMBER_OF_DIGITS; j ++) {
              jj = j*j;
              dl[s][q] += (0 <= s-j && 0 <= q-jj) ? dlm1[s-j][q-jj] : 0;
            }
          }
        }
        
        if (l == 4) {
          printdl(dl, l);
        }
        
        // storing d[l] as d[l-1]
        dlm1 = dl;
      }
      
          
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