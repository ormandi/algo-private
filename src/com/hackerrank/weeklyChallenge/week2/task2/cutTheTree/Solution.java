package com.hackerrank.weeklyChallenge.week2.task2.cutTheTree;

import java.io.*;
import java.util.*;

public class Solution {
  private static enum Color {
    WHITE, GREY, BLACK;
  }
  private static class Node {
    private final int id;
    private int parent;
    private List<Integer> neighbors = new LinkedList<Integer>();
   
    private final int value;
    private int sum;
    private Color color = Color.WHITE;

    private Node(int id, int value) {
      this.id = id;
      this.value = value;
      this.sum = value;
    }
  }
  private static class SimpleStack<T> {
    private final LinkedList<T> list = new LinkedList<T>();

    private void push(T item) {
      list.addFirst(item);
    }
    private T pop() {
      return list.removeFirst();
    }
    private T peek() {
      return list.getFirst();
    }
    private int size() {
      return list.size();
    }
    private boolean isEmpty() {
      return list.isEmpty();
    }
  } 
  
  public static void main(String[] args) throws Exception {
    final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    // reads the input and builds the tree
    final int N = Integer.parseInt(in.readLine());
    final Node[] nodes = new Node[N];
    final String[] valuesStr = in.readLine().split("\\s+");
    for (int i = 0; i < N; i++) {
      nodes[i] = new Node(i, Integer.parseInt(valuesStr[i]));
    }
    for (int i = 0; i < N-1; i++) {
      final String[] edge = in.readLine().split("\\s+");
      final int a = Integer.parseInt(edge[0]) - 1;
      final int b = Integer.parseInt(edge[1]) - 1;
      nodes[a].neighbors.add(b);
      nodes[b].neighbors.add(a);
    }

    // computes sub-tree sums
    final SimpleStack<Integer> stack = new SimpleStack<Integer>();
    nodes[0].color = Color.GREY;
    stack.push(0);
    while (!stack.isEmpty()) {
      boolean forward = false;
      final int currentId = stack.peek();
      final Node current = nodes[currentId];
      for (int childId : current.neighbors) {
        final Node child = nodes[childId];
        if (child.color == Color.WHITE) {
          forward = true;
          child.parent = currentId;
          child.color = Color.GREY;
          stack.push(child.id);
        }
      }
      if (!forward) {
        current.color = Color.BLACK;
        for (int childId : current.neighbors) {
          final Node child = nodes[childId];
          if (child.color == Color.BLACK) {
            current.sum += child.sum;
          }
        }
        stack.pop();
      }
    }
    final int treeSum = nodes[0].sum;
    //System.err.println("1\t" + nodes[0].value + "\t" + nodes[0].sum);
    int min = Integer.MAX_VALUE;
    int minId = 0;
    for (int i = 1; i < N; i++) {
      final int diff = Math.abs(nodes[i].sum - (treeSum - nodes[i].sum));
      if (diff < min) {
        min = diff;
        minId = i;
      }
      //System.err.println((i + 1) + "\t" + nodes[i].value + "\t" + nodes[i].sum + "\t" + diff + "\t" + (nodes[i].parent + 1) + "-" + (i + 1));
    }
    System.out.println(min);
    in.close();
  }
}
