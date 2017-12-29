package com.test.sort;

/**
 * 排序
 * @ClassName: BaseSort 
 * @author zhoujie
 * @date 2017年12月27日 下午5:03:42
 */
public abstract class BaseSort {
	public static void sort(int[] arr){}
	/**
	 * 打印
	 * @param pre
	 * @param a
	 * @author zhoujie
	 * @date 2017年12月26日 下午2:03:30
	 */
	public static void printArray(String pre,int[] a){
		System.out.print(pre+":");
		for(int i:a){
			System.out.print(i+",");
		}
		System.out.println();
	}
}
