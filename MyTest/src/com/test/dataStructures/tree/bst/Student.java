package com.test.dataStructures.tree.bst;

public class Student implements Comparable{
	private String name;
	private int age;
	
	public Student(String name,int age){
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int compareTo(Object o) {
		if(this.name.compareTo(((Student)o).name)==1){
			return 1;
		}else if(this.name.compareTo(((Student)o).name)==-1){
			return -1;
		}else{
			return 0;
		}
	}
}
