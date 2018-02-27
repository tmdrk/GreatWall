package com.test.dataStructures.tree.BTree;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Random;
import java.util.Map.Entry;
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
	
	/**
	 * 二分法获取元素所在节点
	 * @param ele
	 * @return
	 */
	public Node getNode(Integer ele){
		Node node = getRoot();
		if(node==null){
			return null;
		}
		while(true) {
			if(node.children==null) {
				int temp = getDataIndex(node,ele);
				if(temp<0) {
					return null;
				}
				return node;
			}else{
				int temp = getIndex(node,ele);
				if(temp<0) {
					return null;
				}
				node = node.children[temp];
			}
		}
	}
	
	public Object add(int ele,Object obj){
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
//				System.out.println("测试小于情况");
				node.data[0] = new SimpleEntry(entry.getKey(), null);
			}
			//递归调用
			int inde = getIndex(parent.children[index],entry.getKey());
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
	
	public void addDesData(Node parent,int index,Entry<Integer,Object> entry,int des){
		
		if(des==0){
			parent.data[index] = entry;
			parent.data[index].setValue(null);
		}
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
				addData(parent,new SimpleEntry(tempdatas[middle].getKey(),null));
				
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
	
	public Object delete(int ele){
		Object obj = delete(sentinel,0,ele);
		if(getRoot()!=null&&getRoot().dataNumber==1){
			if(getRoot().children!=null){
				sentinel.children[0]=getRoot().children[0];
			}
		}
		if(obj!=null){
			size--;
		}
		return obj;
	}

	public Object delete(Node parent,int index,int ele){
		Object ret = null;
		if(parent.children==null){
			return null;
		}
		Node node = parent.children[index];
		if(node==null){
			return null;
		}
		if(node.children==null){
			//节点为叶子结点时删除元素
			int desIndex = getDataIndex(node,ele);
			if(desIndex==-1){
				return null;
			}
			ret = delData(parent,index,desIndex).getValue();
			//检查删除后节点状态
			if(!getRoot().equals(node)){
				checkDelNode(parent,index);
			}
		}else{
			int childIndex = getIndex(node,ele);
			if(childIndex==-1){
				return null;
			}
			ret = delete(node,childIndex,ele);
			//检查删除后节点状态
			if(!getRoot().equals(node)){
				checkDelNode(parent,index);
				//检查是否需要调整上级节点关键字
				Node tnode = parent.children[index];
				if(parent.data[index]!=null&&!parent.data[index].getKey().equals(tnode.data[0].getKey())) {
					parent.data[index]=new SimpleEntry(tnode.data[0].getKey(), null);
				}
				
			}
		}
		return ret;
	}
	
	/**
	 * 二分法获取children索引
	 * @param node 当前节点
	 * @param start 开始索引
	 * @param end 结束索引
	 * @param ele 元素
	 * @return
	 */
	public int getIndex(Node node,Integer key){
		if(node==null){
			Assert.notNull(node, "获取下级索引时，节点不能为空！");
		}
		int start = 0;
		int end = node.dataNumber-1;
		int index;
		while(true){
			index = (end+start)/2;
			if(node.data[index].getKey()-key==0){
				return index;
			}else if(node.data[index].getKey()<key){
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
	 * 二分法获取data索引，key不存在返回 -1
	 * @param node 当前节点
	 * @param start 开始索引
	 * @param end 结束索引
	 * @param ele 元素
	 * @return
	 */
	public int getDataIndex(Node node,Integer key){
		if(node==null){
			Assert.notNull(node, "获取数据索引时，节点不能为空！");
		}
		int start = 0;
		int end = node.dataNumber-1;
		int index;
		while(true){
			index = (end+start)/2;
			if(node.data[index].getKey()-key==0){
				return index;
			}else if(node.data[index].getKey()<key){
				if(end==start){
					return -1;
				}else{
					start = index+1;
					continue;
				}
			}else{
				if(end==start||start==index){
					return -1;
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
	
	public Entry delData(Node parent,int index,int desIndex){
		Node node = parent.children[index];
		Entry ret = node.data[desIndex];
		System.arraycopy(node.data, desIndex+1, node.data,desIndex,node.dataNumber-(desIndex+1));
		node.data[node.dataNumber-1] = null;
		node.dataNumber--;
		if(desIndex==0&&!getRoot().equals(node)){
			copyKeyToParent(parent,node,index);
		}
		return ret;
	}
	
	public Entry delData(Node node,int desIndex){
		Entry ret = node.data[desIndex];
		System.arraycopy(node.data, desIndex+1, node.data,desIndex,node.dataNumber-(desIndex+1));
		node.data[node.dataNumber-1] = null;
		node.dataNumber--;
		return ret;
	}
	
	/**
	 * 将子节点第一个元素的值赋给父节点
	 * @param parent
	 * @param node
	 * @param desIndex
	 */
	private void copyKeyToParent(Node parent, Node node,int desIndex) {
		parent.data[desIndex]=new SimpleEntry<>(node.data[0].getKey(), null);
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
		if(node.dataNumber<minKeyNumber()){
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
				if(lnode.dataNumber>minKeyNumber()){
					left = true;
				}
				leftKey = lnode.dataNumber;
			}
			//检查右边是否有非hunger型节点
			Node rnode = null;
			if(index+1<parent.dataNumber){
				rnode = parent.children[index+1];
				if(rnode.dataNumber>minKeyNumber()){
					right = true;
				}
				rightKey = rnode.dataNumber;
			}
			if(left||right){
				//有非hunger型节点,操作拥有关键字最多的节点（尽量保持元素均衡分布）
				if(leftKey>=rightKey){
					//左边节点参与交换
					//删除左边节点最大值并返回被删除元素
					Entry temData = delData(parent,index-1,lnode.dataNumber-1);
					//替换父节点n-1位置的值，并返回被替换元素
					repData(parent,index,temData);
					//将父节点被动、替换元素插入不平衡节点
					addData(node,temData);
					if(node.children!=null){
						//如果子节点不为空，则需要把删除对应的子节点转移到新增数据子节点前面
						Node temNode = delChildNode(lnode,lnode.dataNumber,lnode.dataNumber+1);
						addChildNode(node,0,temNode);
					}
				}else{
					//右边节点参与交换
					//删除右边节点最小值并返回被删除元素
					Entry temData = delData(parent,index+1,0);
					//替换父节点n位置的值，并返回被替换元素
//					repData(parent,index,temData);
					//将父节点被动、替换元素插入不平衡节点
					addData(node,temData);
					if(node.children!=null){
						//如果子节点不为空，则需要把删除对应的子节点转移到新增数据子节点后面
						Node temNode = delChildNode(rnode,0,false);
						addChildNode(node,node.dataNumber-1,temNode);
					}
				}
			}else{
				//没有非hunger型节点，则合并节点
				if(leftKey>=rightKey){
					//左节点参与合并
					//数据合并
					System.arraycopy(node.data, 0, lnode.data, lnode.dataNumber, node.dataNumber);
					if(node.children!=null){
						//如果子节点不为空，参与合并的两个节点的孩子也要合并
						System.arraycopy(node.children, 0, lnode.children, lnode.dataNumber, node.dataNumber);
					}
					lnode.dataNumber = node.dataNumber+lnode.dataNumber;
					delChildNode(parent,index,true);
					delData(parent,index);
				}else{
					//右节点参与合并
					//数据合并
					System.arraycopy(rnode.data, 0, node.data, node.dataNumber, rnode.dataNumber);
					if(node.children!=null){
						//如果子节点不为空，参与合并的两个节点的孩子也要合并
						System.arraycopy(rnode.children, 0, node.children, node.dataNumber, rnode.dataNumber);
					}
					node.dataNumber = node.dataNumber+rnode.dataNumber;
					//删除父节点data，和child
					delChildNode(parent,index+1,true);
					delData(parent,index+1);
				}
			}
			
		}
	}
	/**
	 * 获取最小关键字数
	 * @return
	 */
	public int minKeyNumber(){
		return  m%2==0?m/2:m/2+1;
	}
	/**
	 * 替换
	 * @param node
	 * @param index
	 * @param entry
	 * @return
	 */
	public Entry repData(Node node,int index,Entry entry){
		Entry temp = node.data[index];
		node.data[index]=new SimpleEntry(entry.getKey(), null);
		return temp;
	}
	
	/**
	 * 删除index对应孩子节点并返回
	 * @param node 当前节点
	 * @param index children索引
	 * @return
	 */
	public Node delChildNode(Node node,int index,boolean isAbandon){
		Node ret = node.children[index];
		for(int i=index;i<node.dataNumber;i++){
			node.children[i] = node.children[i+1];
		}
		if(isAbandon) {
			//若丢弃该节点，需要修改上个几点的nextNode
			node.children[index-1].nextNode = ret.nextNode;
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
	/**
	 * 添加子节点
	 * @param node
	 * @param index
	 * @param child
	 */
	public void addChildNode(Node node,int index,Node child){
		System.arraycopy(node.children, index, node.children,index+1,node.dataNumber-(index));
		node.children[index] = child;
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
			System.out.print("[");
			for(int i=0;i<node.dataNumber;i++){
				System.out.print(node.data[i]+",");
			}
			System.out.print("]");
			node = node.nextNode;
		}
	}
	
	public static void main(String[] args) {
		BAdditionTree bat = new BAdditionTree(10);
		
		int length = 100000;
		int[] data = new int[length];
		String[] strs = new String[length];
		Random r = new Random();
		for(int i=0;i<length;i++){
			int tem = r.nextInt(length*10);
			data[i]=tem;
			strs[i]="a"+tem;
		}
//		for(int i=0;i<data.length;i++) {
//			System.out.print(data[i]+",");
//		}
//		System.out.println();
//		for(int i=0;i<strs.length;i++) {
//			System.out.print("\""+strs[i]+"\",");
//		}
//		System.out.println();
//		Integer[] arr = new Integer[]{5,18,26,30,8,11,3,1};
//		String[] str = new String[]{"a","b","c","d","e","f","g","h"};
		
//		Integer[] arr = new Integer[]{5,28,36,40,8,21,3,1,6,7,18,20,2,4,0,22,23,24,25,50,60,70};
//		String[] str = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","u","v","w"};
//		Integer[] arr = new Integer[]{778,23,877,126,604,368,308,523,899,479,577,186,385,862,428,29,437,611,56,607,524,178,796,901,908,950,508,451,238,32,547,258,207,0,639,816,875,712,930,552,639,617,360,136,754,364,730,602,991,347,941,450,601,128,95,590,347,794,704,507,867,193,49,164,482,271,5,342,644,502,624,431,598,64,775,884,496,858,86,401,337,114,688,612,17,695,390,475,376,567,991,387,712,603,480,746,25,703,881,68};
//		String[] str = new String[]{"a778","a23","a877","a126","a604","a368","a308","a523","a899","a479","a577","a186","a385","a862","a428","a29","a437","a611","a56","a607","a524","a178","a796","a901","a908","a950","a508","a451","a238","a32","a547","a258","a207","a0","a639","a816","a875","a712","a930","a552","a639","a617","a360","a136","a754","a364","a730","a602","a991","a347","a941","a450","a601","a128","a95","a590","a347","a794","a704","a507","a867","a193","a49","a164","a482","a271","a5","a342","a644","a502","a624","a431","a598","a64","a775","a884","a496","a858","a86","a401","a337","a114","a688","a612","a17","a695","a390","a475","a376","a567","a991","a387","a712","a603","a480","a746","a25","a703","a881","a68"};
//		
		Long startTime = System.currentTimeMillis();
		for(int i=0;i<data.length;i++){
			bat.add(data[i],strs[i]);
		}
		Long endTime = System.currentTimeMillis();
		System.out.println("插入"+data.length+"条数据耗时"+(endTime-startTime)+"毫秒");
//		bat.print(bat.head.nextNode);
		
//		System.out.println("查找："+data[100]+" 返回结果为--> "+bat.getNode(data[100]++));
//		System.out.println("查找："+data[100]+" 返回结果为--> "+bat.getNode(data[100]++));
//		System.out.println("查找："+data[100]+" 返回结果为--> "+bat.getNode(data[100]++));
//		System.out.println("查找："+data[100]+" 返回结果为--> "+bat.getNode(data[100]++));

		Long startTime1 = System.currentTimeMillis();
		for(int i=0;i<data.length;i++){
			bat.delete(data[i]);
		}
		Long endTime1 = System.currentTimeMillis();
		System.out.println("删除"+data.length+"条数据耗时"+(endTime1-startTime1)+"毫秒");
		bat.print(bat.head.nextNode);
	}
}
