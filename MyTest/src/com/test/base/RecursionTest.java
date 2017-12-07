package com.test.base;

public class RecursionTest {
	public static void main(String[] args) {
		for(int i=0;i<1000;i++){
			System.out.println(recursion(16330,0));
		}
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
}
