package com.test.dataStructures.tree.rbTree;

import java.util.Random;

import com.test.common.util.Assert;

/**
 * 红黑树
 * 红黑树需要满足的五条性质： 
 * 性质一：节点是红色或者是黑色；
 * 性质二：根节点是黑色； 
 * 性质三：每个叶节点（NIL或空节点）是黑色；
 * 性质四：每个红色节点的两个子节点都是黑色的（也就是说不存在两个连续的红色节点）；
 * 性质五：从任一节点到其没个叶节点的所有路径都包含相同数目的黑色节点；
 * http://blog.csdn.net/sun_tttt/article/details/65445754  	新增删除规则
 * http://blog.chinaunix.net/uid-26548237-id-3480169.html   图解新增删除
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
    
    /** 大小 **/
    private int size;
    
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
			return key + ":" + value + ":" + (red?"R":"B");
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
			size++;
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
					size++;
					return null;
				}
				node = node.leftNode;
			}else if(node.key < key){
				if(node.rightNode==null) {
					Node right = new Node(key,value,node);
					node.rightNode = right;
					//检查添加后节点是否符合红黑树要求
					checkNode(right);
					size++;
					return null;
				}
				node = node.rightNode;
			}else {
				Object o = node.value;
				node.value = value;
				return o;
			}
		}
		size++;
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
//		try {
			if(node!=null){
				Node l = node.leftNode;
				node.leftNode = l.rightNode;
				if(l.rightNode!=null) {
					l.rightNode.parent = node;
				}
				l.parent = node.parent;
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
//		} catch (Exception e) {
//			System.out.println("11");
//		}
	}
	
	public void rotateLeft(Node node){
//		try {
			if(node!=null){
				Node r = node.rightNode;
				node.rightNode = r.leftNode;
				if(r.leftNode!=null) {
					r.leftNode.parent = node;
				}
				r.parent = node.parent;
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
//		} catch (Exception e) {
//			System.out.println("11");
//		}
	}
	
	/**
	 * 删除元素
	 * @param key
	 * @return
	 */
	public Object remove(int key) {
		Node node = findNode(key);
		if(node == null) {
			return null;
		}

		if(isFull(node)) {
			//左右节点都不为空时，找到当前节点的后继节点，将后继节点key和value赋给当前节点，然后做删除后继节点操作
			Node successor = findSuccessorNode(node);
			copyKV(successor,node);
			node = successor;
		}
		
		if(isLeaf(node)) {
			if(!isRoot(node)) {
				checkDeletion(node);
			}
		}else if(node.leftNode==null){
			node.rightNode.red = BLACK;
		}else{
			node.leftNode.red = BLACK;
		}
		removeUnfullNode(node);
		size--;
		return node.value;
	}
	
	public void checkDeletion(Node node){
		while (node != root && node.red == BLACK) {
			Node brother = getRelative(node, Relative.brother);
			if(isLeftNode(node)) {
				//待删除的节点的兄弟节点是红色的节点；
				if(brother.red==RED){
					brother.red = BLACK;
					node.parent.red = RED;
					rotateLeft(node.parent);
					brother = getRelative(node, Relative.brother);
				}
				if(isBLACK(brother.leftNode)&&isBLACK(brother.rightNode)) {
					brother.red = RED;
					node = node.parent;
				}else {
					if(isBLACK(brother.rightNode)) {
						brother.leftNode.red = BLACK;
						brother.red = RED;
						rotateRight(brother);
						brother = getRelative(node, Relative.brother);
					}
					brother.red = node.parent.red;
					node.parent.red = BLACK;
					brother.rightNode.red = BLACK;
					rotateLeft(brother.parent);
					node = root;
				}
			}else {
				//待删除的节点的兄弟节点是红色的节点；
				if(brother.red==RED){
					brother.red = BLACK;
					node.parent.red = RED;
					rotateRight(node.parent);
					brother = getRelative(node, Relative.brother);
				}
				if(isBLACK(brother.leftNode)&&isBLACK(brother.rightNode)) {
					//待删除的节点的兄弟节点是黑色的节点，且兄弟节点的子节点都是黑色的;
					brother.red = RED;
					node = node.parent;
				}else {
					if(isBLACK(brother.leftNode)) {
						//待删除的节点的兄弟节点是黑色的节点，且兄弟节点右子节点为黑（空）
						brother.rightNode.red = BLACK;
						brother.red = RED;
						rotateLeft(brother);
						brother = getRelative(node, Relative.brother);
					}
					//待删除的节点的兄弟节点是黑色的节点，且兄弟节点右子节点为红色
					brother.red = node.parent.red;
					node.parent.red = BLACK;
					brother.leftNode.red = BLACK;
					rotateRight(brother.parent);
					node = root;
				}
			}
		}
		node.red = BLACK;
	}
	
	/**
	 * 查找节点
	 * @param key
	 * @return
	 */
	public Node findNode(int key) {
		Node node = root;
		while(node!=null) {
			if(node.key > key) {
				node = node.leftNode;
			}else if(node.key < key) {
				node = node.rightNode;
			}else {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * 获取后继节点
	 * @param key
	 * @return
	 */
	public Node findSuccessorNode(Node node) {
		Assert.notNull(node, "获取后继节点时，节点不能为空！");
		if(node.rightNode==null) {
			return null;
		}
		node = node.rightNode;
		while(node.leftNode!=null) {
			node = node.leftNode;
		}
		return node;
	}

	/**
	 * 获取前继节点
	 * @param node
	 * @return
	 */
	public Node findFollowingAgoNode(Node node) {
		Assert.notNull(node, "获取前继节点时，节点不能为空！");
		if(node.leftNode==null) {
			return null;
		}
		node = node.leftNode;
		while(node.rightNode!=null) {
			node = node.rightNode;
		}
		return node;
	}
	
	/**
	 * 复制key value到目的节点
	 * @param ori
	 * @param des
	 */
	public void copyKV(Node ori,Node des) {
		des.key = ori.key;
		des.value = ori.value;
	}
	
	/**
	 * 删除节点（该节点需满足不同时含有左右子树）
	 * @param node
	 */
	public void removeUnfullNode(Node node){
		if(node==root) {
			if(node.leftNode==null) {
				root = node.rightNode;
			}else {
				root = node.leftNode;
			}
		}else {
			if(isLeftNode(node)) {
				if(node.leftNode==null) {
					node.parent.leftNode = node.rightNode;
					if(node.rightNode!=null) {
						node.rightNode.parent = node.parent;
					}
				}else {
					node.parent.leftNode = node.leftNode;
					node.leftNode.parent = node.parent;
				}
			}else {
				if(node.leftNode==null) {
					node.parent.rightNode = node.rightNode;
					if(node.rightNode!=null) {
						node.rightNode.parent = node.parent;
					}
				}else {
					node.parent.rightNode = node.leftNode;
					node.leftNode.parent = node.parent;
				}
			}
		}
	}
	
	/**
	 * 是否拥有两个子节点
	 * @param node
	 * @return
	 */
	public boolean isFull(Node node) {
		if(node.leftNode!=null&&node.rightNode!=null) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是叶子
	 * @param node
	 * @return
	 */
	public boolean isLeaf(Node node) {
		if(node.leftNode==null&&node.rightNode==null) {
			return true;
		}
		return false;
	}
	
	public boolean isBLACK(Node node) {
		if(node==null||node.red==BLACK) {
			return true;
		}
		return false;
	}
	
	public void printTree( ){
		printTree(root);
		System.out.println();
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
		int length = 20;
		int[] data = new int[length];
		Random r = new Random();
		for(int i=0;i<length;i++){
			int a = r.nextInt(length*10);
			data[i]= a;
			System.out.print(a+",");
		}
		System.out.println();
//		int[] data = {20,15,30,16,13,26,35,25,32,38,40};
//		int[] data = {118,112,171,0,33,127,11,57,9,132,95,66,100,196,24,149,5,113,131,128};
		for(int i=0;i<data.length;i++){
			rbt.put(data[i],"v"+data[i]);
		}
		rbt.printTree();
		for(int i=0;i<data.length;i++){
			System.out.println(data[i]+"-->"+rbt.remove(data[i])+" size="+rbt.size);
//			rbt.printTree();
		}
		rbt.printTree();
	}
}
