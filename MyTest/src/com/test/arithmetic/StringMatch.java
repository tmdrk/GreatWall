package com.test.arithmetic;

/**
 * 子串定位运算又称为模式匹配(Pattern Matching)或串匹配(String Matching)
 * 1 Brute Force算法
 * 2 KMP算法
 * @ClassName: StringMatch 
 * @author zhoujie
 * @date 2018年1月3日 下午2:32:43
 */
public class StringMatch {
	public static void main(String[] args) {
//		char[] str = {'a','b','c','d','e','f','a','a','b','c','d','e','f','g','h'};
//		char[] subStr = {'a','b','c','d','e','f','g'};
		String str = "eabchfabcheaaabcdefgh";
		String subStr = "abchfabcdef";
//		printArray("",getNext(subStr));
		System.out.println(index(str.toCharArray(),subStr.toCharArray()));
		System.out.println(str.indexOf(subStr));
		kmpIndex(str,subStr);
		System.out.println("mast");
	}
	
	public static int index(char[] str,char[] subStr){
		int i = 0; 
		int j = 0;
		int sL = str.length;
		int subL = subStr.length;
		while(i<sL && j<subL){
			if(str[i]==subStr[j]){
				i++;
				j++;
			}else{
				i=i-j+1;
				j=0;
			}
			if(j==subL){
				return i-subL;
			}
		}
		return -1;
	}
	
	public static void kmpIndex(String original, String find) {  
		int next[] = new int[find.length()+1];
		initNext(find,next);
	    int j = 0;  
	    for (int i = 0; i < original.length(); i++) {  
	        while (j > 0 && original.charAt(i) != find.charAt(j))  
	            j = next[j];  
	        if (original.charAt(i) == find.charAt(j))  
	            j++;  
	        if (j == find.length()) {  
	            System.out.println("find at position " + (i - j));  
	            System.out.println(original.subSequence(i - j + 1, i + 1));  
	            j = next[j];  
	        }  
	    }  
	}  
	
	public static void initNext(String b,int next[]){  
		int len=b.length();  
		int j=0;  
		next[0]=next[1]=0;  
		for(int i=1;i<len;i++)//i表示字符串的下标，从0开始  
		{//j在每次循环开始都表示next[i]的值，同时也表示需要比较的下一个位置  
			while(j>0&&b.charAt(i)!=b.charAt(j)){
				j=next[j];  
			}
			if(b.charAt(i)==b.charAt(j)){
				j++;  
			}
			next[i+1]=j;  
		}  
		
	}  
	
	public static void printArray(String pre,int[] a){
		System.out.print(pre+":");
		for(int i:a){
			System.out.print(i+",");
		}
		System.out.println();
	}
}
