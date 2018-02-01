package com.test.dataStructures.tree.BTree;

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
public class BUnderscodeTree {
	/** 哨兵 **/
	Node sentinel = new Node();
	/** 树容量 **/
	int size = 0;
	/** B树的阶数 **/
	public static final int m = 3;
	public class Node {
		/** 关键字个数 **/
		int keyNumber;
		/** 数据 **/
		Integer[] data = new Integer[m];
		Node[] children;
		
		public Node(){
		}
		public Node(int v){
			this.keyNumber = 1;
			this.data[0] = v;
		}
		public Node(Integer[] data,int keyNumber){
			this.keyNumber = keyNumber;
			this.data = data;
		}
		public Node(Integer[] data,int keyNumber,Node[] children){
			this.keyNumber = keyNumber;
			this.data = data;
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
	 * 新增
	 * @param ele 新增的元素
	 * @author zhoujie
	 * @date 2018年1月23日 下午4:13:15
	 */
	public boolean add(int ele){
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
			/** 添加元素 **/
			addTree(parent,index,ele);

			/** 检查元素是否需要分裂 **/
			if(parent.children[index].keyNumber==m){//关键字 大于b树阶数减1，需要分裂
				if(parent.children[index].equals(getRoot())){//当前操作元素是根节点时分裂
					Integer[] tempDatas = parent.children[index].data;
					int middle = tempDatas.length/2;
					Integer[] rootData = new Integer[m];
					Integer[] leftData = new Integer[m];
					Integer[] rightData = new Integer[m];
					System.arraycopy(tempDatas, middle, rootData, 0,1);
					System.arraycopy(tempDatas, 0, leftData, 0, middle);
					System.arraycopy(tempDatas, middle+1, rightData, 0, m-(middle+1));
					parent.children[index].keyNumber=1;
					parent.children[index].data = rootData;
					if(parent.children[index].children==null){
						parent.children[index].children = new Node[m+1];
						Node leftNode = new Node(leftData,middle);
						Node rightNode = new Node(rightData,m-(middle+1));
						parent.children[index].children[0] = leftNode;
						parent.children[index].children[1] = rightNode;
					}else{
						Node[] tempNodes = parent.children[index].children;
						int middleNode = tempDatas.length/2;
						Node[] leftNodes = new Node[m+1];
						Node[] rightNodes = new Node[m+1];
						System.arraycopy(tempNodes, 0, leftNodes, 0, middleNode);
						System.arraycopy(tempNodes, middleNode+1, rightNodes, 0, m-(middleNode+1));
						
						Node leftNode = new Node(leftData,middle,leftNodes);
						Node rightNode = new Node(rightData,m-(middle+1),rightNodes);
						Node[] pNode = new Node[m+1];
						pNode[0] = leftNode;
						pNode[1] = rightNode;
						parent.children[index].children = pNode;
					}
				}
			}
			result = true;
		}else{
			result = add(parent.children[index],getIndex(parent.children[index],ele),ele);
			
			if(parent.children[index].children==null){
				Integer[] tempDatas = parent.children[index].data;
				int middle = tempDatas.length/2;
				Integer[] leftData = new Integer[m];
				Integer[] rightData = new Integer[m];
				System.arraycopy(tempDatas, 0, leftData, 0, middle);
				System.arraycopy(tempDatas, middle+1, rightData, 0, m-(middle+1));
				
				int midDate = tempDatas[middle];
				boolean flag = true;
				for(int i=0;i<parent.keyNumber;i++){
					if(flag){
						if(parent.data[i]>midDate){
							int temp = parent.data[i];
							parent.data[i] = midDate;
							midDate = temp;
							flag = false;
						}
					}else{
						int temp = parent.data[i];
						parent.data[i] = ele;
						ele = temp;
					}
				}
				parent.data[parent.keyNumber]=ele;
				parent.keyNumber++;
				
				Node leftNode = new Node(leftData,middle);
				Node rightNode = new Node(rightData,m-(middle+1));

			}else{
				
			}
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
	public void addTree(Node parent,int index,int ele){
		boolean flag = true;
		for(int i=0;i<parent.children[index].keyNumber;i++){
			if(flag){
				if(parent.children[index].data[i]>ele){
					int temp = parent.children[index].data[i];
					parent.children[index].data[i] = ele;
					ele = temp;
					flag = false;
				}
			}else{
				int temp = parent.children[index].data[i];
				parent.children[index].data[i] = ele;
				ele = temp;
			}
		}
		parent.children[index].data[parent.children[index].keyNumber] = ele;
		parent.children[index].keyNumber++;
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
	
	public static void main(String[] args) {
		BUnderscodeTree but = new BUnderscodeTree();
		Node n = but.new Node(3);
//		int s = n.data[1];
//		System.out.println(n.data[0]+"|"+n.data[1]+"|"+s);
		Integer[] arr = new Integer[]{12,23,15,8,10};
		for(int i=0;i<arr.length;i++){
			but.add(arr[i]);
		}
		
//		Integer[] numbers = new Integer[]{12,15,23};
//		int middle = numbers.length/2;
//		Integer[] array1 = new Integer[numbers.length];
//		Integer[] array2 = new Integer[numbers.length];
//		Integer[] array3 = new Integer[numbers.length];
//		System.arraycopy(numbers, middle, array1, 0,1);
//		System.arraycopy(numbers, 0, array2, 0, middle);
//		System.arraycopy(numbers, middle+1, array3, 0, array3.length-(middle+1));
//		printArray("array1",array1);
//		printArray("array2",array2);
//		printArray("array3",array3);
		
//		Integer[] parent = new Integer[]{10,50,69,null,null,null,null,null,null};
//		int pLen = 3;
//		Integer[] numbers2 = new Integer[]{12,13,15,18,20,22,31,38,40};
//		int middle2 = numbers2.length/2;
//		Integer[] array4 = new Integer[numbers2.length];
//		Integer[] array5 = new Integer[numbers2.length];
//		System.arraycopy(numbers2, 0, array4, 0, middle2);
//		System.arraycopy(numbers2, middle2+1, array5, 0, array5.length-(middle2+1));
//		int ele = numbers2[middle2];
//		boolean flag = true;
//		for(int i=0;i<pLen;i++){
//			if(flag){
//				if(parent[i]>ele){
//					int temp = parent[i];
//					parent[i] = ele;
//					ele = temp;
//					flag = false;
//				}
//			}else{
//				int temp = parent[i];
//				parent[i] = ele;
//				ele = temp;
//			}
//		}
//		parent[pLen]=ele;
//		printArray("parent",parent);
//		printArray("array4",array4);
//		printArray("array5",array5);
	}
	
	public static void printArray(String pre,Integer[] a){
		System.out.print(pre+":");
		for(Integer i:a){
			System.out.print(i+",");
		}
		System.out.println();
	}
}
