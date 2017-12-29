package structure.heap.sort;

import java.util.Random;

import com.test.sort.BubbleSort;
import com.test.sort.InsertSort;
import com.test.sort.MergeParallelSort;
import com.test.sort.MergeSort;
import com.test.sort.QuickSort;
import com.test.sort.RadixSort;
import com.test.sort.SelectionSort;
import com.test.sort.ShellSort;

public class HeapSortTest {
	public static void main(String[] args) {
		int length = 10000;
		int[] tree = new int[length];
		Random r = new Random();
		for(int i=0;i<length;i++){
			tree[i]=r.nextInt(length);
		}
		int[] tree1 = tree.clone();
		int[] tree2 = tree.clone();
		int[] tree3 = tree.clone();
		int[] tree4 = tree.clone();
		int[] tree5 = tree.clone();
		int[] tree6 = tree.clone();
		int[] tree7 = tree.clone();
		int[] tree8 = tree.clone();
		
		long start = System.nanoTime();
		sortHeap(tree);
		long end = System.nanoTime();
		System.out.println("堆排序耗时："+(end-start)+"纳秒 ");
		
		long start1 = System.nanoTime();
		InsertSort.sort(tree1);
		long end1 = System.nanoTime();
		System.out.println("插入排序耗时："+(end1-start1)+"纳秒 ");
		
		long start2 = System.nanoTime();
		BubbleSort.sort(tree2);
		long end2 = System.nanoTime();
		System.out.println("冒泡排序耗时："+(end2-start2)+"纳秒 ");
		
		long start3 = System.nanoTime();
		SelectionSort.sort(tree3);
		long end3 = System.nanoTime();
		System.out.println("选择排序耗时："+(end3-start3)+"纳秒 ");
		
		long start4 = System.nanoTime();
		MergeSort.sort(tree4);
		long end4 = System.nanoTime();
		System.out.println("归并排序耗时："+(end4-start4)+"纳秒 ");
		
		long start5 = System.nanoTime();
		MergeParallelSort.sort(tree5);
		long end5 = System.nanoTime();
		System.out.println("并行归并排序耗时："+(end5-start5)+"纳秒");
		
		long start6 = System.nanoTime();
		QuickSort.sort(tree6);
		long end6 = System.nanoTime();
		System.out.println("快速排序耗时："+(end6-start6)+"纳秒");
		
		long start7 = System.nanoTime();
		RadixSort.sort(tree7);
		long end7 = System.nanoTime();
		System.out.println("基数排序耗时："+(end7-start7)+"纳秒");
		
		long start8 = System.nanoTime();
		ShellSort.sort(tree8);
		long end8 = System.nanoTime();
		System.out.println("希尔排序耗时："+(end8-start8)+"纳秒");
//		BuildHeap.printArray("排序后",tree7);
		
	}
	public static void sortHeap(int[] tree){
		BuildHeap.buildHeap(tree);
		int newIndex = tree.length-1;
		while(newIndex>0){
			BuildHeap.swap(tree, 0, newIndex);
			if(--newIndex==0){
				break;
			}
			BuildHeap.adjustHeap(tree, 0, newIndex);
		}
//		BuildHeap.printArray("堆排序",tree);
	}
}
