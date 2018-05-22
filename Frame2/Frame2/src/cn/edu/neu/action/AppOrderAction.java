package cn.edu.neu.action;

import java.util.ArrayList;
import java.util.Arrays;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
			@RequestParam String goodsId,
			@RequestParam String goodsName,
			@RequestParam String goodsDiscount,
			@RequestParam String size,
			@RequestParam String color,
			@RequestParam String num,
			@RequestParam String pic,
			HttpSession session,
			Map<String,Order> m
			){
		   Order order = new Order();
		   order.setUserId(this.getLoginUserId());
		   order.setOrderAddress(address);
		   order.setOrderPostalfee(Float.parseFloat(orderPostalfee));
		   String id1 = goodsId.replace("[", "");
		   String id2 = id1.replace("]", "");
		   String id3 = id2.replace("\"", "");
		   String[] id4 = id3.split(",");
		   String name1 = goodsName.replace("[", "");
		   String name2 = name1.replace("]", "");
		   String name3 = name2.replace("\"", "");
		   String[] name4 = name3.split(",");
		   String price1 = goodsDiscount.replace("[", "");
		   String price2 = price1.replace("]", "");
		   String price3 = price2.replace("\"", "");
		   String[] price4 = price3.split(",");
		   String size1 = size.replace("[", "");
		   String size2 = size1.replace("]", "");
		   String size3 = size2.replace("\"", "");
		   String[] size4 = size3.split(",");
		   String color1 = color.replace("[", "");
		   String color2 = color1.replace("]", "");
		   String color3 = color2.replace("\"", "");
		   String[] color4 = color3.split(",");
		   String num1 = num.replace("[", "");
		   String num2 = num1.replace("]", "");
		   String num3 = num2.replace("\"", "");
		   String[] num4 = num3.split(",");
		   String pic1 = pic.replace("[", "");
		   String pic2 = pic1.replace("]", "");
		   String pic3 = pic2.replace("\"", "");
		   String[] pic4 = pic3.split(",");
		   List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		   for(int i = 0;i<id4.length;i++){
			   System.out.println(id4[i]);
			   OrderDetail orderDetail = new OrderDetail();
			   orderDetail.setGoodsId(Integer.parseInt(id4[i]));
			   
			   orderDetail.setOdetailName(name4[i]);
			   
			   orderDetail.setOdetailPrice(Float.parseFloat(price4[i]));
			   
			   orderDetail.setOdetailSize(size4[i]);
			   
			   orderDetail.setOdetailColor(color4[i]);
			   
			   orderDetail.setOdetailNum(Integer.parseInt(num4[i]));
			   
			   orderDetail.setOdetailPic(pic4[i]);
			   
			   orderDetails.add(orderDetail);
		   }try{
			   int orderId = orderService.addOrder(order, orderDetails);
			   Order orderInfo = orderService.getOrderDetailById(String.valueOf(order.getOrderId()));
			   m.put("orderDetail", orderInfo);
			   System.out.println(orderId);
			   return String.valueOf(orderId);
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
	@ResponseBody
	@RequestMapping("/changeToAlreadyPaid")
	public void changeToAlreadyPaid(@RequestParam (required = false)String orderId){
		orderService.changeToAlreadyPaid(orderId);
	}
}
