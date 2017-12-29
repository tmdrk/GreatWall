package com.test.sort;

/**
 * 快速排序
 * 不稳定排序
 * @ClassName: QuickSort 
 * @author zhoujie
 * @date 2017年12月27日 上午11:39:21
 */
public class QuickSort extends BaseSort{
	public static void main(String[] args) {
		int[] numbers = {10,5,32,45,31,25,16,65,78,35,9,16,44,13,34,38,66,89,31,55};
		printArray("排序前",numbers);
		sort(numbers);
		printArray("排序后",numbers);
	}
	public static void sort(int[] numbers){
		int start = 0;
		int end = numbers.length-1;
		quickSort(numbers,start,end);
	}
	
	public static void quickSort(int[] numbers,int start,int end){
		int start1 = start;
		int end1 = end;
		int baseIndex = start;
		int temp = numbers[baseIndex];
		boolean right = true;
		while(start<end){
			if(right&&temp>numbers[end]){
				numbers[baseIndex]=numbers[end];
				baseIndex = end--;
				right = false;
				continue;
			}else if(right){
				end--;
			}
			if(!right&&temp<numbers[start]){
				numbers[baseIndex]=numbers[start];
				baseIndex = start++;
				right = true;
				continue;
			}else if(!right){
				start++;
			}
		}
		numbers[baseIndex] = temp;
		//保证至少有两个元素参与排序比较
		if(baseIndex-start1>1){
			quickSort(numbers,start1,baseIndex-1);
		}
		if(end1-baseIndex>1){
			quickSort(numbers,baseIndex+1,end1);
		}
	}
}
