package com.test.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ObjectSort {
	public static void main(String[] args){
		List<Student> list = new ArrayList<Student>();
		Student st = new Student(1,"1");
		Student st1 = new Student(12,"12");
		Student st2 = new Student(0,"0");
		Student st3 = new Student(2,"2");
		Student st4 = new Student(3,"3");
		list.add(st);
		list.add(st1);
		list.add(st2);
		list.add(st3);
		list.add(st4);
		sortValue(list);
		for(Student stu:list){
			System.out.println(stu);
		}
	}
	public static void sortValue(List<Student> list){
		Collections.sort(list, new Comparator<Student>(){
			@Override
			public int compare(Student o1, Student o2) {
				Integer value1 = Integer.parseInt(o1.getName());
		    	Integer value2 = Integer.parseInt(o2.getName());
		        return value1.compareTo(value2);
			}
		});
		Student st = list.get(0);
		list.remove(0);
		list.add(st);
	}
}
class MapValueComparator implements Comparator<Student>{
    @Override
    public int compare(Student s1, Student s2) {
    	Integer value1 = Integer.parseInt(s1.getName());
    	Integer value2 = Integer.parseInt(s2.getName());
        return value1.compareTo(value2);
    }
}
class Student{
	public Student(int id,String name){
		this.id = id;
		this.name = name;
	}
	
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
}
