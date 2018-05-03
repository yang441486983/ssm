package cn.edu.neu.action;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.neu.core.common.Page;

import cn.edu.neu.model.Goods;
import cn.edu.neu.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsAction extends BaseAction{

	@Autowired
	private GoodsService goodsService;
	
	
	//@ResponseBody
	@RequestMapping("/getNewGoods")
	public String getNewGoods(Map<String,List<Goods>> m){
		List<Goods> newGoods=goodsService.getNewGoods();
		m.put("newGoods", newGoods);
		System.out.println(m);
		return "/goods/newGoods";
	}
	
	//@ResponseBody
	@RequestMapping("/getSalesGoods")
	public String getSalesGoods(Map<String,List<Goods>> m){
		List<Goods> salesGoods=goodsService.getSalesGoods();
		m.put("salesGoods", salesGoods);
		return "/goods/salesGoods";
	}
	@RequestMapping("/getGoodsByCate")
	public String getGoodsByCate(@RequestParam String cateId,@RequestParam(required = false)String sort,
			Map<String,Page<Goods>> m){
		Page<Goods> goods = goodsService.getGoodsByCate(cateId, sort);
		m.put("goods", goods);
		System.out.println(m);
		return "/goods/goodsList";
	}
	@RequestMapping("/searchGoods")
	public String searchGoods(HttpServletRequest request,
			@RequestParam String keyword,
			@RequestParam(required =false)String sort,
			Map<String ,Page<Goods>> m){
		System.out.println(keyword);
		Page<Goods> searchGodds =goodsService.searchGoods(keyword,sort);
		m.put("searchGoods", searchGodds);		
		return "/goods/searchGoods";
		
	}
	@RequestMapping("/getGoodsDetailById")
	public String getGoodsDetailById(@RequestParam String goodsId,Map<String,Goods> m){
		Goods goodsDetail = goodsService.getGoodsDetailById(goodsId);
		Goods goodsSizes = goodsService.getGoodsSizesById(goodsId);
		Goods goodsColors = goodsService.getGoodsColorsById(goodsId);
		m.put("goodsDetail", goodsDetail);
		m.put("goodsSizes", goodsSizes);
		m.put("goodsColors", goodsColors);
		System.out.println(m);
		return "goods/goodsDetail";
		
	}
	@RequestMapping("/manageGoods")
	public String manageGoods(HttpServletRequest request,Map<String,List<Goods>> m ){
		List<Goods> goods = goodsService.getAllGoods();
		m.put("goodsList", goods);
		request.setAttribute("goodsList", m);
		System.out.println(m);
		return "manage/showManageGoods";
	}
	@RequestMapping("/delGoods")
	public String manageGoods(@RequestParam String goodsId){
		System.out.println(goodsId);
		goodsService.delGoods(goodsId);
		return "redirect:/goods/manageGoods";
	}
	@RequestMapping("/updateGoodsInformation")
	public String updateGoodsInformation(int goodsId
			,int cateId,String goodsName,String goodsDisc,float goodsPrice,
			float goodsDiscount,int goodsStock,String goodsOrigin
			,String goodsMaterial
			,float goodsPostalfee
			,Date goodsDate
			,int goodsSales
			,String goodsPic
			,HttpServletRequest request){
			System.out.println("测试"+goodsId);
			Goods goods = new Goods();
			goods.setGoodsId(goodsId);
			goods.setCateId(cateId);
			goods.setGoodsName(goodsName);
			goods.setGoodsDisc(goodsDisc);
			goods.setGoodsPrice(goodsPrice);
			goods.setGoodsDiscount(goodsDiscount);
			goods.setGoodsStock(goodsStock);
			goods.setGoodsOrigin(goodsOrigin);
			goods.setGoodsMaterial(goodsMaterial);
			goods.setGoodsPostalfee(goodsPostalfee);
			goods.setGoodsDate(goodsDate);
			System.out.println(goodsDate);
			goods.setGoodsSales(goodsSales);
			//System.out.println(goodsSales);
			goods.setGoodsPic(goodsPic);
//			System.out.println(goods);
			goodsService.updateGoodsInformation(goods);
		
		return "redirect:/goods/manageGoods";
		
	}
	@RequestMapping("/toAddGoods")
	public String toAddGoods(){
		return "manage/addGoods";
	}
	@RequestMapping("/addGoods")
	public String addGoods(int cateId,String goodsName,String goodsDisc,float goodsPrice,
			float goodsDiscount,int goodsStock,String goodsOrigin
			,String goodsMaterial
			,float goodsPostalfee
			,int goodsSales
			,String goodsPic
			,HttpServletRequest request){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Goods goods = new Goods();
		goods.setCateId(cateId);
		goods.setGoodsName(goodsName);
		goods.setGoodsDisc(goodsDisc);
		goods.setGoodsPrice(goodsPrice);
		goods.setGoodsDiscount(goodsDiscount);
		goods.setGoodsStock(goodsStock);
		goods.setGoodsOrigin(goodsOrigin);
		goods.setGoodsMaterial(goodsMaterial);
		goods.setGoodsPostalfee(goodsPostalfee);
		goods.setGoodsDate(date);
		goods.setGoodsSales(goodsSales);
		goods.setGoodsPic(goodsPic);
		goodsService.addGoods(goods);
		
		return "redirect:/goods/manageGoods";
	}
}				

