package com.test.dataStructures.tree.avl;

import com.test.common.util.Assert;

/**
 * 平衡二叉树
 * @ClassName: AVLTree 
 * @author zhoujie
 * @date 2018年1月11日 下午3:53:08
 */
public class AVLTree {
	public AVLTree(){
		
	}
	/** 哨兵节点 **/
	Node sentinel = new Node();
	/** 树容量 **/
	int size = 0;
	public class Node {
		int data;
		Node lchild;
		Node rchild;
		public int balance;//平衡因子
		public Node(){
			this(0);
		}
		public Node(int v){
			this(null,null,v);
		}
		public Node(Node lchild,Node rchild,int v){
			this(lchild,rchild,v,0);
		}
		public Node(Node lchild,Node rchild,int v,int balance){
			this.lchild = lchild;
			this.rchild = rchild;
			this.data = v;
			this.balance = balance;
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
	public Node getRoot(){
		return sentinel.lchild;
	}
	public boolean isExist(int v){
		return isExist(getRoot(),v);
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
	boolean needChange=true;//是否需要调整上级平衡因子
	public boolean insert(int v){
		size++;
		needChange=true;
		return insert(sentinel,true,v);
	}
	public boolean insert(Node parent,Boolean isLeft,int v){
		if(isLeft){
			if(parent.lchild==null){
				parent.lchild = new Node(v);
				parent.balance = parent.balance+1;
				return true;
			}else{
				boolean result = false;
				if(parent.lchild.data>=v){
					result = insert(parent.lchild,true,v);
				}else{
					result = insert(parent.lchild,false,v);
				}
				if(parent.lchild.balance==2){
					leftBalance(parent, isLeft);
				}else if(parent.lchild.balance==-2){
					rightBalance(parent, isLeft);
				}
				if(needChange){
					if(parent.lchild.balance!=0){
						parent.balance=parent.balance+Math.abs(parent.lchild.balance);
					}else{
						/********如果插入后父节点平衡因子为0，则递归返回都不用再调整*******/
						needChange = false;
					}
				}
				return result;
			}
		}else{
			if(parent.rchild==null){
				parent.rchild = new Node(v);
				parent.balance = parent.balance-1;
				return true;
			}else{
				boolean result = false;
				if(parent.rchild.data>=v){
					result = insert(parent.rchild,true,v);
				}else{
					result = insert(parent.rchild,false,v);
				}
				if(parent.rchild.balance==2){
					leftBalance(parent,isLeft);
				}else if(parent.rchild.balance==-2){
					rightBalance(parent,isLeft);
				}
				if(needChange){
					if(parent.rchild.balance!=0){
						parent.balance=parent.balance-Math.abs(parent.rchild.balance);
					}else{
						/********如果插入后父节点平衡因子为0，则递归返回都不用再调整*******/
						needChange = false;
					}
				}
				return result;
			}
		}
	}

	public Node delete(int v){
		size--;
		Node retNode = delete(sentinel,true,v);
		return retNode;
	}
	/**
	 * 删除节点
	 * @param parent 父节点
	 * @param isleft 是否是左节点
	 * @param v
	 * @return
	 * @author zhoujie
	 * @date 2018年1月18日 下午5:57:18
	 */
	public Node delete(Node parent,boolean isleft,int v){
		Node retNode = null;
		Node node;
		if(isleft){
			Assert.notNull(parent.lchild, "删除的节点不存在");
			node = parent.lchild;
			if(node.data>v){
				retNode = delete(parent.lchild,true,v);
			}else if(node.data<v){
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
			if(node.data>v){
				retNode = delete(parent.rchild,true,v);
			}else if(node.data<v){
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
	/**
	 * 求树高
	 * @return
	 * @author zhoujie
	 * @date 2018年1月18日 下午6:01:39
	 */
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
	public int leftBalance(Node parent,Boolean isLeft){
		if(isLeft){
			if(parent.lchild.lchild.balance==1){
				/*********** 调节平衡因子 **********/
				parent.lchild.balance = parent.lchild.balance - 2;
				parent.lchild.lchild.balance = parent.lchild.lchild.balance - 1;
				/*********** 调节节点位置 **********/
				Node temp = parent.lchild.lchild;
				parent.lchild.lchild = temp.rchild;
				temp.rchild = parent.lchild;
				parent.lchild = temp;
				
			}else if(parent.lchild.lchild.balance==-1){
				/*********** 调节平衡因子 **********/
				if(parent.lchild.lchild.rchild.balance==0){
					parent.lchild.balance = parent.lchild.balance - 2;
					parent.lchild.lchild.balance = parent.lchild.lchild.balance + 1;
				}else if(parent.lchild.lchild.rchild.balance==1){
					parent.lchild.balance = parent.lchild.balance - 3;
					parent.lchild.lchild.balance = parent.lchild.lchild.balance + 1;
					parent.lchild.lchild.rchild.balance = parent.lchild.lchild.rchild.balance - 1;
				}else if(parent.lchild.lchild.rchild.balance==-1){
					parent.lchild.balance = parent.lchild.balance - 2;
					parent.lchild.lchild.balance = parent.lchild.lchild.balance + 2;
					parent.lchild.lchild.rchild.balance = parent.lchild.lchild.rchild.balance + 1;
				}
				/*********** 调节节点位置 **********/
				Node temp = parent.lchild.lchild.rchild;
				parent.lchild.lchild.rchild = temp.lchild;
				temp.lchild = parent.lchild.lchild;
				parent.lchild.lchild = temp.rchild;
				temp.rchild = parent.lchild;
				parent.lchild = temp;
			}else if(parent.lchild.lchild.balance==0){
				/*********** 调节平衡因子 **********/
				parent.lchild.balance = parent.lchild.balance - 1;
				parent.lchild.lchild.balance = parent.lchild.lchild.balance - 1;
				/*********** 调节节点位置 **********/
				Node temp = parent.lchild.lchild;
				parent.lchild.lchild = temp.rchild;
				temp.rchild = parent.lchild;
				parent.lchild = temp;
			}
			return parent.lchild.balance;
		}else{
			if(parent.rchild.lchild.balance==1){
				/*********** 调节平衡因子 **********/
				parent.rchild.balance = parent.rchild.balance - 2;
				parent.rchild.lchild.balance = parent.rchild.lchild.balance - 1;
				/*********** 调节节点位置 **********/
				Node temp = parent.rchild.lchild;
				parent.rchild.lchild = temp.rchild;
				temp.rchild = parent.rchild;
				parent.rchild = temp;
				
			}else if(parent.rchild.lchild.balance==-1){
				/*********** 调节平衡因子 **********/
				if(parent.rchild.lchild.rchild.balance==0){
					parent.rchild.balance = parent.rchild.balance - 2;
					parent.rchild.lchild.balance = parent.rchild.lchild.balance + 1;
				}else if(parent.rchild.lchild.rchild.balance==1){
					parent.rchild.balance = parent.rchild.balance - 3;
					parent.rchild.lchild.balance = parent.rchild.lchild.balance + 1;
					parent.rchild.lchild.rchild.balance = parent.rchild.lchild.rchild.balance - 1;
				}else if(parent.rchild.lchild.rchild.balance==-1){
					parent.rchild.balance = parent.rchild.balance - 2;
					parent.rchild.lchild.balance = parent.rchild.lchild.balance + 2;
					parent.rchild.lchild.rchild.balance = parent.rchild.lchild.rchild.balance + 1;
				}
				/*********** 调节节点位置 **********/
				Node temp = parent.rchild.lchild.rchild;
				parent.rchild.lchild.rchild = temp.lchild;
				temp.lchild = parent.rchild.lchild;
				parent.rchild.lchild = temp.rchild;
				temp.rchild = parent.rchild;
				parent.rchild = temp;
			}else if(parent.rchild.lchild.balance==0){
				/*********** 调节平衡因子 **********/
				parent.rchild.balance = parent.rchild.balance - 1;
				parent.rchild.lchild.balance = parent.rchild.lchild.balance - 1;
				/*********** 调节节点位置 **********/
				Node temp = parent.rchild.lchild;
				parent.rchild.lchild = temp.rchild;
				temp.rchild = parent.rchild;
				parent.rchild = temp;
			}
			return parent.rchild.balance;
		}
	}
	
	public int rightBalance(Node parent,Boolean isLeft){
		if(isLeft){
			if(parent.lchild.rchild.balance==-1){
				/*********** 调节平衡因子 **********/
				parent.lchild.balance = parent.lchild.balance + 2;
				parent.lchild.rchild.balance = parent.lchild.rchild.balance + 1;
				/*********** 调节节点位置 **********/
				Node temp = parent.lchild.rchild;
				parent.lchild.rchild = temp.lchild;
				temp.lchild = parent.lchild;
				parent.lchild = temp;
				
			}else if(parent.lchild.rchild.balance==1){
				/*********** 调节平衡因子 **********/
				if(parent.lchild.rchild.lchild.balance==0){
					parent.lchild.balance = parent.lchild.balance + 2;
					parent.lchild.rchild.balance = parent.lchild.rchild.balance - 1;
				}else if(parent.lchild.rchild.lchild.balance==1){
					parent.lchild.balance = parent.lchild.balance + 2;
					parent.lchild.rchild.balance = parent.lchild.rchild.balance - 2;
					parent.lchild.rchild.lchild.balance = parent.lchild.rchild.lchild.balance - 1;
				}else if(parent.lchild.rchild.lchild.balance==-1){
					parent.lchild.balance = parent.lchild.balance + 3;
					parent.lchild.rchild.balance = parent.lchild.rchild.balance - 1;
					parent.lchild.rchild.lchild.balance = parent.lchild.rchild.lchild.balance + 1;
				}
				/*********** 调节节点位置 **********/
				Node temp = parent.lchild.rchild.lchild;
				parent.lchild.rchild.lchild = temp.rchild;
				temp.rchild = parent.lchild.rchild;
				parent.lchild.rchild = temp.lchild;
				temp.lchild = parent.lchild;
				parent.lchild = temp;
			}else if(parent.lchild.rchild.balance==0){
				/*********** 调节平衡因子 **********/
				parent.lchild.balance = parent.lchild.balance + 1;
				parent.lchild.rchild.balance = parent.lchild.rchild.balance + 1;
				/*********** 调节节点位置 **********/
				Node temp = parent.lchild.rchild;
				parent.lchild.rchild = temp.lchild;
				temp.lchild = parent.lchild;
				parent.lchild = temp;
			}
			return parent.lchild.balance;
		}else{
			if(parent.rchild.rchild.balance==-1){
				/*********** 调节平衡因子 **********/
				parent.rchild.balance = parent.rchild.balance + 2;
				parent.rchild.rchild.balance = parent.rchild.rchild.balance + 1;
				/*********** 调节节点位置 **********/
				Node temp = parent.rchild.rchild;
				parent.rchild.rchild = temp.lchild;
				temp.lchild = parent.rchild;
				parent.rchild = temp;
				
			}else if(parent.rchild.rchild.balance==1){
				/*********** 调节平衡因子 **********/
				if(parent.rchild.rchild.lchild.balance==0){
					parent.rchild.balance = parent.rchild.balance + 2;
					parent.rchild.rchild.balance = parent.rchild.rchild.balance - 1;
				}else if(parent.rchild.rchild.lchild.balance==1){
					parent.rchild.balance = parent.rchild.balance + 2;
					parent.rchild.rchild.balance = parent.rchild.rchild.balance - 2;
					parent.rchild.rchild.lchild.balance = parent.rchild.rchild.lchild.balance - 1;
				}else if(parent.rchild.rchild.lchild.balance==-1){
					parent.rchild.balance = parent.rchild.balance + 3;
					parent.rchild.rchild.balance = parent.rchild.rchild.balance - 1;
					parent.rchild.rchild.lchild.balance = parent.rchild.rchild.lchild.balance + 1;
				}
				/*********** 调节节点位置 **********/
				Node temp = parent.rchild.rchild.lchild;
				parent.rchild.rchild.lchild = temp.rchild;
				temp.rchild = parent.rchild.rchild;
				parent.rchild.rchild = temp.lchild;
				temp.lchild = parent.rchild;
				parent.rchild = temp;
			}else if(parent.rchild.rchild.balance==0){
				/*********** 调节平衡因子 **********/
				parent.rchild.balance = parent.rchild.balance + 1;
				parent.rchild.rchild.balance = parent.rchild.rchild.balance + 1;
				/*********** 调节节点位置 **********/
				Node temp = parent.rchild.rchild;
				parent.rchild.rchild = temp.lchild;
				temp.lchild = parent.rchild;
				parent.rchild = temp;
			}
			return parent.rchild.balance;
		}
	}
	
	public void printTree(Node node){
		if(node==null)return;  
		printTree(node.lchild);
		if(node.data==getRoot().data){
			System.out.print("["+node.data+":"+node.balance+"];");
		}else{
			System.out.print(node.data+":"+node.balance+";");
		}
		printTree(node.rchild);
	}
	public static void main(String[] args) {
		AVLTree bst = new AVLTree();
		int[] data = {30,17,23,20,18,19,15,16,13,36,26,35};
//		int[] data = {30,17,23,20,15,22};
		for(int i=0;i<data.length;i++){
			System.out.print(data[i]+":");
			bst.insert(data[i]);
		}
		System.out.println("大小:"+bst.size);
		bst.printTree(bst.getRoot());
		System.out.println();
		System.out.println(bst.isExist(23));
		System.out.println(bst.delete(13));
		bst.printTree(bst.getRoot());
		System.out.println();
	}
}
