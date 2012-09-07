package com.interviewstreet.luckyNumbers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution {
  
  private static void printdl(long[][][] dl, int l) {
    for (int k = 0; k < dl.length; k ++) {
      for (int s = 0; s < dl[k].length; s ++) {
        for (int q = 0; q < dl[k][s].length; q ++) {
          if (dl[k][s][q] != 0) {
            System.err.println("d["+l+"]["+k+"]["+s+"]["+q+"] = " + dl[k][s][q]);
          }
        }
      }
    }
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
      // d[l][k][s][q] = number of numbers which
      //                   length is equal to l,
      //                   the lth digit is k,
      //                   the sum of its digits is s,
      //                   the sum of square of its digits is q
      // DP relation: d[l][k][s][q] = sum of each 0<=j<=9: d[l-1][j][s-k][q-k*k] 
      long[][][] dl = null;   // d[l]   
      long[][][] dlm1 = new long[NUMBER_OF_DIGITS][DIGIT_SUM_SIZE][DIGIT_SQUARE_SUM_SIZE]; // d[l-1]
      // c[l][k] = number of lucky number in length l with digit k at position l
      // c[l][k] = sum of each s,q: (isPrime[s] && isPrime[q]) ? d[l][k][s][q] : 0
      final long[][] c = new long[NUMBER_LENGTH][NUMBER_OF_DIGITS];
      
      // computing the base case i.e. when l is equal to 1 into table dlm1
      int kk = 0;
      for (int k = 1; k < NUMBER_OF_DIGITS; k ++) {
        kk = k*k;
        dlm1[k][k][kk] = 1;
        c[1][k] = (isPrime[k] && isPrime[kk]) ? 1 : 0;
      }
      
      //printdl(dlm1, 1);
      
      // dynamic computation of d[l] tables where 2 <= l
      for (int l = 2; l < NUMBER_LENGTH; l++) {
        dl = new long[NUMBER_OF_DIGITS][DIGIT_SUM_SIZE][DIGIT_SQUARE_SUM_SIZE];
        
        // for each digit k
        for (int k = 0; k < NUMBER_OF_DIGITS; k ++) {
          kk = k*k; // square of k
          dl[k][k][kk] += 1;
          // computing d[l][k] and c[l][k]
          for (int s = 0; s < DIGIT_SUM_SIZE; s ++) {
            for (int q = 0; q < DIGIT_SQUARE_SUM_SIZE; q ++) {
              // summing through each possible previous ending digit j
              for (int j = 0; j < NUMBER_OF_DIGITS; j ++) {
                dl[k][s][q] += (0 <= s-k && 0 <= q-kk) ? dlm1[j][s-k][q-kk] : 0;
              }
              
              // d[l][k][s][q] is computed => increment c[l][k] if necessary
              c[l][k] += (isPrime[s] && isPrime[q]) ? dl[k][s][q] : 0;
            }
          }
        }
        
        if (l==4) {
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
        long numberOfLuckyNumbers = numberOfLuckyNumbers(B, c) - numberOfLuckyNumbers(A-1, c);
        
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