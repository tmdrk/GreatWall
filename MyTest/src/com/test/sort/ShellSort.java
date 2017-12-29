package com.test.sort;

import java.util.Random;

/**
 * 希尔排序
 * 不稳定排序
 * 希尔排序Shell Sort 舞蹈 http://v.youku.com/v_show/id_XMjU4NTcwMDIw.html#paction
 * @ClassName: ShellSort 
 * @author zhoujie
 * @date 2017年12月28日 上午9:27:21
 */
public class ShellSort extends BaseSort{
	public static void main(String[] args) {
//		int[] numbers = {10,5,32,45,31,25,16,65,78,35,9,16,44,13,34,38,66,89,31,55};
		int length = 2000;
		int[] numbers = new int[length];
		Random r = new Random();
		for(int i=0;i<length;i++){
			numbers[i]=r.nextInt(length);
		}
		printArray("排序前",numbers);
		sort(numbers);
		printArray("排序后",numbers);
	}
	public static void sort(int[] numbers){
		int h = 1;  
        while (h <= numbers.length / 3) {  
            h = h * 3 + 1;  
        }
        while(h>0){
        	for(int i=h;i<numbers.length;i++){
        		for(int j=i;j>=h&&numbers[j]<numbers[j-h];j-=h){
        			swap(numbers,j,j-h);
        		}
        	}
        	h=h/3;
        }
	}
	public static void swap(int[] numbers,int i,int j){
		int temp = numbers[i];
		numbers[i] = numbers[j];
		numbers[j] = temp;
	}
}
