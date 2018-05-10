package cn.edu.neu.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.edu.neu.core.Constants;
import cn.edu.neu.core.common.Page;
import cn.edu.neu.model.Address;
import cn.edu.neu.model.Order;
import cn.edu.neu.model.OrderDetail;
import cn.edu.neu.service.AddressService;
import cn.edu.neu.service.OrderService;

@Controller
//@SessionAttributes(types={String[].class})
@RequestMapping("/order")
public class OrderAction extends BaseAction {

	@Autowired
	AddressService addressService;
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/buyGoods")
	public String buyGoods(
			@RequestParam(required = false) String[] goodsId,
			@RequestParam(required = false) String[] goodsName,
			@RequestParam(required = false) String[] goodsPrice,
			@RequestParam(required = false) String[] goodsDiscount,
			@RequestParam(required = false) String[] goodsPostalfee,
			@RequestParam(required = false) String[] num,
			@RequestParam(required = false) String[] size,
			@RequestParam(required = false) String[] color,
			@RequestParam(required = false) String[] pic,
			Map<String, Object> m){
		
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
		return "/order/buyGoods";
	}
	
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
			   System.out.println("OrderAction添加订单"+m);
			   return "/order/orderDetail";
		   }catch(Exception e){
			   e.printStackTrace();
			   this.addMessage("提交订单失败");
			   this.addRedirURL("返回", "@back");
			   return EXECUTE_RESULT;			   
		   }		
	}
	@RequestMapping("/getOrderDetailById")
	public String getOrderDetailById(@RequestParam String orderId,Map<String,Order>m){
		Order orderDetail = orderService.getOrderDetailById(orderId);
		m.put("orderDetail", orderDetail);
		System.out.println("这是getOrderDetailById的输出"+m);
		return "/order/orderDetail";
	}
	
	@RequestMapping("/getMyOrders")
	public String getMyOrders(@RequestParam (required = false)String status,
			Map<String,Page<Order>> m){
		System.out.println("OrderAction:" + status);
		Page<Order>orders = orderService.getMyOrders(this.getLoginUserId(), status);
		m.put("orders", orders);		
		return "/order/orderList";
		
	}
	@RequestMapping("/showAllOrders")
	public String getAllOrders(HttpServletRequest request,HttpSession session,Map<String,List<Order>> m ){
		System.out.println("执行了showAllOrders");
		List<Order> orders = orderService.getAllOrders();
		m.put("orderList", orders);
		request.setAttribute("goodsList", m);
		System.out.println(m);
		return "/manage/allOrdersShow";
	}
	@RequestMapping("/adminDelOrder")
	public String adminDelOrder(@RequestParam String orderId){
		orderService.delOrder(orderId);
		return "redirect:/order/showAllOrders";
	}
	@RequestMapping("/updateOrder")
	public String updateOrder(int orderId,int orderStatus,String orderAddress,String orderPostcode,String orderPostname){
		Order order = new Order();
		order.setOrderId(orderId);
		order.setOrderStatus(orderStatus);
		order.setOrderAddress(orderAddress);
		order.setOrderPostcode(orderPostcode);
		order.setOrderPostname(orderPostname);
		orderService.updateOrder(order);
		return "redirect:/order/showAllOrders";
	}
	@RequestMapping("/delOrder")
	public String delOrder(@RequestParam String orderId){
		try{
			int status = orderService.getOrderStatusById(orderId);
			if(status == Constants.ORDER_SUCCEED||status == Constants.ORDER_CLOSED){
				orderService.delOrder(orderId);
				return "forward:"+getReferUrl();
				
			}else{
				this.addMessage("抱歉，目前的订单状态不允许删除订单");
				this.addRedirURL("返回", "@back");
				return EXECUTE_RESULT;
				
			}
		}catch (Exception e){
			e.printStackTrace();
			this.addMessage("删除订单失败");
			this.addRedirURL("返回","@back");
			return EXECUTE_RESULT;
		}		
	}
	
	@ResponseBody
	@RequestMapping("/handleOrderStatus")
	public Map<String, String> handleOrderStatus(@RequestParam String orderId,
			@RequestParam int status) {
		Map<String, String> m = new HashMap<String, String>();
		try {
			switch (status) {
			case 2:// 申请退款
				if (orderService.getOrderStatusById(orderId) == Constants.ORDER_PAID) {
					orderService.changeOrderStatus(orderId, status);
					m.put("handle", "success");
				} else {
					m.put("handle", "failure");
				}
				break;
			case 5:// 确认收货
				if (orderService.getOrderStatusById(orderId) == Constants.ORDER_SENDING) {
					orderService.changeOrderStatus(orderId, status);
					m.put("handle", "success");
				} else {
					m.put("handle", "failure");
				}
				break;
			case 6:// 申请退货
				if (orderService.getOrderStatusById(orderId) == Constants.ORDER_SENDING) {
					orderService.changeOrderStatus(orderId, status);
					m.put("handle", "success");
				} else {
					m.put("handle", "failure");
				}
				break;
			case 9:// 取消订单
				if (orderService.getOrderStatusById(orderId) == Constants.ORDER_WAITING) {
					orderService.changeOrderStatus(orderId, status);
					m.put("handle", "success");
				} else {
					m.put("handle", "failure");
				}
				break;
			default:
				m.put("handle", "failure");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			m.put("handle", "exception");
		}
		return m;
	}

}
