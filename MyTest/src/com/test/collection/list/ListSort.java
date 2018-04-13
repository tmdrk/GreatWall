package com.test.collection.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.test.entity.User;

/**
 * 
 * @ClassName: ListSort  
 * @Description: TODO 
 * @author zhoujie  
 * @date 2018年4月13日
 */
public class ListSort {
	public static void main(String[] args) {
		testStreamSort();
	}
	/**
	 * https://blog.csdn.net/aitangyong/article/details/54880228
	 * 如果任务太小或者运行程序的机器是单核的话，就用串行流，如果任务比较大且运行程序的机
	 * 器是多核，就可以考虑用并行流。
	 * @Title: testStreamSort 
	 * @Description: TODO  void
	 * @author zhoujie
	 * @date 2018年4月13日下午5:30:40
	 */
	public static void  testStreamSort(){
		Comparator<User> cmpUserId = Comparator.comparing(User::getUserId); 
		Comparator<User> cmpUserName = Comparator.comparing(User::getUserName,
				String.CASE_INSENSITIVE_ORDER).reversed(); 
		List<User> list = new ArrayList<User>();
		User u1 = new User(34,"eur","sdf","ihdfi");
		User u2 = new User(87,"ty","sdfs","ert");
		User u3 = new User(22,"yjty","sdfsd","erter");
		User u4 = new User(67,"sdff","th","rthrth");
		User u5 = new User(67,"sdfggg","th","rthrth");
		list.add(u1);
		list.add(u2);
		list.add(u3);
		list.add(u4);
		list.add(u5);
		list = list.stream().sorted(cmpUserId.reversed()).collect(Collectors.toList());
		for(User u:list){
			System.out.println(u.toString());
		}
	}
}
