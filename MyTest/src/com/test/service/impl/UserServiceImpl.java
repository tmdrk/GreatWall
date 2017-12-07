package com.test.service.impl;

import com.test.entity.UserDTO;
import com.test.entity.UserPO;
import com.test.entity.UserVO;
import com.test.service.UserService;

public class UserServiceImpl implements UserService{

	@Override
	public UserDTO findNameSex(UserVO vo) {
		UserDTO dto= new UserDTO();
		if(vo.getUserType1().contains("11")){
			dto.setUserType(vo.getUserType1()+" | 男");
			return dto;
		}
		return dto;
	}
	public static void main(String[] args) {
		UserPO userPO = new UserPO();
		userPO.setUserId(1);
		userPO.setUserName("sdkf");
		userPO.setType("2");
		System.out.println(userPO.getUserId()+"|"+userPO.getUserName()+userPO.getUserType());
	}
}
