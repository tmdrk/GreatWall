package com.test.generic;

import com.test.entity.User;

public class Apple<T extends User,E> extends Fruit<User>{
	@Override
	public User generate() {
		
		return null;
	}
}
