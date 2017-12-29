package com.test.sort;

/**
 *  选择排序：比如在一个长度为N的无序数组中，在第一趟遍历N个数据，找出其中最小的数值与第一个元素交换，
 *  第二趟遍历剩下的N-1个数据，找出其中最小的数值与第二个元素交换......第N-1趟遍历剩下的2个数据，
 *  找出其中最小的数值与第N-1个元素交换，至此选择排序完成。
 *  选择排序
 * @ClassName: SelectionSort 
 * @author zhoujie
 * @date 2017年12月26日 下午4:47:31
 */
public class SelectionSort extends BaseSort{
	public static void main(String[] args) {
		int[] numbers = {10,5,32,45,31,25,16,65,78,35,9,16,44,13,38};
		printArray("排序前",numbers);
		sort(numbers);
		printArray("排序后",numbers);
	}
	public static void sort(int[] numbers){
		int index = 0;
		for(int i=0;i<numbers.length-1;i++){
			index = i;
			for(int j=i+1;j<numbers.length;j++){
				if(numbers[index]>numbers[j]){
					index = j;
				}
			}
			int temp = numbers[i];
			numbers[i] = numbers[index];
			numbers[index] = temp;
		}
	}
}
