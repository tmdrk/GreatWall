package com.test.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapTest {
	public static void main(String[] args) {
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("id", 1);
		map1.put("name", "aaaaaaaaaaaa宽带缴费");
		map1.put("type", "3");
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("id", 2);
		map2.put("name", "nide老师肯定会烦死了电话费aaa");
		map2.put("types", "2");
		System.out.println(map2.put("name", "66"));
		System.out.println(map2.remove("name"));
		Random random = new Random();
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		int sum4 = 0;
		for(int i=0;i<1000000;i++){
//			main();
			String a = "开啊"+random.nextInt(9000000);
			int b = (int) ((hash(a)&(3))+1);
//			System.out.println(a);
//			System.out.println(hash(a+""));
//			System.out.println((hash(a+"")&(3))+1);
			if(b==1){
				sum1++;
			}else if(b==2){
				sum2++;
			}else if(b==3){
				sum3++;
			}else if(b==4){
				sum4++;
			}
		}
		System.out.println("sum1="+sum1);
		System.out.println("sum2="+sum2);
		System.out.println("sum3="+sum3);
		System.out.println("sum4="+sum4);
		
		Integer a = 128;
		Integer b = 128;
		System.out.println(a==b);
		Integer a1 = 127;
		Integer b1 = 127;
		System.out.println(a1==b1);
		
		int aa = 128;
		int bb = 128;
		System.out.println(aa==bb);
		int aa1 = 127;
		int bb1 = 127;
		System.out.println(aa1==bb1);
		
		Long m = 127L;
		Long n = 127L;
		System.out.println(m==n);
		
		int ss1 = 100001;
		int ss2 = 100000;
		Long startTime1 = System.currentTimeMillis();
		for(int i=0;i<ss1-1;i++){
			for(int j=0;j<=ss1-1;j++){
				
			}
		}
		Long endTime1 = System.currentTimeMillis();
		System.out.println("< --"+(endTime1-startTime1));
		
		Long startTime2 = System.currentTimeMillis();
		for(int i=0;i<ss2;i++){
			for(int j=0;j<ss2;j++){
				
			}
		}
		Long endTime2 = System.currentTimeMillis();
		System.out.println("<= --"+(endTime2-startTime2));
		String aaa = "1000.00000";
		System.out.println(aaa.split("\\.")[0]);
	}
	
	public static void main() {
		Random random = new Random();
		System.out.println(hash(String.valueOf(random.nextInt(900)+""))&(4));
	}
	
	public static long hash(String key) { 
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}
}
