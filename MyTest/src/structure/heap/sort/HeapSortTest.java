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
		int length = 100000000;
		int[] tree = new int[length];
		Random r = new Random();
		for(int i=0;i<length;i++){
			tree[i]=r.nextInt(length);
		}
//		int[] tree1 = tree.clone();
//		int[] tree2 = tree.clone();
//		int[] tree3 = tree.clone();
//		int[] tree4 = tree.clone();
//		int[] tree5 = tree.clone();
//		int[] tree6 = tree.clone();
//		int[] tree7 = tree.clone();
//		int[] tree8 = tree.clone();
		
//		long start = nowTime(length);
//		sortHeap(tree);
//		long end = nowTime(length);
//		printTime("堆排序",start,end,length);
		
//		long start1 = nowTime(length);
//		InsertSort.sort(tree1);
//		long end1 = nowTime(length);
//		printTime("插入排序",start1,end1,length);
//		
//		long start2 = nowTime(length);
//		BubbleSort.sort(tree2);
//		long end2 = nowTime(length);
//		printTime("冒泡排序",start2,end2,length);
//		
//		long start3 = nowTime(length);
//		SelectionSort.sort(tree3);
//		long end3 = nowTime(length);
//		printTime("选择排序",start3,end3,length);
		
//		long start4 = nowTime(length);
//		MergeSort.sort(tree4);
//		long end4 = nowTime(length);
//		printTime("归并排序",start4,end4,length);
//		
//		long start5 = nowTime(length);
//		MergeParallelSort.sort(tree5);
//		long end5 = nowTime(length);
//		printTime("并行归并排序",start5,end5,length);
//		
		long start6 = nowTime(length);
		QuickSort.sort(tree);
		long end6 = nowTime(length);
		printTime("快速排序",start6,end6,length);
		
//		long start7 = nowTime(length);
//		RadixSort.sort(tree7);
//		long end7 = nowTime(length);
//		printTime("基数排序",start7,end7,length);
		
//		long start8 = nowTime(length);
//		ShellSort.sort(tree8);
//		long end8 = nowTime(length);
//		printTime("希尔排序",start8,end8,length);
		
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
	
	public static long nowTime(int length){
		if(length>1000){
			return System.currentTimeMillis();
		}else{
			return System.nanoTime();
		}
	}
	public static void printTime(String name,long start,long end,int length){
		if(length>1000){
			System.out.println(name+"耗时："+(end-start)+"毫秒");
		}else{
			System.out.println(name+"耗时："+(end-start)+"纳秒");
		}
	}
}
