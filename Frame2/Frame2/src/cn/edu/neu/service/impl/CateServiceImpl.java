package cn.edu.neu.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.neu.core.common.Page;

import cn.edu.neu.mapper.CateMapper;
import cn.edu.neu.model.Category;
import cn.edu.neu.model.User;
import cn.edu.neu.service.CateService;

@Service
@Transactional
public class CateServiceImpl implements CateService{
	@Autowired
	private CateMapper mapper;

	@Override
	public List<Category> getAllCates() {
		// TODO Auto-generated method stub
		List<Category> cates=mapper.findAll(null);
		return cates;
	}
	
	
}
