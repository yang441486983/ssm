<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单详情</title>
</head>
<body>
<%@include file="../common/userTopNav.jsp" %>

<script>
function handleOrder(orderId,status,msg,e){
	if(confirm(msg)){
		var os=$("#orderStatus");
		
		$.post(getContextPath()+"/order/handleOrderStatus",{orderId:orderId,status:status},function(result){
			if(checkLogin(result)){
				if(result.handle=="success"){
					if(status==2){
						os.html("已提交退款申请，请等待商家处理");
					}
					else if(status==5){
						os.html("交易成功");
					}
					else if(status==6){
						os.html("已提交退货申请，请等待商家处理");
					}
					else if(status==9){
						os.html("交易关闭");
					}
					$(e.target).attr("disabled","disabled");
					$(e.target).siblings("[id!=back]").attr("disabled","disabled");
					$("#msgTitle").html("操作成功");
					$("#msgBody").html("订单操作成功");
					$("#msgModal").modal();	
				}else if(result.handle=="failure"){
					$("#msgTitle").html("操作失败");
					$("#msgBody").html("抱歉，目前的订单状态无法进行此操作");
					$("#msgModal").modal();
				}
				else{
					$("#msgTitle").html("操作失败");
					$("#msgBody").html("操作订单失败");
					$("#msgModal").modal();
				}
			}
		});
	}
}
</script>

<div class="container-fluid">
	<div class="row">
	
		<div class="col-md-10 col-md-offset-1">
		<h3>订单信息</h3>
		<table class="table table-condensed table-hover">
			<tbody>
				<tr>
					<td>订单编号：${orderDetail.orderCode }</td>
					<td>创建时间：${orderDetail.orderDate }</td>
				</tr>
				<tr>
					<td>
						订单状态：<span  id="orderStatus">
					<c:if test="${orderDetail.orderStatus==0}">
						等待付款
					</c:if>
					<c:if test="${orderDetail.orderStatus==1}">
						未发货
					</c:if>
					<c:if test="${orderDetail.orderStatus==2}">
						已提交退款申请，请等待商家处理
					</c:if>
					<c:if test="${orderDetail.orderStatus==3}">
						退款成功
					</c:if>
					<c:if test="${orderDetail.orderStatus==4}">
						已发货
					</c:if>
					<c:if test="${orderDetail.orderStatus==5}">
						交易成功
					</c:if>
					<c:if test="${orderDetail.orderStatus==6}">
						已提交退货申请，请等待商家处理
					</c:if>
					<c:if test="${orderDetail.orderStatus==7}">退货中</c:if>
					<c:if test="${orderDetail.orderStatus==8}">退货成功</c:if>
					<c:if test="${orderDetail.orderStatus==9}">
						交易关闭
					</c:if>
					</span>
					</td>
					<td>送货地址：${orderDetail.orderAddress }</td>
				</tr>
				
				<tr>
					 <td>运单编号：${orderDetail.orderPostcode }</td>
					 <td>快递名称：${orderDetail.orderPostname }</td>			 
				</tr>
				
			</tbody>
			</table>
		
		</div>
		<div class="col-md-10 col-md-offset-1">
			<div class="panel panel-info">
			<div class="panel-heading">商品列表</div>
			<div class="panel-body">	
			<table class="table table-hover table-condensed">
				<thead>
					<tr>
						<th>#</th><th>商品图片</th><th>商品名称</th><th>商品单价</th><th>数量</th><th>小计</th>
					</tr>
				</thead>
				<tbody>
					
					
					
				<c:forEach items="${orderDetail.odetails}" var="od" varStatus="vs">
					<tr >
						<td>
							${vs.index+1 }
						</td>
						<td>
							<img src="${pageContext.request.contextPath}${od.odetailPic}" width="30" height="30">
						</td>
						<td>
							${od.odetailName}&nbsp;&nbsp;
							${od.odetailSize }&nbsp;&nbsp;${od.odetailColor }
						</td>
						<td>
							单价<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>${od.odetailPrice }
						</td>
						<td>
							${od.odetailNum }
						</td>
						<td>
							<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>
							${od.odetailPrice*od.odetailNum }
						</td>
						
					</tr>
					</c:forEach>
				</tbody>
			</table>
			</div></div>
			<c:forEach items="${orderDetail.odetails}" var="od" varStatus="vs">
			<table class="table table-condensed">
				<tr><td class="text-right">运费：<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>${orderDetail.orderPostalfee }</td></tr>
				<tr><td class="text-right">订单总金额（含运费）：<span class="glyphicon glyphicon-yen" aria-hidden="true"></span>${od.odetailPrice+orderDetail.orderPostalfee}</td></tr>
			</table>
			</c:forEach>
			<div class="col-md-12 text-right">
			<span>
					<c:if test="${order.orderStatus==0}">
							<button class="btn btn-primary" type="button" onclick="handleOrder(${order.orderId},9,'确定要取消这个订单吗？',event);">取消订单</button>
							<a class="btn btn-primary" type="button" href="${pageContext.request.contextPath}/order/Pay?orderId=${order.orderId}">去付款</a>
						
					</c:if>
					<c:if test="${order.orderStatus==1}">
						<button class="btn btn-primary" type="button" onclick="handleOrder(${order.orderId},2,'确定要申请退款吗？',event)">申请退款</button>
					</c:if>
					
					<c:if test="${order.orderStatus==4}">
							<button class="btn btn-primary" type="button" onclick="handleOrder(${order.orderId},5,'确定要收货吗？',event)">确认收货</button> | 
							<button class="btn btn-primary" type="button" onclick="handleOrder(${order.orderId},6,'确定要申请退货吗？',event)">申请退货</button>
						
					</c:if>
					<c:if test="${order.orderStatus==5}">
							<a class="btn btn-primary" type="button" href="${pageContext.request.contextPath}/order/delOrder?orderId=${order.orderId}" onclick="return confirm('确定要删除这个订单吗？')">删除订单</a> 
						
					</c:if>
					<c:if test="${order.orderStatus==9}">
							<a class="btn btn-primary" type="button"href="${pageContext.request.contextPath}/order/delOrder?orderId=${order.orderId}" onclick="return confirm('确定要删除这个订单吗？')">删除订单</a> 
						
					</c:if>
					</span>
			
			
			<button class="btn btn-primary" id="back" type="button" onclick="javascript:location.href='${pageContext.request.contextPath}/order/getMyOrders'">返回订单列表</button>
			</div>			 
		</div>
	
	</div>
</div>
<%@include file="../common/userFooter.jsp" %>
</body>
</html>