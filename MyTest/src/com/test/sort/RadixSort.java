package com.test.sort;

/**
 * 基数排序
 * https://www.cnblogs.com/Braveliu/archive/2013/01/21/2870201.html
 * https://www.cnblogs.com/developerY/p/3172379.html
 * 基于两种不同的排序顺序，我们将基数排序分为LSD（Least significant digital）或MSD（Most significant digital），
 * LSD的排序方式由数值的最右边（低位）开始，而MSD则相反，由数值的最左边（高位）开始。
 *
 * 特点：稳定排序，基于内容的排序（其他排序是基于比较的排序,最优速度O(logn)）最优速度O(n)
 * @ClassName: RadixSort 
 * @author zhoujie
 * @date 2017年12月28日 上午10:45:17
 */
public class RadixSort extends BaseSort{
	public static void main(String[] args) {
		int[] numbers = {10,-5,32,45,31,25,16,-65,6,-78,35,9,16,44,13,34,38,66,-99889,9931,55};
//		int[] numbers = {98,88,32,106760,31};
		printArray("排序前",numbers);
		sort(numbers);
		printArray("排序后",numbers);
		System.out.println(depth(numbers));
	}
	
	public static void sort(int[] numbers){
		sortLSD(numbers,depth(numbers));
	}
	
	public static void sortLSD(int[] numbers,int d){
		int[][] store = new int[19][numbers.length];
		int[] index = new int[19];
		int n=1;
		int k = 0;
		while(n<d){
			for(int num:numbers){
				int digit = (num/n)%10+9;
				store[digit][index[digit]] = num;
				index[digit]++;
			}
			for(int i=0;i<19;i++){
				if(index[i]>0){
					for(int j=0;j<index[i];j++){
						numbers[k] = store[i][j];
						k++;
					}
				}
				index[i]=0;
			}
			n*=10;
			k = 0;
		}
	}
	
	public static void sortMSD(int[] numbers,int d){
		
	}
	
	public static int depth(int[] numbers){
		int max = 0;
		for(int i=0;i<numbers.length-1;i++){
			if(numbers[i]<numbers[i+1]){
				max = i+1;
			}
		}
		int n = 10;
		while(true){
			if(numbers[max]/n==0){
				return n;
			}
			n*=10;
		}
	}
}
