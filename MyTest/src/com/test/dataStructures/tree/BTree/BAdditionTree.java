package com.test.dataStructures.tree.BTree;

import java.util.Arrays;

import com.test.common.util.Assert;

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
		/** 关键字数组 **/
		Integer[] key;
		/** 数据数组 **/
		Object[] data;
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
			this.key = d;
		}
		public Node(Integer[] key,int keyNumber){
			this(key,keyNumber,null);
		}
		public Node(Integer[] key,int keyNumber,Node[] children){
			this(key,keyNumber,null,children);
		}
		public Node(Integer[] key,int keyNumber,Object[] data){
			this(key,keyNumber,data,null);
		}
		public Node(Integer[] key,int keyNumber,Object[] data,Node[] children){
			this.keyNumber = keyNumber;
			this.key = key;
			this.data = data;
			this.children = children;
		}
		
		public int getKeyNumber() {
			return keyNumber;
		}
		public void setKeyNumber(int keyNumber) {
			this.keyNumber = keyNumber;
		}
		public Integer[] getkey() {
			return key;
		}
		public void setkey(Integer[] key) {
			this.key = key;
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
			return "Node [keyNumber=" + keyNumber + ", key=" + Arrays.toString(key) + ", isLeft="+ (children==null?"true":"false") + "]";
		}
		
	}
	
	/**
	 * 获取根节点
	 * @return
	 * @author zhoujie
	 * @date 2018年1月23日 下午4:18:28
	 */
	public Node getRoot(){
		if(sentinel.children==null){
			return null;
		}
		return sentinel.children[0];
	}
	
	public boolean add(int ele){
		size++;
		return add(sentinel,0,ele);
	}
	public boolean add(Node parent,int index,int ele){
		boolean result = false;
		if(parent.children==null){
			parent.children = new Node[1];
			parent.children[0] = new Node(ele);
			result = true;
		}else if(parent.children[index].children==null){
			//判断为叶子节点，将元素添加到该节点
			Node node = parent.children[index];
			/** 添加元素 **/
			addkey(node,ele);
			/** 检查元素是否需要分裂 **/
			checkNode(parent,index);
			result = true;
		}else{
			//递归调用
			result = add(parent.children[index],getIndex(parent.children[index],ele),ele);
			//检查节点是否符合B树特征
			checkNode(parent,index);
		}
		return result;
	}
	
	/**
	 * 添加元素
	 * @param parent
	 * @param index
	 * @param ele
	 * @author zhoujie
	 * @date 2018年1月25日 上午11:57:25
	 */
	public void addkey(Node node,int ele){
		boolean notReplace = true; 
		for(int i=node.keyNumber-1;i>=0;i--){
			if(node.key[i]>ele){
				node.key[i+1] = node.key[i];
			}else{
				node.key[i+1] = ele;
				notReplace = false;
				break;
			}
		}
		if(notReplace){
			node.key[0] = ele;
		}
		node.keyNumber++;
	}
	
	/**
	 * 检查节点
	 * @param parent
	 * @param index
	 */
	public void checkNode(Node parent,int index){
		Node node = parent.children[index];
		if(node.keyNumber==m+1){//关键字 大于b树阶数减1，需要分裂
			if(node.equals(getRoot())){//当前操作元素是根节点时分裂
				//将根节点key元素从中间一分为三
				Integer[] tempkeys = node.key;
				int middle = tempkeys.length/2;
				Integer[] rootkey = new Integer[m];
				Integer[] leftkey = new Integer[m];
				Integer[] rightkey = new Integer[m];
				System.arraycopy(tempkeys, middle, rootkey, 0,1);
				System.arraycopy(tempkeys, 0, leftkey, 0, middle);
				System.arraycopy(tempkeys, middle+1, rightkey, 0, m-(middle+1));
				node.keyNumber=1;
				node.key = rootkey;
				if(node.children==null){
					//根节点孩子为空时，只需要将数据拆分为两部分，并赋给分裂后的左右节点
					node.children = new Node[m+1];
					Node leftNode = new Node(leftkey,middle);
					Node rightNode = new Node(rightkey,m-(middle+1));
					//调整父节点
					node.children[0] = leftNode;
					node.children[1] = rightNode;
				}else{
					//根节点孩子不为空时，将孩子数组拆分成两部分，并赋给分裂后的左右节点
					Node[] tempNodes = node.children;
					int middleNode = tempNodes.length%2==0?tempNodes.length/2:tempNodes.length/2+1;
					Node[] leftNodes = new Node[m+1];
					Node[] rightNodes = new Node[m+1];
					System.arraycopy(tempNodes, 0, leftNodes, 0, middleNode);
					System.arraycopy(tempNodes, middleNode, rightNodes, 0, m+1-middleNode);
					Node leftNode = new Node(leftkey,middle,leftNodes);
					Node rightNode = new Node(rightkey,m-(middle+1),rightNodes);
					//调整父节点
					Node[] pNode = new Node[m+1];
					node.children = pNode;
					node.children[0] = leftNode;
					node.children[1] = rightNode;
				}
			}else{//当不平衡节点不为根节点时
				//将不平衡节点key元素从中间一分为三
				Integer[] tempkeys = node.key;
				int middle = tempkeys.length/2;
				Integer[] leftkey = new Integer[m];
				Integer[] rightkey = new Integer[m];
				System.arraycopy(tempkeys, 0, leftkey, 0, middle);
				System.arraycopy(tempkeys, middle+1, rightkey, 0, m-(middle+1));
				addkey(parent,tempkeys[middle]);
				if(node.children==null){
					//不平衡节点孩子为空时，只需要将数据拆分为两部分，并赋给分裂后的左右节点
					Node leftNode = new Node(leftkey,middle);
					Node rightNode = new Node(rightkey,m-(middle+1));
					//调整父节点
					adjustParent(parent,index,leftNode,rightNode);
				}else{
					//不平衡节点孩子不为空时，将孩子数组拆分成两部分，并赋给分裂后的左右节点
					Node[] tempNodes = node.children;
					int middleNode = tempNodes.length%2==0?tempNodes.length/2:tempNodes.length/2+1;
					Node[] leftNodes = new Node[m+1];
					Node[] rightNodes = new Node[m+1];
					System.arraycopy(tempNodes, 0, leftNodes, 0, middleNode);
					System.arraycopy(tempNodes, middleNode, rightNodes, 0, m+1-middleNode);
					Node leftNode = new Node(leftkey,middle,leftNodes);
					Node rightNode = new Node(rightkey,m-(middle+1),rightNodes);
					//调整父节点
					adjustParent(parent,index,leftNode,rightNode);
				}
			}
		}
	}
	
	/**
	 * 调整父节点
	 * @param parent
	 * @param index
	 * @param leftNode
	 * @param rightNode
	 */
	public void adjustParent(Node parent,int index,Node leftNode,Node rightNode){
		Node tempN = parent.children[index+1];
		parent.children[index] = leftNode;
		parent.children[index+1] = rightNode;
		for(int i=index+1;i<parent.keyNumber;i++){
			Node tn = parent.children[i+1];
			parent.children[i+1]=tempN;
			tempN = tn;
		}
	}
	
	/**
	 * 二分法获取children索引
	 * @param node 当前节点
	 * @param start 开始索引
	 * @param end 结束索引
	 * @param ele 元素
	 * @return
	 */
	public int getIndex(Node node,int ele){
		if(node==null){
			Assert.notNull(node, "获取下级索引时，节点不能为空！");
		}
		int start = 0;
		int end = node.keyNumber-1;
		int index;
		while(true){
			index = (end+start)/2;
			if(node.key[index]>ele){
				if(end==start||start==index){
					return index;
				}else{
					end = index-1;
					continue;
				}
			}else{
				if(end==start){
					return index+1;
				}else{
					start = index+1;
					continue;
				}
				
			}
		}
	}
	
	public static void main(String[] args) {
		
	}
}
