package com.interviewstreet.luckyNumbers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SolutionPrev {
  
  public static void main(String[] args) {
    try {
      final int SIEVE_SIZE = 1500; // <= 1458
      final int SQRT_SIEVE_SIZE = (int) Math.sqrt(SIEVE_SIZE);
      boolean isPrime[] = new boolean[SIEVE_SIZE];
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
        int numberOfLuckyNumbers = 0;
        
        // compute the sum of digits of prevN naively
        long prev = 0, cur = 0, prevDiv = 1, div = 10;
        long d = 0;
        int digitSum = 0, digitSquareSum = 0;
        while (cur < A) {
          cur = A % div;
          d = (cur - prev) / prevDiv;
          digitSum += d;
          digitSquareSum += d * d;
          prev = cur;
          prevDiv = div;
          div *= 10;
        }
        
        // check whether A is lucky
        if (isPrime[digitSum] && isPrime[digitSquareSum]) {
          numberOfLuckyNumbers ++;
        }
        
        // process number from A+1 until B
        int t = 0;
        long l = 0;
        for (long n = A+1; n <= B; n++) {
          // count the trailing zeros of n
          t = 0;
          div = 10;
          while ((l = n % div) == 0) {
            t ++;
            div *= 10;
          }
          l /= div / 10;
          
          // compute the digit and digit square sum of n using the number of trailing zeros and previous values
          digitSum += 1 - t * 9;
          digitSquareSum += l * l- (l-1)*(l-1) - t * 81;
          
          // check whether n is lucky or not
          if (isPrime[digitSum] && isPrime[digitSquareSum]) {
            numberOfLuckyNumbers ++;
            System.err.println(n);
          }
        }
        
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
