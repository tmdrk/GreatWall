package com.test.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayListTest {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<20;i++){
			list.add(i);
		}
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()){
			Integer i = it.next();
			System.out.println(i);
			if(i==3){
				it.remove();
			}
		}
		it = list.iterator();
		while(it.hasNext()){
			Integer i = it.next();
			System.out.println(i);
		}
	}
}
