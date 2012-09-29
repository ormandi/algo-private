#include <math.h>
#include <stdio.h>

#define PROBLEM_SIZE 18
#define NUMBER_LENGTH PROBLEM_SIZE + 2
#define NUMBER_OF_DIGITS 10
#define DIGIT_SUM_SIZE PROBLEM_SIZE * 9 + 1
#define DIGIT_SQUARE_SUM_SIZE PROBLEM_SIZE * 81 + 1
 
// for sieving
#define SIEVE_SIZE DIGIT_SQUARE_SUM_SIZE
#define SQRT_SIEVE_SIZE ((int)sqrt(SIEVE_SIZE))

int c[NUMBER_LENGTH];
unsigned char isPrime[SIEVE_SIZE];

// d[l][k][s][q] = number of numbers which
//                   has a length of l,
//                   the digit on position l is k,
//                   the sum of its digits is s,
//                   the sum of square of its digits is q
// DP relation: d[l][k][s][q] = sum of each 0<=j<=9: d[l-1][s-j][q-j*j] 
int d[NUMBER_LENGTH][NUMBER_OF_DIGITS][DIGIT_SUM_SIZE][DIGIT_SQUARE_SUM_SIZE];

long long numberOfLuckyNumbers(long long num) {
  if (num < 10) {
    return 0;
  }
  
  long long numberOfLuckyNumbers = 0;  
  int mod, k = 0, l = 0;
  while (num / 10 != 0 || num % 10 != 0) {
    mod = (int)(num % 10);
    c[l ++] = mod;
    num /= 10;
  }
  
  int kk = 0;
  int digitSumPrev = 0, digitSquareSumPrev = 0; // accumulators
  int j, s, q;
  for (; 0 < l; l --) {
    k = c[l-1]; kk = k*k;
    for (j = 0; j < k; j++) {
      for (s = 0; s <= l * 9; s ++) {
        for (q = 0; q <= l * 81; q ++) {
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

int main(int argc, char **argv) {
  int i, j;
  // initialization of sieve
  for (i = 2; i < SIEVE_SIZE; i ++) {
    isPrime[i] = 1;
  }
  
  // sieving
  for (i = 2; i < SQRT_SIEVE_SIZE; i++) {
    if (isPrime[i]) {
      for (j = i*i; j < SIEVE_SIZE; j += i) {
        isPrime[j] = 0;
      }
    }
  }
  
  // computing the base case i.e. when l is equal to 1 into table dlm1
  int jj = 0;
  for (j = 0; j < NUMBER_OF_DIGITS; j ++) {
    jj = j*j;
    d[1][j][j][jj] = 1;
  }
  
  // dynamic computation of d[l] tables where 2 <= l
  int l, s, q, k;
  for (l = 2; l < NUMBER_LENGTH; l ++) {
    // summing through each possible extension digit j
    for (j = 0; j < NUMBER_OF_DIGITS; j ++) {
      jj = j*j;
      for (s = 0; s < DIGIT_SUM_SIZE; s ++) {
        for (q = 0; q < DIGIT_SQUARE_SUM_SIZE; q ++) {
          for (k = 0; k < NUMBER_OF_DIGITS; k++) {
            d[l][j][s][q] += (0 <= s-j && 0 <= q-jj) ? d[l-1][k][s-j][q-jj] : 0;
          }
        }
      }
    }
  }
  
  printf("int d[NUMBER_LENGTH][NUMBER_OF_DIGITS][DIGIT_SUM_SIZE][DIGIT_SQUARE_SUM_SIZE] = ");
  for (l=0; l < NUMBER_LENGTH; l++) {
    printf("{");
    for (j=0; j < NUMBER_OF_DIGITS; j++) {
      printf("{");
      for (s = 0; s < DIGIT_SUM_SIZE; s ++) {
        printf("{");
        for (q = 0; q < DIGIT_SQUARE_SUM_SIZE; q ++) {
          printf("{");
          for (k = 0; k < NUMBER_OF_DIGITS; k++) {
            printf("%d", d[l][j][s][q]);
            if (k < NUMBER_OF_DIGITS - 1) {
              printf(",");
            }
          }
          printf("}");
        }
        printf("}");
      }
      printf("}");
    }
    printf("}");
  }
  printf(";\n\n");
  
  // solution
  long long N, A, B, value;
  
  scanf("%lld", &N);;
  for (i = 0; i < N; i ++) {
    scanf("%lld %lld", &A, &B);
    
    // check number between A and B
    value = numberOfLuckyNumbers(B) - numberOfLuckyNumbers(A-1);
        
    // show output
    if (i < N-1) {
      printf("%lld\n", value);
    } else {
      printf("%lld", value);
    }
  }
    
  // reading end evaluation
  return 0;
}

