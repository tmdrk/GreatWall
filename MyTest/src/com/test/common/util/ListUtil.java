package com.test.common.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
	public static List<?> retainAllInt(List<?> list1, List<?> list2) {
		if (null == list1 && null == list2) {
			return null;
		} else if (null != list1 && null == list2) {
			return list1;
		} else if (null == list1 && null != list2) {
			return list2;
		} else if (null != list1 && null != list2) {
			list1.retainAll(list2);
			return list1;
		}
		return null;
	}
	
	public static List<?> newArrayList(Object... obj){
		List<Object> list = new ArrayList<>();
		for(Object o: obj){
			list.add(o);
		}
		return list;
	}
	
	public static void main(String[] args) {
		List<Integer> userIdList = new ArrayList<Integer>();
		userIdList.add(1);
		userIdList.add(2);
		List<Integer> userIdList1 = new ArrayList<Integer>();
		userIdList1.add(2);
		userIdList1.add(3);
		List<Integer> userIdList2 = new ArrayList<Integer>();
		List<Integer> userIdList3 = new ArrayList<Integer>();
		userIdList = (List<Integer>)retainAllInt(userIdList,userIdList1);
		System.out.println(userIdList);
	}
}
