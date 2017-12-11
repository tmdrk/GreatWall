package com.test.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapTest {
	public static void main(String[] args) {
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("id", 1);
		map1.put("name", "宽带缴费!");
		map1.put("type", "3");
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("id", 2);
		map2.put("name", "小小小小阿达");
		map2.put("types", "2");
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
