<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询商品列表</title>


</head>


<body> 
<h3>User: ${ sessionScope.user }<a href="${ pageContext.request.contextPath }/logout">登出</a></h3>

<form action="${pageContext.request.contextPath }/item/queryitem.action" method="post">
查询条件：
<table width="100%" border=1>
<tr>
<td><input type="submit" value="查询"/></td>
</tr>
</table>
</form>
商品列表：
<%-- <form action="${pageContext.request.contextPath}/item/delete" method="post">
 --%>
 <form action="${pageContext.request.contextPath}/item/updates" method="post">
 
 <table width="100%" border=1>
<tr>
	<td><input type="checkbox" name="ids" value=""/></td>
	<td>商品名称</td>
	<td>商品价格</td>
	<td>生产日期</td>
	<td>商品描述</td>
	<td>操作</td>
</tr>
<c:forEach items="${itemList }" var="item" varStatus="s">
<tr>
	<td><input type="checkbox" name="ids" value="${item.id}"/></td>
	<td><input type="text" name="itemsList[${ s.index }].name" value="${item.name }" /></td>
	<td><input type="text" name="itemsList[${ s.index }].price" value="${item.price }" /></td>
	<td><input type="text" name="itemsList[${ s.index }].createtime" value="${item.createtime }" /><%-- <fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/> --%></td>
	<td><input type="text" name="itemsList[${ s.index }].detail" value="${item.detail }" /></td>
	
	<td><a href="${pageContext.request.contextPath }/item/toEdit.action?id=${item.id}">修改</a></td>

</tr>
</c:forEach>

</table>
<input type="submit" value="删除">
<input type="submit" value="修改">
</form>
<div>
	<p>Json Request Result: </p>
	<div id="json"></div>
</div>
</body>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript">
var d = '{"id":5, "name":"cup", "detail":"color：deep blue.", "price":50.0 }';

$(function(){
	$.ajax({
		url : "${pageContext.request.contextPath}/item/json",
		type : 'post',
		contentType: 'application/json;charset=utf-8',
		data : d,
		dataType : 'json',
		success: function(data){
			var str = '{"id":'+data.id+', "name":"'+data.name+'", "detail":"'+data.detail+'", "price":'+data.price+' }';
			//alert(str);
			$('#json').text(str);
		},
		error:function(data){
			//alert('json error');
			$('#json').text('json error');
		}
	})
});

</script>
</html>