package com.test.sort;

/**
 * 冒泡排序：依次比较相邻的数据，将小数据放在前，大数据放在后；即第一趟先比较第1个和第2个数，大数在后，
 * 小数在前，再比较第2个数与第3个数，大数在后，小数在前，以此类推则将最大的数"滚动"到最后一个位置；
 * 第二趟则将次大的数滚动到倒数第二个位置......第n-1(n为无序数据的个数)趟即能完成排序。
 * 稳定排序
 * @ClassName: BubbleSort 
 * @author zhoujie
 * @date 2017年12月26日 下午4:48:10
 */
public class BubbleSort extends BaseSort{
	public static void main(String[] args) {
		int[] numbers = {10,5,32,45,31,25,16,65,78,35,9,16,44,13,38};
		printArray("排序前",numbers);
		sort(numbers);
		printArray("排序后",numbers);
	}
	public static void sort(int[] arr){
		for(int i=0;i<arr.length-1;i++){
			for(int j=0;j<arr.length-i-1;j++){
				if(arr[j]>arr[j+1]){
					int t=arr[j];
					arr[j]=arr[j+1];
					arr[j+1]=t;
				}
			}
		}
	}
	
}
