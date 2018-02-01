package com.test.base;

public class RecursionTest {
	public static void main(String[] args) {
//		for(int i=0;i<1000;i++){
//			System.out.println(binaryRecursion(1,100L));
//		}
		long start = System.currentTimeMillis();
		long sum =0;
		for(long i=0;i<=1000000;i++){
			sum = sum+i;
		}
		long end = System.currentTimeMillis();
		System.out.println(sum);
		System.out.println("循环求和耗时："+(end-start)+"毫秒");
		long start1 = System.currentTimeMillis();
		System.out.println(binaryRecursion(1,1000000L));
		long end1 = System.currentTimeMillis();
		System.out.println("递归求和耗时："+(end1-start1)+"毫秒");
	}
	public static long recursion(long index,long sum){
		if(index<=0){
			return sum;
		}else{
			sum = sum+index;
			index--;
			return recursion(index,sum);
		}
	}
	
	public static long binaryRecursion(long start,long end){
		if(end==start){
			return end;
		}
		long mid = (start+end)/2;
		return binaryRecursion(start,mid)+binaryRecursion(mid+1,end);
	}
}
