<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="chen" uri="/pager-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>办公管理系统-添加用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta name="Keywords" content="keyword1,keyword2,keyword3" />
<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
<!-- 导入bootStrap的库 -->
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
</head>
<body style="background: #F5FAFA;">
	<center>
		<form id="updateUserForm" action="${ctx}/identity/user/updateUser" method="post">
			<table class="table-condensed" style="width: 80%;height: 80%;padding: 13px;">
				<tbody>
					<tr>
						<td><label>登陆名称:</label></td>
						<td><span class="label label-info">${user.userId }</span></td>
						<td><label>用户姓名:</label></td>
						<td><span class="label label-info">${user.name }</span></td>
						<td><label>性别</label></td>
						<td>
						   <span class="label label-info">
						      <c:if test="${user.sex == 1 }">男</c:if>
						      <c:if test="${user.sex == 2 }">女</c:if>
						   </span>
					   </td>
					</tr>
					<tr>
						<td><label>部门:</label></td>
						<td>
						<span class="label label-info"> ${user.dept.name }</span>
						</td>
						<td><label>职位:</label></td>
						<td>  <span class="label label-info"> ${user.job.name }</span>
						</td>
						<td><label>邮箱:</label></td>
						<td><span class="label label-info"> ${user.email}</span></td>
					</tr>

					<tr>
						<td><label>电话:</label></td>
						<td><span class="label label-info"> ${user.tel}</span>
						</td>
						<td><label>手机:</label></td>
						<td><span class="label label-info"> ${user.phone}</span>
						</td>
						<td><label>qq号码:</label></td>
						<td><span class="label label-info"> ${user.qqNum}</span>
						</td>
					</tr>

					<tr>
						<td><label>问题:</label></td>
						<td>
						<c:if test="${user.question == 1 }"><span class="label label-info">您的生日</span></c:if>
						<c:if test="${user.question == 2 }"><span class="label label-info">您的出生地</span></c:if>
						<c:if test="${user.question == 3 }"><span class="label label-info">您的名字</span></c:if>
						</td>
						<td><label>答案:</label></td>
						<td><span class="label label-info">${user.answer}</span></td>
					</tr>
					<tr>
						<td><label>创建人:</label></td>
						<td><span class="label label-info">${user.creater.name}</span></td>
					
					
						<td><label>创建日期:</label></td>
						<td><span class="label label-info">${user.createDate}</span></td>
					</tr>
					<tr>
						<td><label>修改人:</label></td>
						<td><span class="label label-info">${user.modifier.name}</span></td>
						<td><label>修改日期:</label></td>
						<td><span class="label label-info">${user.modifyDate}</span></td>
					</tr>
					<tr>
						<td><label>审核人:</label></td>
						<td><span class="label label-info">${user.checker.name}</span></td>
						<td><label>审核日期:</label></td>
						<td><span class="label label-info">${user.checkDate}</span></td>
					</tr>
				</tbody>
			</table>
		</form>
	</center>
</body>
</html>
