package com.test.dataStructures.tree.BTree;

import java.util.Arrays;
import java.util.Random;

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
 * 
 * B树节点的状态有三种 full：代表节点有m棵子树   hunger：代表节点有[m/2]棵子树 unfull:代表节点有m-1到[m/2]+1棵子树
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
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Node [keyNumber=" + keyNumber + ", data="
					+ Arrays.toString(data) + "]";
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
	/**
	 * 添加元素，获取目标节点
	 * @param ele
	 * @return
	 * @author zhoujie
	 * @date 2018年1月26日 下午4:30:54
	 */
	public Node getAddNode(Integer ele){
		Node node = getRoot();
		if(node==null){
			return null;
		}
		while(true){
			if(node.children==null){
				return node;
			}else{
				node = node.children[getIndex(node,ele)];
			}
		}
	}
	/**
	 * 获取元素所在的节点
	 * @param ele
	 * @return
	 */
	public Node getNode(Integer ele){
		Node node = getRoot();
		while(true){
			if(node==null){
				return null;
			}
			for(int i=0;i<node.keyNumber;i++){
				if(node.data[i]==ele){
					return node;
				}else if(node.data[i]>ele){
					if(node.children==null){
						return null;
					}
					node = node.children[i];
					break;
				}
			}
			node = node.children[node.keyNumber];
		}
	}
//	/**
//	 * 二分法获取节点
//	 * @param ele
//	 * @return
//	 */
//	public Node getNode(Integer ele){
//		return getNode(getRoot(),0,getRoot().keyNumber,ele);
//	}
//	public Node getNode(Node node,int start,int end,Integer ele){
//		if(end==start){
//			if(node.data[start]==ele){
//				return node;
//			}else{
//				return null;
//			}
//		}
//		int index = (end-start)/2;
//		if(node.data[index]>ele){
//			return getNode(node,index+1,end,ele);
//		}else if(node.data[index]<ele){
//			return getNode(node,start,index-1,ele);
//		}else{
//			return node;
//		}
//	}
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
	public void print(Node node){
		if(node!=null){
			System.out.print("tree:");
			printNode(node);
		}else{
			System.out.println(" tree:null");
		}
		System.out.println(" size:"+size);
	}
	public void printNode(Node node){
		for(int i=0;i<node.keyNumber+1;i++){
			if(node.children!=null){
				printNode(node.children[i]);
				if(i!=node.keyNumber){
					System.out.print(node.data[i]+",");
				}
			}else{
				if(i!=node.keyNumber){
					System.out.print(node.data[i]+",");
				}
			}
		}
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
		boolean result = false;
		if(parent.children==null){
			parent.children = new Node[m+1];
			parent.children[0] = new Node(ele);
			result = true;
		}else if(parent.children[index].children==null){
			Node node = parent.children[index];
			/** 添加元素 **/
			addData(node,ele);
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
	public void addData(Node node,int ele){
		boolean flag = true;
		int temp;
		for(int i=0;i<node.keyNumber;i++){
			if(flag){
				if(node.data[i]>ele){
					temp = node.data[i];
					node.data[i] = ele;
					ele = temp;
					flag = false;
				}
			}else{
				temp = node.data[i];
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
				addData(parent,tempDatas[middle]);
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
		Node retNode = delete(sentinel,0,ele);
		if(getRoot()!=null&&getRoot().keyNumber==0){
			if(getRoot().children==null){
				sentinel.children = null;
			}else{
				sentinel.children[0]=getRoot().children[0];
			}
		}
		if(retNode!=null){
			size--;
		}
		return retNode;
	}
	public Node delete(Node parent,int index,int ele){
		Node ret = null;
		if(parent.children==null){
			return null;
		}
		Node node = parent.children[index];
		if(node==null){
			return null;
		}
		boolean isCurrent=false;
		int tempIndex=0;
		for(int i=0;i<node.keyNumber;i++){
			if(node.data[i]==ele){
				isCurrent = true;
				tempIndex = i;
			}
		}
		if(isCurrent){
			if(node.children==null){
				//被删除节点为叶子结点时
				delData(node, tempIndex);
				//检查删除后节点状态
				if(!getRoot().equals(node)){
					checkDelNode(parent,index);
				}
			}else{
				//被删除节点不为叶子结点时
				//找到该节点右子树最小值，删除该值并返回
				delRightChildMin(node,tempIndex+1);
				//检查删除后节点状态
				if(!getRoot().equals(node)){
					checkDelNode(parent,index);
				}
			}
			ret = node;
		}else{
			ret = delete(node,getIndex(node,ele),ele);
			//检查删除后节点状态
			if(!getRoot().equals(node)){
				checkDelNode(parent,index);
			}
		}
		return ret;
	}
	
	public int delRightChildMin(Node parent,int index){
		Node node = parent.children[index];
		if(node.children==null){
			int ele = delData(node,0);
			parent.data[index-1] = ele;
			//检查删除后节点状态
			if(!getRoot().equals(node)){
				checkDelNode(parent,index);
			}
			return ele;
		}
		int ele = delRightChildMin_(node);
		parent.data[index-1] = ele;
		//检查节点状态
		if(!getRoot().equals(node)){
			checkDelNode(parent,index);
		}
		return ele;
	}
	public int delRightChildMin_(Node parent){
		Node node = parent.children[0];
		if(node.children==null){
			int ele = delData(node,0);
			//检查删除后节点状态
			if(!getRoot().equals(node)){
				checkDelNode(parent,0);
			}
			return ele;
		}
		int ele = delRightChildMin_(node);
		//检查节点状态
		if(!getRoot().equals(node)){
			checkDelNode(parent,0);
		}
		return ele;
	}
	public int minKeyNumber(){
		return  m%2==0?m/2-1:m/2;
	}
	
	public void addChildNode(Node node,int index,Node child){
		Node temp;
		for(int i=index;i<node.keyNumber+1;i++){
			temp = node.children[i];
			node.children[i] = child;
			child = temp;
		}
	}
	
	public int delData(Node node,int index){
		int ret = node.data[index];
		for(int i=index;i<node.keyNumber;i++){
			node.data[i] = node.data[i+1];
		}
		node.keyNumber--;
		return ret;
	}
	/**
	 * 删除index对应孩子节点并返回
	 * @param node 当前节点
	 * @param index children索引
	 * @return
	 */
	public Node delChildNode(Node node,int index){
		Node ret = node.children[index];
		for(int i=index;i<node.keyNumber+1;i++){
			node.children[i] = node.children[i+1];
		}
		return ret;
	}
	/**
	 * 删除index对应孩子节点并返回（当节点关键字不能准确反映children长度时使用该方法）
	 * @param node 当前节点
	 * @param index children索引
	 * @param length children长度 
	 * @return
	 */
	public Node delChildNode(Node node,int index,int length){
		Node ret = node.children[index];
		for(int i=index;i<length;i++){
			node.children[i] = node.children[i+1];
		}
		return ret;
	}
	public int repData(Node node,int index,int ele){
		int temp = node.data[index];
		node.data[index]=ele;
		return temp;
	}
	
	/**
	 * 检查删除后节点状态
	 * @param parent
	 * @param index
	 * @author zhoujie
	 * @date 2018年1月31日 下午6:08:44
	 */
	public void checkDelNode(Node parent,int index){
		Node node = parent.children[index];
		//检查删除后节点是否符合B树要求
		if(node.keyNumber<minKeyNumber()){
			//不符合
			//校验父节点左右子树是否有非hunger型节点，若有则从父节点借元素，父节点再从非hunger型节点借元素
			boolean left = false;
			boolean right = false;
			int leftKey = 0;
			int rightKey = 0;
			//检查左边是否有非hunger型节点
			Node lnode = null;
			if(index-1>=0){
				lnode = parent.children[index-1];
				if(lnode.keyNumber>minKeyNumber()){
					left = true;
				}
				leftKey = lnode.keyNumber;
			}
			//检查右边是否有非hunger型节点
			Node rnode = null;
			if(index+1<parent.keyNumber+1){
				rnode = parent.children[index+1];
				if(rnode.keyNumber>minKeyNumber()){
					right = true;
				}
				rightKey = rnode.keyNumber;
			}
			if(left||right){
				//有非hunger型节点,操作拥有关键字最多的节点（尽量保持元素均衡分布）
				if(leftKey>=rightKey){
					//左边节点参与交换
					//删除左边节点最大值并返回被删除元素
					int temData = delData(lnode,lnode.keyNumber-1);
					//替换父节点n-1位置的值，并返回被替换元素
					temData = repData(parent,index-1,temData);
					//将父节点被动、替换元素插入不平衡节点
					addData(node,temData);
					if(node.children!=null){
						//如果子节点不为空，则需要把删除对应的子节点转移到新增数据子节点前面
						Node temNode = delChildNode(lnode,lnode.keyNumber+1,lnode.keyNumber+2);
						addChildNode(node,0,temNode);
					}
				}else{
					//右边节点参与交换
					//删除左边节点最大值并返回被删除元素
					int temData = delData(rnode,0);
					//替换父节点n位置的值，并返回被替换元素
					temData = repData(parent,index,temData);
					//将父节点被动、替换元素插入不平衡节点
					addData(node,temData);
					if(node.children!=null){
						//如果子节点不为空，则需要把删除对应的子节点转移到新增数据子节点后面
						Node temNode = delChildNode(rnode,0);
						addChildNode(node,node.keyNumber,temNode);
					}
				}
			}else{
				//没有非hunger型节点，则合并节点
				if(leftKey>=rightKey){
					//左节点参与合并
					//数据合并
					System.arraycopy(parent.data, index-1, lnode.data, lnode.keyNumber, 1);
					System.arraycopy(node.data, 0, lnode.data, lnode.keyNumber+1, node.keyNumber);
					if(node.children!=null){
						//如果子节点不为空，参与合并的两个节点的孩子也要合并
						System.arraycopy(node.children, 0, lnode.children, lnode.keyNumber+1, node.keyNumber+1);
					}
					lnode.keyNumber = node.keyNumber+lnode.keyNumber+1;
					delChildNode(parent,index);
					delData(parent,index-1);
				}else{
					//右节点参与合并
					//数据合并
					System.arraycopy(parent.data, index, node.data, node.keyNumber, 1);
					System.arraycopy(rnode.data, 0, node.data, node.keyNumber+1, rnode.keyNumber);
					if(node.children!=null){
						//如果子节点不为空，参与合并的两个节点的孩子也要合并
						System.arraycopy(rnode.children, 0, node.children, node.keyNumber+1, rnode.keyNumber+1);
					}
					node.keyNumber = node.keyNumber+rnode.keyNumber+1;
					//删除父节点data，和child
					delChildNode(parent,index+1);
					delData(parent,index);
				}
			}
			
		}
	}
	public static void main(String[] args) {
		BUnderscodeTree2 but = new BUnderscodeTree2(9);
		Integer[] arr = new Integer[]{12,23,15,13,22,14,25,17,19,6,8,2,5,18,34,56,21,9,16,44,41,38,1,77,52,29,37,47,66,48};
//		Integer[] arr = new Integer[]{12,23,15,13,22,14,25,17,19};
//		Integer[] arr = new Integer[]{12,23,15,13,22,14,25,17,19,21,20};
//		Integer[] arr = new Integer[]{12,23,15};
		
//		int length = 1000000;
//		int[] data = new int[length];
//		Random r = new Random();
//		for(int i=0;i<length;i++){
//			data[i]=r.nextInt(length*10);
//		}
		Long startTime = System.currentTimeMillis();
		for(int i=0;i<arr.length;i++){
			but.add(arr[i]);
		}
		Long endTime = System.currentTimeMillis();
		System.out.println("插入"+arr.length+"条数据耗时"+(endTime-startTime)+"毫秒");
//		for(int i=0;i<data.length;i++){
//			but.delete(data[i]);
//			System.out.println(JSON.toJSONString(but.getRoot(),true));
//			but.print(but.getRoot());
//		}
		System.out.println(JSON.toJSONString(but.getRoot(),true));
		Node node = but.getNode(53);
		System.out.println(node);
//		System.out.println(but.getRoot());
//		System.out.println(JSON.toJSONString(but.getRoot(),true));
//		but.print(but.getRoot());
//		but.delete(37);
//		System.out.println(JSON.toJSONString(but.getRoot(),true));
//		but.print(but.getRoot());
//		but.delete(34);
//		System.out.println(JSON.toJSONString(but.getRoot(),true));
//		but.print(but.getRoot());
//		but.delete(15);
//		System.out.println(JSON.toJSONString(but.getRoot(),true));
//		but.print(but.getRoot());
	}
	
	public static void printArray(String pre,Integer[] a){
		System.out.print(pre+":");
		for(Integer i:a){
			System.out.print(i+",");
		}
		System.out.println();
	}
}