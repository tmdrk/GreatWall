package com.test.service;

import com.test.entity.UserDTO;
import com.test.entity.UserVO;

public interface UserService {
	UserDTO findNameSex(UserVO vo);
}
