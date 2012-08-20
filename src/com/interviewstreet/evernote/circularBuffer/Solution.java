package com.interviewstreet.evernote.circularBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Solution {
  public static void main(String[] args) {
    try {
      // open stdin as reader
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      StringWriter output = new StringWriter();
      PrintWriter out = new PrintWriter(output);
      
      // read buffer size
      final int size = Integer.parseInt(in.readLine());
      
      // create buffer
      final Solution solutionInstance = new Solution();
      final CircularBuffer buffer = solutionInstance.new CircularBuffer(size);
      
      // read input line by line
      String line = in.readLine();
      while (line != null) {
        // parse command
        if (line.startsWith("A") || line.startsWith("R")) {
          // process add and remove command
          String[] splited = line.split(" ");
          final char o = line.charAt(0);
          final int n = Integer.parseInt(splited[1]);
          
          // perform operations n times (parameter of the operation)
          for (int i = 0; i < n; i ++) {
            if (o == 'A') {
              // read and add to the buffer
              buffer.offer(in.readLine());
            } else if (o == 'R') {
              // remove from the buffer
              buffer.poll();
            }
          }
        } else if (line.startsWith("L")) {
          // print buffer
          out.print(buffer.toString());
        } else if (line.startsWith("Q")) {
          // quit
          break;
        }
        
        // read the next line
        line = in.readLine();
      }
      
      // show output
      StringBuffer outBuffer = output.getBuffer();
      if (outBuffer.length() > 0) {
        outBuffer.deleteCharAt(outBuffer.length() - 1);
      }
      System.out.print(outBuffer.toString());
      
      // close output and input
      out.close();
      //in.close();
    } catch (IOException e) {
      throw new RuntimeException(e); // dirty solution
    }
  }
  
  /**
   * It wrappers an queue and keeps its size bounded.<br/>
   * Applying linked list as queue makes the time complexity of add and remove operations O(1)
   * while supporting iteration makes the implementation of toString easy and natural.
   * I decided to make a dedicated iterator to keep the queue property of the buffer
   * by hiding the random remove operation in the original queue iterator.
   */
  private class CircularBuffer  implements Iterable<String> {
    private final Queue<String> q;
    private final int size;
    
    public CircularBuffer(int k) {
      size = k;
      q = new LinkedList<String>();
    }
    
    public int size() {
      return Math.min(size, q.size());
    }
    
    public Iterator<String> iterator() {
      return new CircularBufferIterator(q.iterator());
    }
    
    // O(1)
    public boolean offer(String e) {
      if (q.size() >= size) {
        q.poll();
      }
      q.offer(e);
      return true;
    }
    
    // O(1)
    public String poll() {
      return q.poll();
    }
    
    // O(size)
    public String toString() {
      StringBuffer out = new StringBuffer();
      for (String e : this) {
        out.append(e).append('\n');
      }
      return out.toString();
    }
  }

  private class CircularBufferIterator implements Iterator<String> {
    private final Iterator<String> origIter;
    
    public CircularBufferIterator(Iterator<String> iter) {
      origIter = iter;
    }
    
    public boolean hasNext() {
      return origIter.hasNext();
    }
    
    public String next() {
      return origIter.next();
    }
    
    public void remove() {
      throw new RuntimeException("Remove is not supported through iterator!");
    }
  }
}