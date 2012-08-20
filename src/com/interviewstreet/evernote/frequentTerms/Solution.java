package com.interviewstreet.evernote.frequentTerms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Solution {
  public static void main(String[] args) {
    try {
      // open stdin as reader
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      
      // counting the frequencies applying a hash map
      final Map<String,Long> frequencies = new HashMap<String,Long>();
      
      // read the number of terms
      String line = in.readLine();
      final long N = Long.parseLong(line);
      
      // read and process terms
      line = in.readLine();
      for (long i = 0; i < N && line != null; i ++) {
        Long origFreq = frequencies.get(line);
        if (origFreq == null) {
          frequencies.put(line, new Long(1));
        } else {
          frequencies.put(line, 1 + origFreq);
        }
        
        // read the next line
        line = in.readLine();
      }
      
      // read k and build top structure
      final int k = Integer.parseInt(line);
      final Solution solution = new Solution();
      final TopK<TermFrequency> topk = solution.new TopK<TermFrequency>(k);
      
      // find top k elements
      for (String term : frequencies.keySet()) {
        Long frequency = frequencies.get(term);
        topk.insert(solution.new TermFrequency(term, frequency));
      }
      
      // show output
      System.out.print(topk);
      
      // close input
      //in.close();
    } catch (IOException e) {
      throw new RuntimeException(e); // dirty solution
    }
  }
  
  private class TermFrequency implements Serializable, Comparable<TermFrequency> {
    private static final long serialVersionUID = -632023940965356L;
    private final String term;
    private final Long frequency;
    
    public TermFrequency(String t, Long f) {
      term = t;
      frequency = f;
    }
    
    public String getTerm() {
      return term;
    }
    
    public Long getFrequency() {
      return frequency;
    }
    
    public String toString() {
      return term;
    }

    @Override
    public int compareTo(TermFrequency o) {
      Long o1 = this.frequency;
      Long o2 = o.frequency;
      if (o1 == null && o2 == null) {
        return 0;
      } else if (o1 == null) {
        return -1;
      } else if (o2 == null) {
        return +1;
      } else if (o1.longValue() < o2.longValue()) {
        return +1;
      } else if (o1.longValue() > o2.longValue()) {
        return -1;
      }
      return term.compareTo(o.term);
    }
  }
  
  private class TopK <T extends Serializable & Comparable<? super T>> implements Serializable {
    private static final long serialVersionUID = 6829986719707246937L;
    private final T[] list;
    private int c = 0;

    /**
     * This is the only constructor of the class. It creates an empty list with bound size length.
     * @param length
     */
    @SuppressWarnings("unchecked")
    public TopK(int length) {
      Serializable[] tmp = new Serializable[length];
      this.list = (T[]) tmp;
    }

    /**
     * Simply returns the size of the list.
     */
    public int size() {
      return Math.min(c, list.length);
    }

    /**
     * Returns the <i>i</i>th element of the current list or null if the index <i>i</i> is out of the size range
     * of the list.
     */
    public T get(int i) {
      if (0 <= i && i < c && i < list.length) {
        return (T) list[i];
      }
      return null;
    }
    
    /**
     * Inserts the element <i>a</i> to the current list considering the ordering defined by the comparable interface.<br/>
     * The time complexity is O(k^2) where k is the size of the list. Finding the top-k elements of n entities
     * can be done in O(k^2*n) which is efficient for small k and it is linear in <i>n</i>.  
     */
    public void insert(T a) {
      boolean isAdded = false;
      if (list.length > 0) {
        if (a != null && c < list.length) {
          // list is not full => we can simply store the new element
          list[c ++] = a;
          isAdded = true;
        } else if (a != null && (list[list.length - 1] == null || list[list.length - 1].compareTo(a) > 0)) {
          // new element is better than the worst in the current list => we have to store the new one
          list[c-1] = a;
          isAdded = true;
        }
        if (isAdded) {
          // repair the ordering of the list
          for (int i = c - 1; i > 0 && (list[i-1] == null || list[i-1].compareTo(a) > 0); i --) {
            //swap
            T tmp = list[i];
            list[i] = list[i-1];
            list[i-1] = tmp;
          }
        }
      }
    }
    
    public String toString() {
      final StringBuffer outBuffer = new StringBuffer();
      final int s = size() - 1;
      for (int i = 0; i < s; i ++) {
        outBuffer.append(get(i)).append('\n');
      }
      if (0 <= s) {
        outBuffer.append(get(s));
      }
      return outBuffer.toString();
    }
  }
}