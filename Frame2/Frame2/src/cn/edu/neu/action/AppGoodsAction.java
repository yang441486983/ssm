package cn.edu.neu.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.neu.core.common.Page;
import cn.edu.neu.model.Goods;
import cn.edu.neu.service.GoodsService;

@Controller
@RequestMapping("/appGoods")
public class AppGoodsAction extends BaseAction {
	@Autowired
	private GoodsService goodsService;
	
	
	@ResponseBody
	@RequestMapping("/getNewGoods")
	public Map<String, List<Goods>> getNewGoods(){
		List<Goods> newGoods=goodsService.getNewGoods();
		Map<String,List<Goods>> m = new HashMap<>();
		m.put("newGoods", newGoods);
		return m;
	}
	@ResponseBody
	@RequestMapping("/getSalesGoods")
	public Map<String, List<Goods>> getSalesGoods(){
		List<Goods> salesGoods=goodsService.getSalesGoods();
		Map<String,List<Goods>> m = new HashMap<>();
		m.put("salesGoods", salesGoods);
		return m;
	}
	@ResponseBody
	@RequestMapping("/getGoodsDetailById")
	public Map<String, Goods> getGoodsDetailById(@RequestParam String goodsId){
		Goods goodsDetail = goodsService.getGoodsDetailById(goodsId);
		Goods goodsSizes = goodsService.getGoodsSizesById(goodsId);
		Goods goodsColors = goodsService.getGoodsColorsById(goodsId);
		Map<String,Goods> m = new HashMap<>(); 
		m.put("goodsDetail", goodsDetail);
		m.put("goodsSizes", goodsSizes);
		m.put("goodsColors", goodsColors);
		System.out.println(m);
		return m;
		
	}
	@ResponseBody
	@RequestMapping("/getGoodsByCate")
	public Map<String, Page<Goods>> getGoodsByCate(@RequestParam String cateId,@RequestParam(required = false)String sort){
		Map<String,Page<Goods>> m = new HashMap<>();
		Page<Goods> goods = goodsService.getGoodsByCate(cateId, sort);
		m.put("goods", goods);
		System.out.println(m);
		return m;
	}
	@ResponseBody
	@RequestMapping("/searchGoods")
	public Map<String, Page<Goods>> searchGoods(HttpServletRequest request,
			@RequestParam String keyword,
			@RequestParam(required =false)String sort){
		Map<String ,Page<Goods>> m = new HashMap<>();
		System.out.println(keyword);
		Page<Goods> searchGodds =goodsService.searchGoods(keyword,sort);
		m.put("searchGoods", searchGodds);		
		return m;
		
	}
}
