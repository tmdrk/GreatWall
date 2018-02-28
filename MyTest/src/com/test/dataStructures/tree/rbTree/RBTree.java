package com.test.dataStructures.tree.rbTree;

import com.test.common.util.Assert;

/**
 * 红黑树
 * 红黑树需要满足的五条性质： 
 * 性质一：节点是红色或者是黑色；
 * 性质二：根节点是黑色； 
 * 性质三：每个叶节点（NIL或空节点）是黑色；
 * 性质四：每个红色节点的两个子节点都是黑色的（也就是说不存在两个连续的红色节点）；
 * 性质五：从任一节点到其没个叶节点的所有路径都包含相同数目的黑色节点；
 * http://blog.csdn.net/sun_tttt/article/details/65445754
 * http://blog.csdn.net/yang_yulei/article/details/26066409 可以用2-3树理解红黑树
 * 
 * @author zhoujie
 *
 */
public class RBTree {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    /** 根节点 **/
    private Node root;
    
	public class Node {
		/** key **/
		Integer  key;
		/** value **/
		Object value;
		/** 左节点 **/
		Node leftNode;
		/** 右节点 **/
		Node rightNode;
		/** 父节点 **/
		Node parent;
		/** 是否为红色 **/
		boolean red;
		
		public Node(){
			
		}
		public Node(int key,Object value){
			this(key,value,null,RED);
		}
		public Node(int key,Object value,Node parent){
			this(key,value,parent,RED);
		}
		public Node(int key,Object value,boolean red){
			this(key,value,null,red);
		}
		public Node(int key,Object value,Node parent,boolean red){
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.red = red;
		}
		@Override
		public String toString() {
			return key + ":" + value + ":" + (red?"red":"black");
		}
		
	}
	
	/**
	 * 插入元素
	 * @param key
	 * @param value
	 * @return
	 */
	public Object put(int key,Object value) {
		if(root==null){
			root = new Node(key,value,BLACK);
		}
		Node node = root;
		while(node!=null) {
			if(node.key > key){
				if(node.leftNode==null) {
					Node left = new Node(key,value,node);
					node.leftNode = left;
					//检查添加后节点是否符合红黑树要求
					checkNode(left);
					return null;
				}
				node = node.leftNode;
			}else if(node.key < key){
				if(node.rightNode==null) {
					Node right = new Node(key,value,node);
					node.rightNode = right;
					//检查添加后节点是否符合红黑树要求
					checkNode(right);
					return null;
				}
				node = node.rightNode;
			}else {
				Object o = node.value;
				node.value = value;
				return o;
			}
		}
		return null;
	}
	
	public void checkNode(Node node){
		while(node!=null&&node!=root&&node.parent.red==RED) {
			if(isLeftNode(node.parent)){
				Node uncle = getRelative(node,Relative.uncle);
				if(uncle!=null&&uncle.red==RED) {
					node.parent.red = BLACK;
					uncle.red = BLACK;
					node.parent.parent.red = RED;
					node = getRelative(node,Relative.grandFather);
				}else{
					if(!isLeftNode(node)){
						node = node.parent;
						rotateLeft(node);
					}
					node.parent.red = false;
					node.parent.parent.red = true;
					rotateRight(node.parent.parent);
				}
			}else {
				Node uncle = getRelative(node,Relative.uncle);
				if(uncle!=null&&uncle.red==RED) {
					node.parent.red = BLACK;
					uncle.red = BLACK;
					node.parent.parent.red = RED;
					node = getRelative(node,Relative.grandFather);
				}else{
					if(isLeftNode(node)){
						node = node.parent;
						rotateRight(node);
					}
					node.parent.red = false;
					node.parent.parent.red = true;
					rotateLeft(node.parent.parent);
				}
			}
		}
		root.red = false;
	}
	
	public boolean isRoot(Node node) {
		return node==root?true:false;
	}
	
	public boolean isLeftNode(Node node) {
		if(node.parent==null) {
			throw new RuntimeException("根节点不区分左右！");
		}
		return node.parent.key>node.key?true:false;
	}
	
	/**
	 * 是否包含key
	 * @param key
	 * @return
	 */
	public boolean containsKey(int key) {
		Node node = root;
		while(node!=null) {
			if(node.key > key){
				node = node.leftNode;
			}else if(node.key < key){
				node = node.rightNode;
			}else {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取亲属节点
	 * @param node
	 * @param relationship
	 * @return
	 */
	private Node getRelative(Node node,Relative relationship) {
		Assert.notNull(node, "获取亲属时节点不能为空！");
		
		Node relative = null;
		switch (relationship) {
		case brother:
			Assert.notNull(node.parent, "获取兄弟节点时，父亲节点不能为空！");
			relative = isLeftNode(node)?node.parent.rightNode:node.parent.leftNode;
			break;
		case father:
			relative = node.parent;
			break;
		case uncle:
			Assert.notNull(node.parent, "获取叔父节点时，父亲节点不能为空！");
			Assert.notNull(node.parent.parent, "获取叔父节点时，祖父节点不能为空！");
			relative = isLeftNode(node.parent)?node.parent.parent.rightNode:node.parent.parent.leftNode;
			break;
		case grandFather:
			Assert.notNull(node.parent, "获取祖父节点时，父亲节点不能为空！");
			relative = node.parent.parent;
			break;
		case greatGrandFather:
			Assert.notNull(node.parent, "获取曾祖父节点时，父亲节点不能为空！");
			Assert.notNull(node.parent.parent, "获取曾祖父节点时，祖父节点不能为空！");
			relative = node.parent.parent.parent;
			break;
		}
		return relative;
	}
	
	/**
	 * 亲属关系
	 * @author zhoujie
	 *
	 */
	public enum Relative {  
		brother, father, uncle, grandFather ,greatGrandFather 
	}
	
	/**
	 * 右旋
	 * @param node
	 */
	public void rotateRight(Node node){
		if(node!=null){
			Node l = node.leftNode;
			node.leftNode = l.rightNode;
			if(l.rightNode!=null) {
				l.rightNode.parent = node;
			}
			if(node.parent==null) {
				root = l;
			}else if(node.parent.leftNode==node) {
				node.parent.leftNode = l;
			}else {
				node.parent.rightNode = l;
			}
			l.rightNode = node;
			node.parent = l;
		}
	}
	
	public void rotateLeft(Node node){
		if(node!=null){
			Node r = node.rightNode;
			node.rightNode = r.leftNode;
			if(r.leftNode!=null) {
				r.leftNode.parent = node;
			}
			if(node.parent==null) {
				root = r;
			}else if(node.parent.rightNode==node) {
				node.parent.rightNode = r;
			}else {
				node.parent.leftNode = r;
			}
			r.leftNode = node;
			node.parent = r;
		}
	}
	
	public void printTree( ){
		printTree(root);
	}
	public void printTree(Node node){
		if(node==null)return;  
		if(node==root){
			System.out.print("["+node.toString()+"];");
		}else{
			System.out.print(node.toString()+";");
		}
		printTree(node.leftNode);
		printTree(node.rightNode);
	}
	
	public static void main(String[] args) {
		RBTree rbt = new RBTree();
		int[] data = {20,15,30,16,13,26,35,25,32,38,40};
		for(int i=0;i<data.length;i++){
			rbt.put(data[i],null);
		}
		
		rbt.printTree();
	}
}
