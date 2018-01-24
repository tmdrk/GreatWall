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
		Integer[] data = new Integer[m+1];
		Node[] children;
		
		public Node(){
		}
		public Node(int v){
			this.keyNumber = 1;
			this.data[0] = v;
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
			parent.children = new Node[m];
			parent.children[0] = new Node(ele);
			result = true;
		}else if(parent.children[index].children==null){
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
			if(parent.children[index].keyNumber==m){//关键字 大于b树阶数减1，需要分裂
				Integer[] numbers = parent.children[index].data;
				int middle = numbers.length/2;
				Integer[] array1 = new Integer[1];
				Integer[] array2 = new Integer[middle];
				Integer[] array3 = new Integer[numbers.length%2==0?middle-1:middle];
				System.arraycopy(numbers, middle, array1, 0,1);
				System.arraycopy(numbers, 0, array2, 0, array2.length);
				System.arraycopy(numbers, middle+1, array3, 0, array3.length);
				printArray("a",array1);
				printArray("b",array2);
				printArray("c",array3);
			}
			result = true;
		}else{
			result = add(parent.children[index],getIndex(parent.children[index],ele),ele);
			
		}
		return result;
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
		int[] arr = new int[]{12,23,15,13,22,17,25};
		for(int i=0;i<arr.length;i++){
			but.add(arr[i]);
		}
//		int[] numbers = new int[]{12,13,15};
//		int middle = numbers.length/2;
//		int[] array1 = new int[1];
//		int[] array2 = new int[middle];
//		int[] array3 = new int[numbers.length%2==0?middle-1:middle];
//		System.arraycopy(numbers, middle, array1, 0,1);
//		System.arraycopy(numbers, 0, array2, 0, array2.length);
//		System.arraycopy(numbers, middle+1, array3, 0, array3.length);
//		printArray("a",array1);
//		printArray("b",array2);
//		printArray("c",array3);
	}
	public static void printArray(String pre,Integer[] a){
		System.out.print(pre+":");
		for(Integer i:a){
			System.out.print(i+",");
		}
		System.out.println();
	}
}
