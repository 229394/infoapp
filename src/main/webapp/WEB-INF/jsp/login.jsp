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
    <title>智能办公软件</title>
    <link href="${ctx}/css/base.css" type="text/css" rel="stylesheet">
    <link href="${ctx}/css/login.css" type="text/css" rel="stylesheet">
    <link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
     <script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
     <script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
     <link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css" type="text/css">
	 <script type="text/javascript">
		 $(function(){
			 // 等文档加载完成以后再执行本脚本 
		 	 // 给验证码绑定点击事件 
		 	 // vimg 
		 	 $("#vimg").click(function(){
		 		 $(this).attr("src","${ctx}/createCode?timer="+new Date().getTime());
		 	 }).mouseover(function(){
		 		  $(this).css("cursor","pointer");
		 	 });
			 
			 /** 回车键事件  
			   event:事件源,代表按下的那个按键
			 */
			 $(document).keydown(function(event){
				 if(event.keyCode == 13){
					 $("#login_id").trigger("click");
				 }
			 });
			 
			 /** 1.异步登录功能  */
			 $("#login_id").bind("click",function(){
				 var userId = $("#userId").val();
				 var password = $("#password").val();
				 var vcode = $("#vcode").val();
				 
				 // 定义一个校验结果 
				 var msg = "";
				 if(!/^\w{2,20}$/.test(userId.trim())){
					 msg = "登录名必须是2-20个的字符!";
				 }else if(!/^\w{6,20}$/.test(password)){
					 msg = "密码必须是6-20个的字符!";
				 }else if(!/^\w{4}$/.test(vcode)){
					 msg = "验证码格式不正确!";
				 }
				 
				 if(msg!=""){
					 // 校验失败了 
					 $.messager.alert("登录提示","<span style='color:red;'>"+msg+"</span>","error");
					 return ; // 结束程序 
				 }
				
				 var params = $("#loginForm").serialize();
				
				 /** 发起异步请求登录 */
				 $.ajax({
					 url:"${ctx}/loginAjax",
					 type: "post",
					 dataType : "json",
					 data : params ,
					 async : true ,  //是异步还是异步中的同步
					 success : function(data){
						 if(data.state == 1){
							  /** 跳转到主界面 */
							  window.location = "${ctx}/oa/main";
						 }else{
							 $("#vimg").trigger("click");
							 $.messager.alert("登录提示","<span style='color:red;'>"+data.tip+"</span>","error");
						 }
					 },error : function(){
						 $.messager.alert("登录提示","<span style='color:red;'>您登陆失败了</span>","error");
					 }
				 })
			 })
		 })
	 
	 </script>
</head> 
<body>
	<div class="login-hd">
		<div class="leaf-bg"></div>
		<div class="right-bg"></div>
		<div class="hg-inner">
			<span class="logo"></span>
			<span class="split"></span>
			<h2><span class="sys-name text-center">智能办公平台</span></h2>
		</div>
	</div>
	<div class="login-bd">
		<div class="bd-inner">
			<div class="inner-wrap">
				<div class="lg-zone">
					<div class="lg-box">
						<div class="panel-heading" style="background-color: #11a9e2;">
							<h3 class="panel-title" style="color: #FFFFFF;font-style: italic;">用户登陆</h3>
						</div>
						<form id="loginForm">
						   <div class="form-horizontal" style="padding-top: 20px;padding-bottom: 30px; padding-left: 20px;">
								
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<input class="form-control" id="userId" name="userId" type="text" placeholder="账号/邮箱">
									</div>
								</div>
				
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<input  class="form-control"  id="password" name="password" type="password" placeholder="请输入密码">
									</div>
								</div>
				
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<div class="input-group">
										<input class="form-control" id="vcode" name="vcode" type="text" placeholder="验证码">
										<span class="input-group-addon" id="basic-addon2"><img class="check-code" id="vimg" alt="" src="${ctx}/createCode"></span>
										</div>
									</div>
								</div>
				
						</div>
							<div class="tips clearfix">
											<label><input type="checkbox" checked="checked">记住用户名</label>
											<a href="javascript:;" class="register">忘记密码?</a>
										</div>
							<div class="enter">
								<a id="login_id" class="purchaser" href="javascript:;">登录</a>
								<a href="javascript:;" class="supplier" onclick="javascript:window.location='main.html'">重置</a>
							</div>
						</form>
					</div>
				</div>
				<div class="lg-poster"></div>
			</div>
	</div>
	</div>
	<div class="login-ft">
		<div class="ft-inner">
			<div class="about-us">
				<a href="javascript:;">关于我们</a>
				<a href="javascript:;">法律声明</a>
				<a href="javascript:;">服务条款</a>
				<a href="javascript:;">联系方式</a>
			</div>
			<div class="address">
			地址:南京信息工程大学计算机与软件学院
			&nbsp;邮编:211800&nbsp;&nbsp;
			Copyright&nbsp;©&nbsp;2018&nbsp;-&nbsp;2019&nbsp;落叶知秋对版权进行支持</div>
			<div class="other-info">
			建议使用Firefox、Chrome浏览器,不建议使用IE浏览器!</div>
		</div>
	</div>
</body> 
</html>