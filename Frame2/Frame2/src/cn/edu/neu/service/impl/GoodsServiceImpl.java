package cn.edu.neu.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.neu.core.common.Page;

import cn.edu.neu.mapper.GoodsMapper;
import cn.edu.neu.model.Category;
import cn.edu.neu.model.Goods;

import cn.edu.neu.service.GoodsService;

@Service
@Transactional 
public class GoodsServiceImpl implements GoodsService{

	@Autowired
	private GoodsMapper mapper;
	
	
	@Override
	public List<Goods> getNewGoods() {
		// TODO Auto-generated method stub
		List<Goods> newGoods=mapper.findNewGoods();
		//System.out.println(newGoods);
		return newGoods;
	}

	@Override
	public List<Goods> getSalesGoods() {
		// TODO Auto-generated method stub
		List<Goods> salesGoods=mapper.findSalesGoods();
		//System.out.println(salesGoods);
		return salesGoods;
	}

	@Override
	public Page<Goods> getGoodsByCate(String cateId, String sort) {
		// TODO Auto-generated method stub
		Page<Goods> page= new Page<Goods>(12);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("cateId", cateId);
		m.put("sort", sort);
		page.setParams(m);
		List<Goods> goodsList = mapper.findAllGoodsByCateId(page);
		page.setList(goodsList);
		return page;
	}
	
	@Override
	public Page<Goods> searchGoods(String keyword, String sort) {
		// TODO Auto-generated method stub
		System.out.println("-------"+ sort);
		Page<Goods> page =new Page<Goods>(9);
		String[] keywords =keyword.split(" ");
		
		Map<String, Object> m = new HashMap<String ,Object>();
		m.put("keywords", keywords);
		m.put("sort", sort);
		page.setParams(m);
		List<Goods>goodsList = mapper.findGoodsByKeyword(page);
		System.out.println("----" + goodsList);
		page.setList(goodsList);		
		return page;
		
	}

	@Override
	public Goods getGoodsDetailById(String goodsId){
		Goods goodsDetail = mapper.findGoodsDetailById(goodsId);
		return goodsDetail;
	}
	@Override
	public Goods getGoodsSizesById(String goodsId){
		Goods goodsSizes = mapper.findGoodsSizesById(goodsId);
		return goodsSizes;
	}
	@Override
	public Goods getGoodsColorsById(String goodsId){
		Goods goodsColors = mapper.findGoodsColorsById(goodsId);
		return goodsColors;
	}

	@Override
	public List<Goods> getAllGoods() {
		List<Goods> goods = mapper.findAllGoods();
		// TODO Auto-generated method stub
		return goods;
	}

	@Override
	public void delGoods(String goodsId) {
		// TODO Auto-generated method stub
		mapper.delGoods(goodsId);
	}

	@Override
	public void updateGoodsInformation(Goods goods) {
		// TODO Auto-generated method stub
		mapper.updateGoodsInformation(goods);
	}

	@Override
	public void addGoods(Goods goods) {
		// TODO Auto-generated method stub
		mapper.addGoods(goods);
	}
	

	

}
