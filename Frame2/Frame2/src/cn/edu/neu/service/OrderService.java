package cn.edu.neu.service;

import java.util.List;

import cn.edu.neu.core.common.Page;
import cn.edu.neu.model.Order;
import cn.edu.neu.model.OrderDetail;

public interface OrderService {
	int addOrder(Order order,List<OrderDetail> orderDetails);

	Order getOrderDetailById(String orderId);

	Page<Order> getMyOrders(int loginUserId, String status);

	int getOrderStatusById(String orderId);

	void delOrder(String orderId);

	void changeOrderStatus(String orderId, int status);

	List<Order> getMyListOrders(int loginUserId, String status);



}
