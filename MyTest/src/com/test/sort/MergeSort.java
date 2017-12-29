package com.test.sort;

/**
 * 归并排序
 * 稳定排序
 * @ClassName: MergeSort 
 * @author zhoujie
 * @date 2017年12月26日 下午5:29:39
 */
public class MergeSort extends BaseSort{
	public static void main(String[] args) {
//		int[] numbers = {10,5,32,45,31,25,16,65,78,35,9,16,44,13,38};
		int[] numbers = {10,5,32,45,31};
		printArray("排序前",numbers);
		sort(numbers);
		printArray("排序后",numbers);
	}
	public static void sort(int[] numbers){
		sort(numbers, 0, numbers.length);
	}
	
	public static void sort(int[] numbers,int pos,int end){
		if ((end - pos) > 1) {
            int offset = (end + pos) / 2;
            sort(numbers, pos, offset);
            sort(numbers, offset, end);
            merge(numbers, pos, offset, end);
        }
	}
	/**
	 * 合并排序后的两个数组
	 * @param numbers
	 * @param pos
	 * @param offset
	 * @param end
	 * @author zhoujie
	 * @date 2017年12月27日 下午4:19:54
	 */
	public static void merge(int[] numbers,int pos,int offset,int end){
        int[] array1 = new int[offset - pos];
        int[] array2 = new int[end - offset];
        System.arraycopy(numbers, pos, array1, 0, array1.length);
        System.arraycopy(numbers, offset, array2, 0, array2.length);
        for (int i = pos,j=0,k=0; i < end ; i++) {
        	//将两个数组按从小到大顺序逐次插入到原数组中
            if (j == array1.length) {//如果array1循环到尾部，则把array2剩余部分全部copy到numbers中
                System.arraycopy(array2, k, numbers, i, array2.length - k);
                break;
            }
            if (k == array2.length) {//如果array2循环到尾部，则把array1剩余部分全部copy到numbers中
                System.arraycopy(array1, j, numbers, i, array1.length - j);
                break;
            }
            if (array1[j] <= array2[k]) {
                numbers[i] = array1[j++];
            } else {
                numbers[i] = array2[k++];
            }
        }
    }
}
