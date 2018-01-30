package com.test.dataStructures.tree.BTree;

import com.alibaba.fastjson.JSON;

/**
 * BUnderscodeTree树
 * m 阶B_树满足或空，或为满足下列性质的m叉树：
 * (1) 树中每个结点最多有m 棵子树
 * (2) 根结点在不是叶子时，至少有两棵子树
 * (3) 除根外，所有非终端结点至少有 ?m/2?棵子树 
 * (4) 有 s 个子树的非叶结点具有 n=s -1个关键字，结点的信息组织为:(n,A0,K1,A1,K2,A2 … Kn，An）
 *  这里：n:关键字的个数，ki（i=1,2,…,n)为关键字，且满足Ki<Ki+1,，Ai(i=0,1,..n)为指向子树的指针。
 * (5)所有的叶子结点都出现在同一层上，不带信息（可认为外部结点或失败结点）。
 * @ClassName: BUnderscodeTree 
 * @author zhoujie
 * @date 2018年1月23日 上午11:42:39
 */
public class BUnderscodeTree2 {
	public BUnderscodeTree2(){
		this(3);
	}
	public BUnderscodeTree2(int m){
		this.m=m;
	}
	/** 哨兵 **/
	Node sentinel = new Node();
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
		
		public Node(){
		}
		public Node(int v){
			Integer[] d = new Integer[m];
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
	}
	/**
	 * 获取根节点
	 * @return
	 * @author zhoujie
	 * @date 2018年1月23日 下午4:18:28
	 */
	public Node getRoot(){
		return sentinel.children[0];
	}
	/**
	 * 获取元素所在节点
	 * @param ele
	 * @return
	 * @author zhoujie
	 * @date 2018年1月26日 下午4:30:54
	 */
	public Node getNode(Integer ele){
		Node node = getRoot();
		if(node==null){
			return null;
		}
		while(true){
			if(node.children==null){
				return node;
			}else{
				int nodeIndex = getIndex(node,ele);
				node = node.children[nodeIndex];
			}
		}
	}
	/**
	 * 获取下级节点索引
	 * @param node
	 * @param ele
	 * @return
	 * @author zhoujie
	 * @date 2018年1月23日 下午6:04:18
	 */
	public int getIndex(Node node,int ele){
		for(int i=0;i<node.keyNumber;i++){
			if(node.data[i]>ele){
				return i;
			}
		}
		return node.keyNumber;
	}
	/**
	 * 新增
	 * @param ele 新增的元素
	 * @author zhoujie
	 * @date 2018年1月23日 下午4:13:15
	 */
	public boolean add(int ele){
		size++;
		return add(sentinel,0,ele);
	}
	/**
	 * 新增节点方法
	 * @param parent 父亲节点
	 * @param index	孩子节点的索引
	 * @author zhoujie
	 * @date 2018年1月23日 下午4:11:24
	 */
	public boolean add(Node parent,int index,int ele){
		if(ele==20){
			System.out.println(1);
		}
		boolean result = false;
		if(parent.children==null){
			parent.children = new Node[m+1];
			parent.children[0] = new Node(ele);
			result = true;
		}else if(parent.children[index].children==null){
			Node node = parent.children[index];
			/** 添加元素 **/
			addTree(node,ele);
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
	public void addTree(Node node,int ele){
		boolean flag = true;
		for(int i=0;i<node.keyNumber;i++){
			if(flag){
				if(node.data[i]>ele){
					int temp = node.data[i];
					node.data[i] = ele;
					ele = temp;
					flag = false;
				}
			}else{
				int temp = node.data[i];
				node.data[i] = ele;
				ele = temp;
			}
		}
		node.data[node.keyNumber] = ele;
		node.keyNumber++;
	}
	
	public void checkNode(Node parent,int index){
		Node node = parent.children[index];
		if(node.keyNumber==m){//关键字 大于b树阶数减1，需要分裂
			if(node.equals(getRoot())){//当前操作元素是根节点时分裂
				//将根节点data元素从中间一分为三
				Integer[] tempDatas = node.data;
				int middle = tempDatas.length/2;
				Integer[] rootData = new Integer[m];
				Integer[] leftData = new Integer[m];
				Integer[] rightData = new Integer[m];
				System.arraycopy(tempDatas, middle, rootData, 0,1);
				System.arraycopy(tempDatas, 0, leftData, 0, middle);
				System.arraycopy(tempDatas, middle+1, rightData, 0, m-(middle+1));
				node.keyNumber=1;
				node.data = rootData;
				if(node.children==null){
					//根节点孩子为空时，只需要将数据拆分为两部分，并赋给分裂后的左右节点
					node.children = new Node[m+1];
					Node leftNode = new Node(leftData,middle);
					Node rightNode = new Node(rightData,m-(middle+1));
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
					Node leftNode = new Node(leftData,middle,leftNodes);
					Node rightNode = new Node(rightData,m-(middle+1),rightNodes);
					//调整父节点
					Node[] pNode = new Node[m+1];
					node.children = pNode;
					node.children[0] = leftNode;
					node.children[1] = rightNode;
				}
			}else{//当不平衡节点不为根节点时
				//将不平衡节点data元素从中间一分为三
				Integer[] tempDatas = node.data;
				int middle = tempDatas.length/2;
				Integer[] leftData = new Integer[m];
				Integer[] rightData = new Integer[m];
				System.arraycopy(tempDatas, 0, leftData, 0, middle);
				System.arraycopy(tempDatas, middle+1, rightData, 0, m-(middle+1));
				addTree(parent,tempDatas[middle]);
				if(node.children==null){
					//不平衡节点孩子为空时，只需要将数据拆分为两部分，并赋给分裂后的左右节点
					Node leftNode = new Node(leftData,middle);
					Node rightNode = new Node(rightData,m-(middle+1));
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
					Node leftNode = new Node(leftData,middle,leftNodes);
					Node rightNode = new Node(rightData,m-(middle+1),rightNodes);
					//调整父节点
					adjustParent(parent,index,leftNode,rightNode);
				}
			}
		}
	}
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
	
	public Node delete(int ele){
		size--;
		Node retNode = delete(sentinel,0,ele);
		return retNode;
	}
	public Node delete(Node parent,int index,int ele){
		Node node = parent.children[index];
		if(node==null){
			return null;
		}
		boolean isNow=false;
		int tempIndex=0;
		for(int i=0;i<node.keyNumber;i++){
			if(node.data[i]==ele){
				isNow = true;
				tempIndex = i;
			}
		}
		if(isNow){
			if(node.children==null){
				//被删除节点为根结点时
				for(int i=tempIndex;i<node.keyNumber;i++){
					node.data[i] = node.data[i+1];
				}
				node.keyNumber--;
				//检查删除后节点是否符合B树要求
				if(node.keyNumber>=getMinKey()){
					//符合，无需操作
					
				}else{
					//不符合
					//校验父节点左右子树是否有非饥饿型节点，若有则从父节点借元素，父节点再从非饥饿型节点借元素
					boolean unhunger = false;
					boolean left = true;
					if(index-1>=0){
						Node lnode = parent.children[index-1];
						if(lnode.keyNumber>getMinKey()){
							unhunger = true;
						}else{
							if(index+1<parent.keyNumber){
								Node rnode = parent.children[index+1];
								if(rnode.keyNumber>getMinKey()){
									unhunger = true;
									left = false;
								}
							}
						}
					}
					if(unhunger){
						if(left){
							
						}else{
							
						}
					}
					//没有，则合并节点
					
				}
			}else{
				//被删除节点不为根结点时
				
			}
		}else{
			Node ret = delete(node,getIndex(node,ele),ele);
			
			return ret;
		}
		return null;
	}
	public int getMinKey(){
		return  m%2==0?m/2-1:m/2;
	}
	public static void main(String[] args) {
		BUnderscodeTree2 but = new BUnderscodeTree2(3);
		Node n = but.new Node(3);
		Integer[] arr = new Integer[]{12,23,15,13,22,14,25,17,19,6,8,2,5,18};
		for(int i=0;i<arr.length;i++){
			but.add(arr[i]);
		}
//		Node node = but.getNode(22);
		System.out.println(but.getRoot());
		System.out.println(JSON.toJSONString(but.getRoot(),true));
	}
	
	public static void printArray(String pre,Integer[] a){
		System.out.print(pre+":");
		for(Integer i:a){
			System.out.print(i+",");
		}
		System.out.println();
	}
}