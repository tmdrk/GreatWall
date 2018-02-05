package com.test.dataStructures.tree.BTree;

import java.util.Arrays;

import com.test.dataStructures.tree.BTree.BUnderscodeTree.Node;

/**
 * B+树
 * 在实际的文件系统中，用的是B+树或其变形。有关性质与操作类似与B_树。
 * 1、差异：
 *（1）有n棵子树的结点中有n个关键字
 *（2）所有叶子结点中包含全部关键字信息及对应记录位置信息
 *（3）所有非叶子为索引，只含关键字而且仅有子树中最大或最小的信息。
 *（4）非叶最底层顺序联结，这样可以进行顺序查找
 * @author zhoujie
 *
 */
public class BAdditionTree {
	public BAdditionTree(){
		this(3);
	}
	public BAdditionTree(int m){
		this.m=m;
	}
	/** 哨兵 **/
	Node sentinel = new Node();
	/** 叶子节点的链表头 */  
	Node head = new Node();
	/** 树容量 **/
	int size = 0;
	/** B树的阶数 **/
	public final int m;
	
	public class Node {
		/** 关键字个数 **/
		int keyNumber;
		/** 数据 **/
		Integer[] data;
		/** 子节点 **/
		Node[] children;
		/** 下一兄弟节点 **/
		Node[] nextNode;
		
		public Node(){
		}
		public Node(int v){
			Integer[] d = new Integer[m+1];
			d[0] = v;
			this.keyNumber = 1;
			this.data = d;
		}
		public Node(Integer[] data,int keyNumber){
			this(data,keyNumber,null);
		}
		public Node(Integer[] data,int keyNumber,Node[] children){
			this.keyNumber = keyNumber;
			this.data = data;
			this.children = children;
		}
		
		public int getKeyNumber() {
			return keyNumber;
		}
		public void setKeyNumber(int keyNumber) {
			this.keyNumber = keyNumber;
		}
		public Integer[] getData() {
			return data;
		}
		public void setData(Integer[] data) {
			this.data = data;
		}
		public Node[] getChildren() {
			return children;
		}
		public void setChildren(Node[] children) {
			this.children = children;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Node [keyNumber=" + keyNumber + ", data=" + Arrays.toString(data) + ", isLeft="+ (children==null?"true":"false") + "]";
		}
		
	}
	
	
	public static void main(String[] args) {
		
	}
}
