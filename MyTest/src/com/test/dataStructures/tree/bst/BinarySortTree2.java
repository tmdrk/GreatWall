package com.test.dataStructures.tree.bst;

import com.test.common.util.Assert;

@SuppressWarnings({"rawtypes","hiding","unchecked"})
public class BinarySortTree2<E> {
	public BinarySortTree2(){
		
	}
	/** 哨兵节点 **/
	Node sentinel = new Node();
	/** 树容量 **/
	int size = 0;
	public class Node<E> {
		E data;
		Node lchild;
		Node rchild;
		public int height;//当前结点的高度
		public Node(){
			this(null);
		}
		public Node(E data){
			this(null,null,data);
		}
		public Node(Node lchild,Node rchild,E data){
			this(lchild,rchild,data,0);
		}
		public Node(Node lchild,Node rchild,E data,int height){
			this.lchild = lchild;
			this.rchild = rchild;
			this.data = data;
			this.height = height;
		}
		public String toString(){
			return "data="+data+" lchild data="+(lchild==null?"null":String.valueOf(lchild.data))+" rchild data="+(rchild==null?"null":String.valueOf(rchild.data));
		}
		public int compare(E data){
			return ((Comparable<? super E>)this.data).compareTo(data);
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
	public Node getRoot(){
		return sentinel.lchild;
	}
	public boolean isExist(E v){
		return isExist(getRoot(),v);
	}
	public boolean isExist(Node node,E v){
		if(node==null){
			return false;
		}
		if(node.compare(v)==0){
			return true;
		}
		return isExist(node.lchild,v)||isExist(node.rchild,v);
	}
	public boolean insert(E v){
		size++;
		return insert(sentinel,true,v);
	}
	public boolean insert(Node parent,Boolean isLeft,E v){
		if(isLeft){
			if(parent.lchild==null){
				parent.lchild = new Node(v);
				return true;
			}else{
				if(parent.lchild.compare(v)>=0){
					return insert(parent.lchild,true,v);
				}else{
					return insert(parent.lchild,false,v);
				}
			}
		}else{
			if(parent.rchild==null){
				parent.rchild = new Node(v);
				return true;
			}else{
				if(parent.rchild.compare(v)>=0){
					return insert(parent.rchild,true,v);
				}else{
					return insert(parent.rchild,false,v);
				}
			}
		}
//		if(node.data>=v){
//			if(node.lchild==null){
//				node.lchild = new Node(v);
//				return true;
//			}else{
//				return insert(node.lchild,v);
//			}
//		}else{
//			if(node.rchild==null){
//				node.rchild = new Node(v);
//				return true;
//			}else{
//				return insert(node.rchild,v);
//			}
//		}
	}
	public void add(E v){
		add(sentinel,true,v);
	}
	public void add(Node parent,boolean isleft,E v){
		
	}
	public Node delete(E v){
		size--;
		Node retNode = delete(sentinel,true,v);
		return retNode;
	}
	public Node delete(Node parent,boolean isleft,E v){
		Node retNode = null;
		Node node;
		if(isleft){
			Assert.notNull(parent.lchild, "删除的节点不存在");
			node = parent.lchild;
			if(node.compare(v)>0){
				retNode = delete(parent.lchild,true,v);
			}else if(node.compare(v)<0){
				retNode = delete(parent.lchild,false,v);
			}else{
				if(node.lchild!=null&&node.rchild!=null){
					Node temp = delMinNode(node);
					temp.lchild = parent.lchild.lchild;
					temp.rchild = parent.lchild.rchild;
					parent.lchild = temp;
					retNode = node;
				}else{
					if(node.lchild==null){
						parent.lchild = node.rchild;
					}else{
						parent.lchild = node.lchild;
					}
					retNode = node;
				}
			}
		}else{
			Assert.notNull(parent.rchild, "删除的节点不存在");
			node = parent.rchild;
			if(node.compare(v)>0){
				retNode = delete(parent.rchild,true,v);
			}else if(node.compare(v)<0){
				retNode = delete(parent.rchild,false,v);
			}else{
				if(node.lchild!=null&&node.rchild!=null){
					Node temp = delMinNode(node);
					temp.lchild = parent.rchild.lchild;
					temp.rchild = parent.rchild.rchild;
					parent.rchild = temp;
					retNode = node;
				}else{
					if(node.lchild==null){
						parent.rchild = node.rchild;
					}else{
						parent.rchild = node.lchild;
					}
					retNode = node;
				}
			}
		}
		return retNode;
	}
	/**
	 * 返回该节点最左子树
	 * @param node
	 * @return
	 * @author zhoujie
	 * @date 2018年1月12日 下午5:46:34
	 */
	private Node minNode(Node node){
		if(node.lchild==null){
			return node;
		}
		return minNode(node.lchild);
	}
	/**
	 * 删除该节点的后继节点，并返回被删除的节点
	 * @param node
	 * @return
	 * @author zhoujie
	 * @date 2018年1月12日 下午1:54:42
	 */
	private Node delMinNode(Node node){
		if(node.rchild.lchild==null){
			Node temp = node.rchild;
			node.rchild = temp.rchild;
			return temp;
		}
		return delMinNode_(node.rchild);
	}
	private Node delMinNode_(Node node){
		if(node.lchild.lchild==null){
			Node temp = node.lchild;
			node.lchild = temp.rchild;
			return temp;
		}
		return delMinNode_(node.lchild);
	}
	/**
	 * 返回该节点最右子树
	 * @param node
	 * @return
	 * @author zhoujie
	 * @date 2018年1月12日 下午5:49:18
	 */
	private Node maxNode(Node node){
		if(node.rchild==null){
			return node;
		}
		return maxNode(node.rchild);
	}
	/**
	 * 删除键值最小结点，并返回新节点
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
	 * 删除键值最大结点，并返回新节点
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
	public int height(){
		return height(getRoot());
	}
	private int height(Node node){
		if(node==null){
			return 0;
		}else{
			int rh = height(node.rchild);
			int lh = height(node.lchild);
			if(rh>lh){
				return rh+1;
			}else{
				return lh+1;
			}
		}
	}
	public void printTree(Node node){
		if(node==null)return;  
		printTree(node.lchild);
		if(node.data==getRoot().data){
			System.out.print("["+node.data+"]:");
		}else{
			System.out.print(node.data+":");
		}
		printTree(node.rchild);
	}
//	public static void main(String[] args) {
//		BinarySortTree2 bst = new BinarySortTree2();
//		int[] data = {30,17,23,20,18,19,15,16,13,36,26,35};
//		for(int i=0;i<data.length;i++){
//			System.out.print(data[i]+":");
//			bst.insert(data[i]);
//		}
//		System.out.println("大小:"+bst.size);
//		bst.printTree(bst.getRoot());
//		System.out.println();
//		System.out.println(bst.isExist(23));
//		System.out.println(bst.delete(30));
//		bst.printTree(bst.getRoot());
//		System.out.println();
//	}
	public static void main(String[] args) {
		BinarySortTree2<String> bst = new BinarySortTree2<String>();
		String[] data = {"30","17","23","20","18","19","15","16","13","36","26","35","202","102","305"};
		for(int i=0;i<data.length;i++){
			System.out.print(data[i]+":");
			bst.insert(data[i]);
		}
		System.out.println("大小:"+bst.size);
		bst.printTree(bst.getRoot());
		System.out.println();
		System.out.println(bst.isExist("23"));
		System.out.println(bst.delete("30"));
		bst.printTree(bst.getRoot());
		System.out.println();
	}
}
