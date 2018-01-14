package com.test.dataStructures.tree.bst;

import java.util.Comparator;

public class Node<V> implements Comparator<V>{
	V v;
	Node lchild;
	Node rchild;
	public int height;//当前结点的高度
	public Node(V v){
		this.v = v;
	}
	public static void main(String[] args) {
		Node<Integer> node = new Node<Integer>(12);
		System.out.println(node.v.compareTo(12));
	}
	@Override
	public int compare(V o1, V o2) {
		return 0;
	}
	public boolean isExist(Node<V> node,V v){
//		int result=v.compareTo(node.v);
		return false;
	}
}
