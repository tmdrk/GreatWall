package com.test.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RedPacketsUtil {
	public static void main(String[] args) {
		int sum = 100;
		int number = 10;
		int remainNumber = number;
		int min = 6;
		int maxSum = 14;
		int max = sum - remainNumber + min;
		int temp = 0;
		Random ran = new Random();
		Map<String,Object> map = new HashMap<String, Object>();
		for(int i=0;i<number;i++){
			if(number==i+1){
				System.out.println("人物"+(i+1)+" "+sum);
				map.put("人物"+(i+1),sum);
				break;
			}
			temp = ran.nextInt(maxSum-min+1)+min;
			System.out.println("人物"+(i+1)+" "+temp);
			map.put("人物"+(i+1),temp);
			sum -= temp;
			remainNumber--;
			max = sum - remainNumber + min;
		}
	}
	
//	//分几个红包
//	static int num = 10;
//	//一共多少钱
//	static int money = 100;
//	//每个人最少得多少
//	static int min = 6;
//	//每个人最多得多少
//	static int max = 14;
//	static int[] array = new int[num];
//	public static void main(String[] args) {
//		Random random = new Random();
//		for (int i = 0; i < num; i++) {
//			int[] calculateMaxMin = calculateMaxMin(i);
//			int num = random.nextInt(calculateMaxMin[1] - calculateMaxMin[0] + 1) + calculateMaxMin[0];
//			array[i] = num;
//		}
//		int sum=0;
//		for (int i : array) {
//			System.out.println(i);
//			sum+=i;
//		}
//		System.out.println("sum="+sum);
//	}
//	private static int[] calculateMaxMin(int currentIndex) {
//		int[] range = new int[2];
//		int sum = 0;
//		for (int i = 0; i < currentIndex; i++) {
//			sum += array[i];
//		}
//		//剩余可以分配的钱
//		int remainMoney = money - sum;
//		//剩余可以分配的人数
//		int remainNum = num - currentIndex;
//		if (remainNum <= 1) {
//			range[0] = remainMoney;
//			range[1] = remainMoney;
//			return range;
//		}
//		//本次分配红包的最大金额
//		int tempMax = remainMoney - (remainNum - 1) * min ;
//		if (tempMax > max) {
//			tempMax = max;
//		}
//		//本次分配红包的最小金额
//		int tempMin = remainMoney - (remainNum - 1) * max;
//		if (tempMin < min) {
//			tempMin = min;
//		}
//		range[1] = tempMax;
//		range[0] = tempMin;
//		return range;
//	}
}
