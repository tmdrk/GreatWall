package com.test.sort;

import java.util.Random;

/**
 * 将数组看成左右两个数组，左边初始默认为第一个元素，右边初始n-1个，从右边逐个选取元素
 * （选取某一元素后，备份该元素为tem，该元素所在位置暂时不在有意义，只用于交换），与左边降序逐个比较，
 * 假如是升序排列，arr[j]>tem则arr[j+1]=arr[j]，内部循环退出时表明arr[j]小于tem，则arr[j+1] = tem就是需要插入的位置
 * 将数组看成两部分 3,|5,2,7,6
 * 第一次排序 3,5,|2,7,6
 * 第二次排序 2,3,5,|7,6
 * 第三次排序 2,3,5,7,|6
 * 第四次排序 2,3,5,6,7|
 * 稳定排序
 * @ClassName: InsertSort 
 * @author zhoujie
 * @date 2017年12月26日 下午5:12:41
 */
public class InsertSort extends BaseSort{
	public static void main(String[] args) {
		int[] numbers = {10,5,32,45,31,25,16,65,78,35,9,16,44,13,38};
		printArray("排序前",numbers);
		sort(numbers);
		printArray("排序后",numbers);
		
		int length = 100000;
		int[] tree = new int[length];
		Random r = new Random();
		for(int i=0;i<length;i++){
			tree[i]=r.nextInt(length);
		}
		long start1 = System.currentTimeMillis();
		InsertSort.sort(tree);
		long end1 = System.currentTimeMillis();
		System.out.println("插入排序耗时："+(end1-start1)+"毫秒");
	}
	public static void sort(int[] numbers){
        for (int i = 1; i < numbers.length; i++) {
            int currentNumber = numbers[i];
            int j = i - 1;
            while (j >= 0 && numbers[j] > currentNumber) {
                numbers[j + 1] = numbers[j];
                j--;
            }
            numbers[j + 1] = currentNumber;
        }
    }
}
