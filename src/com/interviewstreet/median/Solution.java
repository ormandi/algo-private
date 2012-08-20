package com.interviewstreet.median;

import java.util.Scanner;
import java.util.TreeMap;

class Solution {
  
  private static void printMedian(MedianHeap m) {
    final double median = m.median();
    System.out.print((median == (long)median) ? String.format("%.0f", median) : String.format("%.1f", median));
  }
  
  public static void main( String args[] ) {
    // helpers for input/output
    Scanner in = new Scanner(System.in);
    
    // reading the number of operations
    final long N = in.nextInt();
    
    MedianHeap m = new MedianHeap();
    char o; // operation code ('a' = add, 'r' = remove)
    long x;  // operation parameter (arbitrary integer)
    
    // reading and processing the operations
    for(long i = 0; i < N; i ++){
      o = in.next().charAt(0);
      x = in.nextInt();
      
      //System.out.println(o + " " + x);
      
      if (o == 'a') {
        m.add(x);
        printMedian(m);
        
      } else if (o == 'r') {
        boolean isRemoved = m.remove(x);
        
        if (isRemoved && m.size() > 0) {
          printMedian(m);
        } else {
          System.out.print("Wrong!");
        }
      }
      
      if (i < N-1) {
        System.out.println();
      }
      
      //System.out.println(m);
    }
  }
}

/**
 * Stores the elements in two approximately balanced
 * priority heaps. The first heap (heapHigh) is dedicated to the 
 * greater elements and ordered based on a min comparator.
 * The second one (called heapLow) is for the smaller elements
 * and ordered based on a max comparator.<br/>
 * Based on the head of the heaps the median can be computed efficiently
 * in each time moment while the add and remove operations also fast.
 */
class MedianHeap {
  long c = 0, cL = 0, cH = 0;  // number of elements in the two heaps, heapLow and heap High respectively
  TreeMap<Long,Integer> heapHigh;  // min heap applying RBTree
  TreeMap<Long,Integer> heapLow;   // max heap applying RBTree
  
  public MedianHeap() {
    heapHigh = new TreeMap<Long,Integer>();
    heapLow = new TreeMap<Long,Integer>();
  }
  
  public void add(long n) {
    // check whether n is greater than the min of heapHigh
    Long minOfHigh = (heapHigh.size() > 0) ? heapHigh.firstKey() : null;
    if (minOfHigh != null && minOfHigh.longValue() < n) {
      // add to heapHigh
      Integer freqOfN = heapHigh.get(n);
      if (freqOfN == null) {
        freqOfN = new Integer(1);
      } else {
        freqOfN = new Integer(1 + freqOfN);
      }
      heapHigh.put(n, freqOfN);
      cH ++;
    } else {
      // add to heapLow
      Integer freqOfN = heapLow.get(n);
      if (freqOfN == null) {
        freqOfN = new Integer(1);
      } else {
        freqOfN = new Integer(1 + freqOfN);
      }
      heapLow.put(n, freqOfN);
      cL ++;
    }
    
    // perform balancing
    balancing();
    
    // increment counter
    c ++;
  }
  
  public long size() {
    return c;
  }
  
  public boolean remove(long x) {
    boolean isRemoved = false;
    
    // remove from heapLow
    Integer freqOfX = heapLow.get(x);
    if (freqOfX != null) {
      if (freqOfX == 1) {
        // remove
        heapLow.remove(x);
      } else {
        // decrease
        heapLow.put(x, freqOfX - 1);
      }
      
      isRemoved = true;
      cL --;
      c --;
    }
    
    if (!isRemoved) {
      // remove from heapHigh
      freqOfX = heapHigh.get(x);
      if (freqOfX != null) {
        if (freqOfX == 1) {
          // remove
          heapHigh.remove(x);
        } else {
          // decrease
          heapHigh.put(x, freqOfX - 1);
        }
        
        isRemoved = true;
        cH --;
        c --;
      }
    }
    
    // balancing the heaps
    balancing();
    
    return isRemoved;
  }
  
  public double median() {
    double m = Double.NaN;  // default value, used when no data in the heaps
    if (c % 2 == 0 && c > 0) {
      m = (heapLow.lastKey() + heapHigh.firstKey()) / 2.0; // average
    } else if (c > 0) {
      m = heapLow.lastKey(); // from low, since it contains more elements (balancing invariant)
    }
    return m;
  }
  
  private void shift(TreeMap<Long,Integer> source, TreeMap<Long,Integer> target, Long shiftingKey) {
    // decrease source
    Integer freqOfShiftingInSource = source.get(shiftingKey);
    if (freqOfShiftingInSource == 1) {
      source.remove(shiftingKey);
    } else {
      source.put(shiftingKey, freqOfShiftingInSource - 1);
    }
    
    // increase target
    Integer freqOfShiftingInTarget = target.get(shiftingKey);
    if (freqOfShiftingInTarget == null) {
      target.put(shiftingKey, 1);
    } else {
      target.put(shiftingKey, 1 + freqOfShiftingInTarget);
    }
  }
  
  private void balancing() {
    // heapLow will contain more elements => simpler median calculation
    if (cL < cH) {
      // move min of heapHigh to heapLow
      shift(heapHigh, heapLow, heapHigh.firstKey());
      cH --;
      cL ++;
    }
    
    // if it became unbalanced => balance it
    if (cL > cH + 1) {
      // move max of heapLow to heapHigh
      shift(heapLow, heapHigh, heapLow.lastKey());
      cL --;
      cH ++;
    }
  }
  
  public String toString() {
    return median() + "\tHL: " + heapLow + "\tsizeHL: " + heapLow.size()  + "\tHH: " + heapHigh + "\tsizeHH: " + heapHigh.size() + "\tc: " + c; 
  }
}
