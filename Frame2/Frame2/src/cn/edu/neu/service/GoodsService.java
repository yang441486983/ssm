package cn.edu.neu.service;


import java.util.List;
import java.util.Map;

import cn.edu.neu.core.common.Page;

import cn.edu.neu.model.Goods;


public interface GoodsService {


	List<Goods> getNewGoods();

	List<Goods> getSalesGoods();

	Page<Goods> getGoodsByCate(String cateId, String sort);

	Page<Goods> searchGoods(String keyword, String sort);

	Goods getGoodsDetailById(String goodsId);

	Goods getGoodsColorsById(String goodsId);

	Goods getGoodsSizesById(String goodsId);

	



	


	


}
