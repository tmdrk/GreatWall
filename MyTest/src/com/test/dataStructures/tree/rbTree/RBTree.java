package com.test.dataStructures.tree.rbTree;

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
			return "[" + key + "=" + value + "]";
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
					if(node.red==RED){
						checkNode(left,true);
					}
					return null;
				}
				node = node.leftNode;
			}else if(node.key < key){
				node = node.rightNode;
				if(node.rightNode==null) {
					Node right = new Node(key,value,node);
					node.rightNode = right;
					//检查添加后节点是否符合红黑树要求
					if(node.red==RED){
						checkNode(right,false);
					}
					return null;
				}
			}else {
				Object o = node.value;
				node.value = value;
				return o;
			}
		}
		return null;
	}
	
	public void checkNode(Node node,boolean isLeft){
		if(isLeft){
			Node uncle = node.parent.rightNode;
			if(uncle!=null&&uncle.red==RED) {
				node.red = BLACK;
				uncle.red = BLACK;
			}else{
				
			}
		}else {
			Node uncle = node.parent.leftNode;
			if(uncle!=null&&uncle.red==RED) {
				node.red = BLACK;
				uncle.red = BLACK;
			}else{
				
			}
		}
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
}
