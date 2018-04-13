package cn.edu.neu.service;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import cn.edu.neu.core.common.Page;

import cn.edu.neu.model.User;


public interface UserService {
	void save(User user);
	boolean update(User user);
	boolean delete(int id);
	User findById(int id);
	Page<User> findAll();
	User checkUser(User user);
	boolean regUser(User user);
	boolean checkUserName(String userName);
	User checkAdmin(User user);
	User findMyInfo(String userName);

	
}
