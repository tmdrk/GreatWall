package com.test.dataStructures.tree.bst;

@SuppressWarnings({"rawtypes","unchecked"})
public class Node<V> {
	V v;
	Node lchild;
	Node rchild;
	public Node(V v){
		this.v = v;
	}
	
	public int compare(V v){
		return ((Comparable<? super V>)this.v).compareTo(v);
	}
	
	public static void main(String[] args) {
		Node<Integer> node = new Node<Integer>(12);
		System.out.println(node.v.compareTo(12));
		Node<String> node2 = new Node<String>("22");
		System.out.println(node2.v.compareTo("102"));
		Node<Student> node3 = new Node<Student>(new Student("a",83));
		System.out.println(node3.compare(new Student("b",123)));
	}
	
	
}
