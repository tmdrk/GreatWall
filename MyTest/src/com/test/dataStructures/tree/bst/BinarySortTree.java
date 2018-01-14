package com.test.dataStructures.tree.bst;

import java.util.Random;

import com.test.common.util.Assert;

/**
 * 二叉排序树
 * 二叉排序树（Binary Sort Tree）或者是一棵空树；或者是具有下列性质的二叉树：
 *（1）若左子树不空，则左子树中所有结点的值均小于它的根结点的值；
 *（2）若右子树不空，则右子树中所有结点的值均大于它的根结点的值；
 *（3）左、右子树也分别为二叉排序树；
 * @ClassName: BinarySortTree 
 * @author zhoujie
 * @date 2018年1月9日 上午10:38:56
 */
public class BinarySortTree {
	public BinarySortTree(){
		
	}
	/** 哨兵节点 **/
	Node sentinel = new Node();
	/** 根节点 **/
	Node root;
	/** 树容量 **/
	int size = 0;
	Node tempNode = null;
	public class Node {
		int data;
		Node lchild;
		Node rchild;
		public Node(){
			
		}
		public Node(int v){
			this.data = v;
		}
		public String toString(){
			return "data="+data+" lchild data="+(lchild==null?"null":String.valueOf(lchild.data))+" rchild data="+(rchild==null?"null":String.valueOf(rchild.data));
		}
//		public int compare(int v){
//			if(v>this.data){
//				return 1;
//			}
//			if(v<this.data){
//				return -1;
//			}
//			return 0;
//		}
	}
	public boolean isExist(int v){
		return isExist(root,v);
	}
	public boolean isExist(Node node,int v){
		if(node==null){
			return false;
		}
		if(node.data==v){
			return true;
		}
		return isExist(node.lchild,v)||isExist(node.rchild,v);
	}
	public void insert(int v){
		if(root==null){
			root = new Node(v);
			sentinel.lchild = root;
			size++;
			return;
		}
		insert(root,v);
		size++;
	}
	public void insert(Node node,int v){
		if(node.data>=v){
			if(node.lchild==null){
				node.lchild = new Node(v);
			}else{
				insert(node.lchild,v);
			}
		}else{
			if(node.rchild==null){
				node.rchild = new Node(v);
			}else{
				insert(node.rchild,v);
			}
		}
	}
	public Node delete(int v){
		Assert.notNull(root, "删除的节点不存在");
		Node retNode = delete(root,v);
		if(tempNode!=null){
			sentinel.lchild = tempNode;
			root = tempNode;
		}
		return retNode;
	}
	public Node delete(Node node,int v){
		Assert.notNull(node, "删除的节点不存在");
		Node retNode;
		if(node.data<v){
			retNode = delete(node.rchild,v);
			if(tempNode!=null){
				node.rchild = tempNode;
			}
		}else if(node.data>v){
			retNode = delete(node.lchild,v);
			if(tempNode!=null){
				node.lchild = tempNode;
			}
		}else{
//			System.out.println("正在删除:"+node+"...");
			Node dest = node;
			if(node.lchild==null&&node.rchild==null){
				node=null;
			}
			else if(node.lchild==null&&node.rchild!=null){
				node=node.rchild;
			}
			else if(node.lchild!=null&&node.rchild==null){
				node=node.lchild;
			}
			else if(node.lchild!=null&&node.rchild!=null){
				node = minNode(dest.rchild);
				node.rchild = deleteMin(dest.rchild);
				node.lchild = dest.lchild;
			}
			tempNode = node;
			return dest;
		}
		tempNode = null;
		return retNode;
	}
	private Node minNode(Node node){
		if(node.lchild==null){
			return node;
		}
		return minNode(node.lchild);
	}
	private Node maxNode(Node node){
		if(node.rchild==null){
			return node;
		}
		return maxNode(node.rchild);
	}
	/**
	 * 删除键值最小结点
	 * @param node
	 * @return
	 * @author zhoujie
	 * @date 2018年1月9日 下午5:46:50
	 */
	private Node deleteMin(Node node){
		if(node.lchild==null){
			return node.rchild;
		}
		node.lchild = deleteMin(node.lchild);
		return node;
	}
	/**
	 * 删除键值最大结点
	 * @param node
	 * @return
	 * @author zhoujie
	 * @date 2018年1月9日 下午6:14:27
	 */
	private Node deleteMax(Node node){
		if(node.rchild==null){
			return node.lchild;
		}
		node.rchild = deleteMax(node.rchild);
		return node;
	}
	public void printTree(Node node){
		if(node==null)return;  
		printTree(node.lchild);
		System.out.print(node.data+":");
		printTree(node.rchild);
	}
	public static void main(String[] args) {
		BinarySortTree bst = new BinarySortTree();
//		Random r = new Random();
//		for(int i=0;i<100;i++){
//			int ran = r.nextInt(100);
//			System.out.print(ran+":");
//			bst.insert(ran);
//		}
		int[] data = {30,17,23,20,18,19,15,16,13,36,26};
		for(int i=0;i<data.length;i++){
			System.out.print(data[i]+":");
			bst.insert(data[i]);
		}
		System.out.println("大小:"+bst.size);
		bst.printTree(bst.root);
		System.out.println();
		System.out.println(bst.isExist(23));
		System.out.println(bst.delete(30));
		bst.printTree(bst.root);
	}
}
