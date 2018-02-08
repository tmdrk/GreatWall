package com.test.dataStructures.tree.BTree;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Random;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.test.common.util.Assert;
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
@SuppressWarnings({"rawtypes","unchecked"})
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
		int dataNumber;
		/** 数据数组 **/
		Entry<Integer,Object>[] data;
		/** 子节点 **/
		Node[] children;
		/** 下一兄弟节点 **/
		Node nextNode;
		
		public Node(){
			
		}
		public Node(int key){
			Entry<Integer,Object>[] d = new SimpleEntry[m+1];
			d[0] = new SimpleEntry<Integer,Object>(key, null);
			this.dataNumber = 1;
			this.data = d;
		}
		public Node(int key,Object val){
			Entry<Integer,Object>[] d = new SimpleEntry[m+1];
			d[0] = new SimpleEntry<Integer,Object>(key, val);
			this.dataNumber = 1;
			this.data = d;
		}
		public Node(Entry entry){
			Entry<Integer,Object>[] d = new SimpleEntry[m+1];
			d[0] = entry;
			this.dataNumber = 1;
			this.data = d;
		}
		public Node(Entry<Integer,Object>[] data,int dataNumber){
			this(data,dataNumber,null);
		}
		public Node(Entry<Integer,Object>[] data,int dataNumber,Node[] children){
			this(data,dataNumber,children,null);
		}
		public Node(Entry<Integer,Object>[] data,int dataNumber,Node[] children,Node nextNode){
			this.dataNumber = dataNumber;
			this.data = data;
			this.children = children;
			this.nextNode = nextNode;
		}
		
		public int getdataNumber() {
			return dataNumber;
		}
		public void setdataNumber(int dataNumber) {
			this.dataNumber = dataNumber;
		}
		@SuppressWarnings("rawtypes")
		public Entry[] getdata() {
			return data;
		}
		public void setdata(Entry[] data) {
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
			return "Node [dataNumber=" + dataNumber + ", data=" + Arrays.toString(data) + ", isLeft="+ (children==null?"true":"false") + "]";
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
	
	public Object add(int ele,Object obj){
		if(ele==8){
			System.out.println();
		}
		Entry<Integer,Object> entry = new SimpleEntry<Integer,Object>(ele, obj);
		Object result = add(sentinel,0,entry);
		if(result==null){
			size++;
		}
		return result;
	}
	public Object add(Node parent,int index,Entry<Integer,Object> entry){
		Object result = null;
		if(parent.children==null){
			parent.children = new Node[1];
			parent.children[0] = new Node(entry);
			head.nextNode = parent.children[0];
		}else if(parent.children[index].children==null){
			//判断为叶子节点，将元素添加到该节点
			Node node = parent.children[index];
			/** 添加元素 **/
			result = addData(node,entry);
			/** 检查元素是否需要分裂 **/
			checkNode(parent,index);
		}else{
			Node node = parent.children[index];
			//
			if(node.data[0].getKey()>entry.getKey()){
				System.out.println("测试小于情况");
				node.data[0] = entry;
			}
			//递归调用
			int inde = getIndex(parent.children[index],entry);
			result = add(node,inde,entry);
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
	public Object addData(Node node,Entry<Integer,Object> entry){
//		boolean notReplace = true; 
//		for(int i=node.dataNumber-1;i>=0;i--){
//			if(node.data[i].getKey()>entry.getKey()){
//				node.data[i+1] = node.data[i];
//			}else{
//				node.data[i+1] = entry;
//				notReplace = false;
//				break;
//			}
//		}
//		if(notReplace){
//			node.data[0] = entry;
//		}
		int start = 0;
		int end = node.dataNumber-1;
		int mid;
		while(true){
			mid = (start+end)/2;
			if(node.data[mid].getKey()-entry.getKey()==0){
				Object obj = node.data[mid].getValue();
				node.data[mid] = entry;
				return obj;
			}else if(node.data[mid].getKey()>entry.getKey()){
				if(start==end||start==mid){
					System.arraycopy(node.data, mid, node.data, mid+1, node.dataNumber-mid);
					node.data[mid] = entry;
					break;
				}
				end = mid-1;
			}else{
				if(start==end){
					System.arraycopy(node.data, mid+1, node.data, mid+2, node.dataNumber-mid-1);
					node.data[mid+1] = entry;
					break;
				}
				start = mid+1;
			}
		}
		node.dataNumber++;
		return null;
	}
	
	/**
	 * 新建或更新节点
	 * @param node
	 * @param data
	 * @param dataNumber
	 * @param children
	 * @param nextNode
	 * @return
	 */
	public Node newOrUpdate(Node node,Entry<Integer,Object>[] data,int dataNumber,Node[] children,Node nextNode){
		if(node==null){
			return new Node(data,dataNumber,children,nextNode);
		}
		node.children = children;
		node.dataNumber = dataNumber;
		node.data = data;
		node.nextNode = nextNode;
		return node;
	}
	
	/**
	 * 检查节点
	 * @param parent
	 * @param index
	 */
	public void checkNode(Node parent,int index){
		Node node = parent.children[index];
		if(node.dataNumber==m+1){//关键字 大于b树阶数减1，需要分裂
			if(isRoot(node)){//当前操作元素是根节点时分裂
				//将根节点data元素从中间一分为三
				Entry<Integer,Object>[] tempdatas = node.data;
				int middle = tempdatas.length/2;
				Entry<Integer,Object>[] rootdata = new SimpleEntry[m+1];
				Entry<Integer,Object>[] leftdata = new SimpleEntry[m+1];
				Entry<Integer,Object>[] rightdata = new SimpleEntry[m+1];
				//将根数据拆分为两份
				System.arraycopy(tempdatas, 0, leftdata, 0, middle);
				System.arraycopy(tempdatas, middle, rightdata, 0, m+1-middle);
				//设置新的根数据
				rootdata[0] = new SimpleEntry(tempdatas[0].getKey(),null);
				rootdata[1] = new SimpleEntry(tempdatas[middle].getKey(),null);
				
				Node[] leftNodes = null;
				Node[] rightNodes = null;
				//根节点孩子不为空时，将孩子数组拆分成两部分，并赋给分裂后的左右节点
				if(node.children!=null){
					Node[] tempNodes = node.children;
					int middleNode = middle;
					leftNodes = new Node[m+1];
					rightNodes = new Node[m+1];
					System.arraycopy(tempNodes, 0, leftNodes, 0, middleNode);
					System.arraycopy(tempNodes, middleNode, rightNodes, 0, m+1-middleNode);
				}
				Node rightNode = newOrUpdate(null,rightdata,m+1-middle,rightNodes,node.nextNode);
				node = newOrUpdate(node,leftdata,middle,leftNodes,rightNode);
				//调整父节点
				Node[] rootChildren = new Node[m+1];
				rootChildren[0] = node;
				rootChildren[1] = rightNode;
				Node rootNode = newOrUpdate(null,rootdata,2,rootChildren,null);
				parent.children[index] = rootNode;
			}else{//当不平衡节点不为根节点时
				//将不平衡节点data元素从中间一分为三
				Entry<Integer,Object>[] tempdatas = node.data;
				int middle = tempdatas.length/2;
				Entry<Integer,Object>[] leftdata = new SimpleEntry[m+1];
				Entry<Integer,Object>[] rightdata = new SimpleEntry[m+1];
				//将节点数据拆分为两份
				System.arraycopy(tempdatas, 0, leftdata, 0, middle);
				System.arraycopy(tempdatas, middle, rightdata, 0, m+1-middle);
				addData(parent,tempdatas[middle]);
				
				Node[] leftNodes = null;
				Node[] rightNodes = null;
				//不平衡节点孩子不为空时，将孩子数组拆分成两部分，并赋给分裂后的左右节点
				if(node.children!=null){
					Node[] tempNodes = node.children;
					int middleNode = middle;
					leftNodes = new Node[m+1];
					rightNodes = new Node[m+1];
					System.arraycopy(tempNodes, 0, leftNodes, 0, middleNode);
					System.arraycopy(tempNodes, middleNode, rightNodes, 0, m+1-middleNode);
				}
				Node rightNode = newOrUpdate(null,rightdata,m+1-middle,rightNodes,node.nextNode);
				node = newOrUpdate(node,leftdata,middle,leftNodes,rightNode);
				//调整父节点
				adjustParent(parent,index,node,rightNode);
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
		for(int i=index+1;i<parent.dataNumber-1;i++){
			Node tn = parent.children[i+1];
			parent.children[i+1]=tempN;
			tempN = tn;
		}
	}
	
//	public Node delete(int ele){
//		Node retNode = delete(sentinel,0,ele);
//		if(getRoot()!=null&&getRoot().dataNumber==0){
//			if(getRoot().children==null){
//				sentinel.children = null;
//			}else{
//				sentinel.children[0]=getRoot().children[0];
//			}
//		}
//		if(retNode!=null){
//			size--;
//		}
//		return retNode;
//	}
//	public Node delete(Node parent,int index,int ele){
//		Node ret = null;
//		if(parent.children==null){
//			return null;
//		}
//		Node node = parent.children[index];
//		if(node==null){
//			return null;
//		}
//		boolean isCurrent=false;
//		int tempIndex=0;
//		for(int i=0;i<node.dataNumber;i++){
//			if(node.data[i].equals(ele)){
//				isCurrent = true;
//				tempIndex = i;
//			}
//		}
//		if(isCurrent){
//			if(node.children==null){
//				//被删除节点为叶子结点时
//				delData(node, tempIndex);
//				//检查删除后节点状态
//				if(!getRoot().equals(node)){
//					checkDelNode(parent,index);
//				}
//			}else{
//				//被删除节点不为叶子结点时
//				//找到该节点右子树最小值，删除该值并返回
//				delRightChildMin(node,tempIndex+1);
//				//检查删除后节点状态
//				if(!getRoot().equals(node)){
//					checkDelNode(parent,index);
//				}
//			}
//			ret = node;
//		}else{
//			ret = delete(node,getIndex(node,ele),ele);
//			//检查删除后节点状态
//			if(!getRoot().equals(node)){
//				checkDelNode(parent,index);
//			}
//		}
//		return ret;
//	}
	
	/**
	 * 二分法获取children索引
	 * @param node 当前节点
	 * @param start 开始索引
	 * @param end 结束索引
	 * @param ele 元素
	 * @return
	 */
	public int getIndex(Node node,Entry<Integer,Object> entry){
		if(node==null){
			Assert.notNull(node, "获取下级索引时，节点不能为空！");
		}
		int start = 0;
		int end = node.dataNumber-1;
		int index;
		while(true){
			index = (end+start)/2;
			if(node.data[index].getKey()-entry.getKey()==0){
				return index;
			}else if(node.data[index].getKey()<entry.getKey()){
				if(end==start){
					return index;
				}else{
					start = index+1;
					continue;
				}
			}else{
				if(end==start||start==index){
					return index-1;
				}else{
					end = index-1;
					continue;
				}
			}
		}
	}
	/**
	 * 判断当前节点是否是根节点
	 * @param node
	 * @return
	 */
	public boolean isRoot(Node node){
		return node.equals(getRoot());
	}
	/**
	 * 遍历叶子节点
	 * @param node
	 */
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
		while(node!=null){
			for(int i=0;i<node.dataNumber;i++){
				System.out.print(node.data[i]+",");
			}
			node = node.nextNode;
		}
	}
	
	public static void main(String[] args) {
		BAdditionTree bat = new BAdditionTree(5);
//		Integer[] arr = new Integer[]{5,18,26,30,8,11,3,1};
//		String[] str = new String[]{"a","b","c","d","e","f","g","h"};
		
		Integer[] arr = new Integer[]{5,18,26,30,8,11,3,1,6,7,9,10,2,4,0,12,13,14};
		String[] str = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r"};

		Long startTime = System.currentTimeMillis();
		for(int i=0;i<arr.length;i++){
			bat.add(arr[i],str[i]);
		}
		Long endTime = System.currentTimeMillis();
		System.out.println("插入"+arr.length+"条数据耗时"+(endTime-startTime)+"毫秒");
		bat.print(bat.head.nextNode);
	}
}
