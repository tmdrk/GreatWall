package com.test.dataStructures.tree.BTree;

import java.util.Random;

import com.alibaba.fastjson.JSON;

public class B_Test {
	public static final int m = 1000;
	public static void main(String[] args) {
		int length = 100000;
		int[] data = new int[length];
		Random r = new Random();
		for(int i=0;i<length;i++){
			data[i]=r.nextInt(length*10);
		}
		int[] data1 = data.clone();
		int[] data2 = data.clone();
		BUnderscodeTree but1 = new BUnderscodeTree(m);
		BUnderscodeTree2 but2 = new BUnderscodeTree2(m);
		
//		int[] d1 = new int[length];
//		for(int i=0;i<length;i++){
//			data1[i]=r.nextInt(length*10);
//		}
		
		Long startTime11 = System.currentTimeMillis();
		for(int i=0;i<data1.length;i++){
			but1.add(data1[i]);
		}
		Long endTime11 = System.currentTimeMillis();
		System.out.println("插入"+data1.length+"条数据耗时"+(endTime11-startTime11)+"毫秒");
		
		Long startTime12 = System.currentTimeMillis();
		for(int i=0;i<data1.length;i++){
			but1.getNode(data1[i]);
		}
		Long endTime12 = System.currentTimeMillis();
		System.out.println("查询"+data1.length+"条数据耗时"+(endTime12-startTime12)+"毫秒");
		
		Long startTime13 = System.currentTimeMillis();
		for(int i=0;i<data1.length;i++){
			but1.getAddNode(data1[i]);
//			System.out.println(data1[i]+":"+but1.getAddNode(data1[i]));
		}
		Long endTime13 = System.currentTimeMillis();
		System.out.println("获取添加节点，查询"+data1.length+"条数据耗时"+(endTime13-startTime13)+"毫秒");
		
		Long startTime14 = System.currentTimeMillis();
		for(int i=0;i<data1.length;i++){
			but1.delete(data1[i]);
		}
		Long endTime14 = System.currentTimeMillis();
		System.out.println("删除"+data1.length+"条数据耗时"+(endTime14-startTime14)+"毫秒");
//		System.out.println(JSON.toJSONString(but1.getRoot(),true));
		
		System.out.println();
				
		Long startTime21 = System.currentTimeMillis();
		for(int i=0;i<data2.length;i++){
			but2.add(data2[i]);
		}
		Long endTime21 = System.currentTimeMillis();
		System.out.println("插入"+data2.length+"条数据耗时"+(endTime21-startTime21)+"毫秒");
		
		Long startTime22 = System.currentTimeMillis();
		for(int i=0;i<data2.length;i++){
			but2.getNode(data2[i]);
		}
		Long endTime22 = System.currentTimeMillis();
		System.out.println("查询"+data2.length+"条数据耗时"+(endTime22-startTime22)+"毫秒");
		
		Long startTime23 = System.currentTimeMillis();
		for(int i=0;i<data2.length;i++){
			but2.getAddNode(data2[i]);
//			System.out.println(data2[i]+":"+but2.getAddNode(data2[i]));
		}
		Long endTime23 = System.currentTimeMillis();
		System.out.println("获取添加节点，查询"+data2.length+"条数据耗时"+(endTime23-startTime23)+"毫秒");
		
		Long startTime24 = System.currentTimeMillis();
		for(int i=0;i<data2.length;i++){
			but2.delete(data1[i]);
		}
		Long endTime24 = System.currentTimeMillis();
		System.out.println("删除"+data2.length+"条数据耗时"+(endTime24-startTime24)+"毫秒");
//		System.out.println(JSON.toJSONString(but2.getRoot(),true));
	}
}
