package cn.edu.neu.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.edu.neu.core.common.Page;
import cn.edu.neu.model.Address;
import cn.edu.neu.model.Order;
import cn.edu.neu.model.OrderDetail;
import cn.edu.neu.service.AddressService;
import cn.edu.neu.service.OrderService;

@Controller
@SessionAttributes(types={String[].class})
@RequestMapping("/appOrder")
public class AppOrderAction extends BaseAction {

	@Autowired
	AddressService addressService;
	@Autowired
	OrderService orderService;
	@ResponseBody
	@RequestMapping("/buyGoods")
	public Map<String, Object> buyGoods(
			@RequestParam(required = false) String[] goodsId,
			@RequestParam(required = false) String[] goodsName,
			@RequestParam(required = false) String[] goodsPrice,
			@RequestParam(required = false) String[] goodsDiscount,
			@RequestParam(required = false) String[] goodsPostalfee,
			@RequestParam(required = false) String[] num,
			@RequestParam(required = false) String[] size,
			@RequestParam(required = false) String[] color,
			@RequestParam(required = false) String[] pic){
		Map<String,Object> m = new HashMap<String, Object>();
		
		m.put("goodsId", goodsId);
		m.put("goodsName", goodsName);
		m.put("goodsDiscount", goodsDiscount);
		//m.put("goodsPostalfee", goodsPostalfee);
		m.put("size", size);
		m.put("color", color);
		m.put("num", num);
		m.put("pic", pic);
		
		List<Address> addrs = addressService.getAddressByUserId(this.getLoginUserId());
		m.put("addrs", addrs);
		return m;
	}
	@ResponseBody
	@RequestMapping("/addOrder")
	public String addOrder(
			@RequestParam String address,
			@RequestParam String orderPostalfee,
			HttpSession session,
			Map<String,Order> m
			){
		   Order order = new Order();
		   order.setUserId(this.getLoginUserId());
		   order.setOrderAddress(address);
		   order.setOrderPostalfee(Float.parseFloat(orderPostalfee));
		   
		   List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		   for(int i = 0;i<((String[])session.getAttribute("goodsId")).length;i++){
			   OrderDetail orderDetail = new OrderDetail();
			   orderDetail.setGoodsId(Integer.parseInt(((String[]) session
					   .getAttribute("goodsId"))[i]));
			   
			   orderDetail.setOdetailName(((String[]) session
					   .getAttribute("goodsName"))[i]);
			   
			   orderDetail.setOdetailPrice(Float.parseFloat(((String[]) session
					   .getAttribute("goodsDiscount"))[i]));
			   
			   orderDetail.setOdetailSize(((String[]) session
					   .getAttribute("size"))[i]);
			   
			   orderDetail.setOdetailColor(((String[]) session
					   .getAttribute("color"))[i]);
			   
			   orderDetail.setOdetailNum(Integer.parseInt(((String[]) session
					   .getAttribute("num"))[i]));
			   
			   orderDetail.setOdetailPic(((String[]) session
					   .getAttribute("pic"))[i]);
			   
			   orderDetails.add(orderDetail);
			   System.out.println("orderAction输出："+orderDetails);
		   }try{
			   orderService.addOrder(order, orderDetails);
			   Order orderInfo = orderService.getOrderDetailById(String.valueOf(order.getOrderId()));
			   m.put("orderDetail", orderInfo);
			   return "提交成功";
		   }catch(Exception e){
			   e.printStackTrace();
			   this.addMessage("提交订单失败");
			   this.addRedirURL("返回", "@back");
			   return EXECUTE_RESULT;			   
		   }		
	}
	@ResponseBody
	@RequestMapping("/getMyOrders")
	public Map<String, Page<Order>> getMyOrders(@RequestParam (required = false)String status){
		Map<String,Page<Order>> m = new HashMap<>();
		System.out.println("OrderAction:" + status);
		Page<Order>orders = orderService.getMyOrders(this.getLoginUserId(), status);
		m.put("orders", orders);		
		return m;
		
	}
	@ResponseBody
	@RequestMapping("/getMyListOrders")
	public Map<String,List<Order>> getMyListOrders(@RequestParam (required = false)String status){
		Map<String,List<Order>> m = new HashMap<>();
		System.out.println("OrderAction:" + status);
		List<Order>orders = orderService.getMyListOrders(this.getLoginUserId(), status);
		m.put("orders", orders);		
		return m;
	}
}
