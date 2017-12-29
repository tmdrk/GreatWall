package structure.heap.sort;

public class BuildHeap {
	public static void main(String[] args) {
		int[] tree = {10,5,32,45,31,25,16,65,78,35,9,16,44,13,38};
		buildHeap(tree);
	}
	/**
	 * 构建堆
	 * @param tree
	 * @author zhoujie
	 * @date 2017年12月26日 下午2:02:41
	 */
	public static void buildHeap(int[] tree){
//		printArray("树结构",tree);
		int lastIndex = tree.length -1;
		for(int i=(lastIndex-1)/2;i>=0;i--){
			adjustHeap(tree,i,lastIndex);
		}
//		printArray("堆结构",tree);
	}
	/**
	 * 调整堆
	 * @param tree
	 * @param rootIndex
	 * @param lastIndex
	 * @author zhoujie
	 * @date 2017年12月26日 下午2:02:56
	 */
	public static void adjustHeap(int[] tree,int rootIndex,int lastIndex){
		int biggerIndex = rootIndex;
		int leftChildIndex = 2*rootIndex + 1;
		int rightChildIndex = 2*rootIndex + 2;
		if(rightChildIndex<=lastIndex){
			if(tree[rootIndex]<tree[leftChildIndex]||tree[rootIndex]<tree[rightChildIndex]){
				biggerIndex = tree[leftChildIndex]>tree[rightChildIndex]?leftChildIndex:rightChildIndex;
			}
		}else if(leftChildIndex<=lastIndex){
			if(tree[rootIndex]<tree[leftChildIndex]){
				biggerIndex = leftChildIndex;
			}
		}
		if(biggerIndex!=rootIndex){
			swap(tree,rootIndex,biggerIndex);
			if(biggerIndex<=(lastIndex-1)/2){
				adjustHeap(tree,biggerIndex,lastIndex);
			}
		}
	}
	/**
	 * 交换
	 * @param tree
	 * @param rootIndex
	 * @param biggerIndex
	 * @author zhoujie
	 * @date 2017年12月26日 下午2:03:08
	 */
	public static void swap(int[] tree,int rootIndex,int biggerIndex){
		int temp = tree[rootIndex];
		tree[rootIndex] = tree[biggerIndex];
		tree[biggerIndex] = temp;
	}
	/**
	 * 打印
	 * @param pre
	 * @param a
	 * @author zhoujie
	 * @date 2017年12月26日 下午2:03:30
	 */
	public static void printArray(String pre,int[] a){
		System.out.print(pre+":");
		for(int i:a){
			System.out.print(i+",");
		}
		System.out.println();
	}
}
