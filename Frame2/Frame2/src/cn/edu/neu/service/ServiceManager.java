package cn.edu.neu.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="servMgr")
public class ServiceManager {
	protected final Log log = LogFactory.getLog(getClass());

	/* 项目所用到的 Service */
	@Autowired
	private UserService userService;
	@Autowired
	private CateService cateService;
	@Autowired
	private GoodsService goodsService;
	
	

	/* Service 的存取方法 */
	public UserService getUserService() {
		return userService;
	}
	public CateService getCateService() {
		return cateService;
	}
	public GoodsService getGoodsService() {
		return goodsService;
	}
	
}
