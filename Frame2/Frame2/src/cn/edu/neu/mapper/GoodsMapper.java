package cn.edu.neu.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.neu.core.common.Page;
import cn.edu.neu.model.Goods;


public interface GoodsMapper {

	

	List<Goods> findNewGoods();

	List<Goods> findSalesGoods();

	List<Goods> findAllGoodsByCateId(Page<Goods> page);

	List<Goods> findGoodsByKeyword(Page<Goods> page);

	Goods findGoodsDetailById(String goodsId);

	Goods findGoodsSizesById(String goodsId);

	Goods findGoodsColorsById(String goodsId);

	

	

	

}
