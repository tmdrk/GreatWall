package com.test;

public class Test {
	public static void main(String[] args) {
		Test t = new Test();
		User u = new User("aaaaaaaaaaaaaaaaahuanhuanhuanhuanbbbbbbcc",21);
		User c = new User("child",1);
		String name = "zhoujie";
		int aa = 123;
		u.setU(c);
		System.out.println(u+"|"+name+"|"+aa);
		t.make(u,name,aa);
//		User uu = u;
//		uu = new User("zhouzhou",23);
//		uu.setName("ssss");
//		String name1 = name;
//		name1 = "huihui";
		System.out.println(u+"|"+name+"|"+aa);
		t.make2(u.u);
		System.out.println(u+"|"+name+"|"+aa);
	}
	public void make(User uu,String name,int aa){
//		u = new User("zhouzhou",23);
		User c = new User("erzi",3);
		uu.u=c;
		uu.age = 34;
		uu.setName("时间");
		name = "胡来";
		aa = aa+1;
	}
	public void make2(User uuu){
		uuu.age=65;
	}
}
class User{
	String name;
	int age;
	User u;
	public User(String name,int age){
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

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", u=" + u + "]";
	}
	
}