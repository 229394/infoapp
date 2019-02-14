<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="chen" uri="/pager-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en"> 
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <title>工作台</title>
	<!-- 导入css样式 -->
	<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctx}/resources/bootstrap/style.css" />
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
	<script type="text/javascript">
	$(function(){
		//如果有提示就弹出来
		if("${tip}"){
			$.messager.alert('错误提示',"${tip}",'info');
		}
		//选中当前用户的问题
		$("#question").val("${user_session.question}");
		
		/**提交表单函数 */
		$("#btn_submit").click(function(){
			//对表单中所有字段做校验
			var name = $("#name");
			var text = $("#email");
			var tel = $("#tel");
			var phone = $("#phone");
			var qqNum = $("#qqNum");
			var answer = $("#answer");
			var msg = "";
			if($.trim(name.val()) == ""){
				msg += "姓名不能为空!";
				name.focus();
			}else if ($.trim(text.val()) == ""){
				msg += "邮箱不能为空!";
				text.focus();
			}else if (!/^\w+@\w{2,}.\w{2,}$/.test(text.val())){
				msg += "邮箱格式不正确!";
				text.focus();
			}else if ($.trim(tel.val()) == ""){
				msg += "电话号码不能为空!";
				tel.focus();
			}else if (!/^0\d{2,3}-?\d{7,8}$/.test(tel.val())){
				msg += "电话号码格式不正确!";
				tel.focus();
			}else if ($.trim(phone.val()) == ""){
				msg += "手机号码不能为空!";
				phone.focus();
			}else if (!/^1[3|5|8]\d{9}$/.test(phone.val())){
				msg += "手机号码格式不正确!";
				phone.focus();
			}else if ($.trim(qqNum.val()) == ""){
				msg += "QQ号码不能为空!";
				qqNum.focus();
			}else if (!/^\d{5,11}$/.test(qqNum.val())){
				msg += "QQ号码长度必须在5-11之间!";
				qqNum.focus();
			}else if ($.trim(answer.val()) == ""){
				msg += "密保问题不能为空!";
				answer.focus();
			}
			//直接提交
			if (msg != ""){
				$.messager.alert('错误提示',msg,'error');
			}else{
				$("#updateSelfForm").submit();
			}
		});
	});
	</script>
</head> 
<body >
	<div class="container">
	      <div class="row info-content">
		 	<form id="updateSelfForm" class="form-horizontal" method="post" action="${ctx}/identity/user/updateSelf"  style="margin-top: 20px;">
				<!-- 隐藏用户的id,修改用 -->
				<input type="hidden"  name="userId" value="${user_session.userId}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">登录名称</label>
					<div class="col-sm-3">
						<button class="btn" style="background: #11a9e2;color: #ffffff;" disabled="disabled" type="button">
						  ${user_session.userId} <span class="badge"></span>
						</button>				
					</div>
					<label class="col-sm-2 control-label">用户姓名</label>
					<div class="col-sm-3">
						<input type="text" id="name" name="name" value="${user_session.name}"  class="form-control" placeholder="请输入您的电子邮件">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">性别</label>
					<div class="col-sm-3">
						<select  class="btn btn-default" >
							<c:if test="${user_session.sex == 1 }">
								<option>男</option>
							</c:if>
								<c:if test="${user_session.sex == 2 }">
								<option>女</option>
							</c:if>
							
						</select>
					</div>
					<label class="col-sm-2 control-label">部门</label>
					<div class="col-sm-3">
						<select  class="btn btn-default" id="deptSelect" >
							 <option >${user_session.dept.name}</option>
						</select>
					</div>
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label">职位</label>
					<div class="col-sm-3">
						<select class="btn btn-default" id="jobSelect" >
								 <option>${user_session.job.name}</option>
						</select>	
					</div>
					<label class="col-sm-2 control-label">邮箱</label>
					<div class="col-sm-3">
						<input id="email" name="email"  type="text" value="${user_session.email}" class="form-control" placeholder="请输入您的电子邮件">
					</div>
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label">电话</label>
					<div class="col-sm-3">
						<input id="tel" name="tel" type="text" value="${user_session.tel}" class="form-control" placeholder="请输入您的电子邮件">
					</div>
					<label class="col-sm-2 control-label">手机</label>
					<div class="col-sm-3">
						<input id="phone" name="phone" type="text" value="${user_session.phone}" class="form-control" placeholder="请输入您的电子邮件">
					</div>
				</div>
					<div class="form-group">
					<label class="col-sm-2 control-label">问题</label>
					<div class="col-sm-3">
						<select name="question" class="btn btn-default"
						  id="question" >
							<option value="1">您的生日</option>
							<option value="2">您的出生地</option>
							<option value="3">您的名字</option>
						</select>
					</div>
					<label class="col-sm-2 control-label">答案</label>
					<div class="col-sm-3">
						<input id="answer" name="answer"  value="${user_session.answer}" type="text" class="form-control" placeholder="请输入您的电子邮件">
					</div>
				</div>	
				
					<div class="form-group">
					<label class="col-sm-2 control-label">QQ号码</label>
					<div class="col-sm-3">
						<input id="qqNum" name="qqNum" value="${user_session.qqNum }" type="text" class="form-control" placeholder="请输入您的电子邮件">
					</div>
					
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"></label>
					<div class="col-sm-3">
						 <button type="button" id="btn_submit"  class="btn btn-info"><span class="glyphicon glyphicon-edit">提交修改</span></button>
					</div>
				</div>
		 	 </form>
		    </div>
		</div>
 
</body> 
</html>
