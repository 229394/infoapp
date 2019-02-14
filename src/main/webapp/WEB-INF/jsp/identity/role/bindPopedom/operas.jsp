<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-用户管理</title>
	<%@ include file="taglib4.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link rel="stylesheet" href="${ctx }/resources/bootstrap/css/bootstrap.min.css" />
	<script type="text/javascript" src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
	<!-- 导入bootStrap的库 -->
	<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
	<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
	<script type="text/javascript">

	$(function(){
	
	// 如果有提示就弹出来 
		if("${tip}"){
			$.messager.show({
				title:'操作提示',
				msg:"<font color='red'>${tip}</font>",
				timeout:2000,
				showType:'slide'
			});
		}

	/** 用户界面效果开发  */
	/** 得到所有数据行的jquery对象 */
	var dataTrs = $("tr[id^='dataTr_']");
	dataTrs.hover(function(){
		$(this).css({backgroundColor: "#eeecdd" , cursor : "pointer"});
	},function(){
		// 判断这一行的单选是否被选中了,如果被选中不要恢复成白色背景 
		// 得到当前行对应的单选的id 
		var trBoxId = this.id.replace("dataTr_","box_");
		var trBox = $("#"+trBoxId);
		if(!trBox.attr("checked")){
			$(this).css("backgroundColor","#ffffff");
		}
	}); 
	
	/** 全选  */
	/**得到所有数据行的选项按钮  */
	var dataBoxs = $("input[name='box'][id^='box_']");
	$("#checkAll").click(function(){
		dataBoxs.attr("checked",this.checked);
		/** 全选如果被选中,则所有行的背景色被选中 ,反之 */
		dataTrs.trigger(this.checked?"mouseover":"mouseout");
	});
	
	/** 如果没有全部选中那么全选按钮也应该不选中  */
	var boxSize = dataBoxs.length;
	/** 给每个单选绑定点击事件 */
	dataBoxs.on("click",function(event){
		/** 取消单选事件的传播,单选点击完成以后,事件就结束了 */
		event.stopPropagation();
		/** 拿到当前选中的单选 */
		var checkedBoxs = dataBoxs.filter(":checked");
		$("#checkAll").attr("checked",checkedBoxs.length == boxSize);
	});
	
   /** 为所有数据行绑定点击事件  */
   dataTrs.click(function(){
		/** 得到当前所点击行的对应单选按钮对象 */
	    var trBoxId = this.id.replace("dataTr_","box_");
			var trBox = $("#"+trBoxId);
			trBox.trigger("click");
   });
   
   $("#back").click(function(){
	   parent.window.location = "${ctx}/identity/role/selectRole";
   });
   /** 固定表的高度 */
   $("#table").css("height",window.innerHeight - window.innerHeight/4);

   // alert("${roleModuleOperasCodes}");
   dataBoxs.each(function(){
	   // 只要当前行的操作编号被包含在权限编号中就会被选中
	   if("${roleModuleOperasCodes}".indexOf($.trim(this.value))!=-1){
		    // 触发当前单选对应的行点击一下。
		    // 获取当前单选行对应的数据行的id 
		    var trId = this.id.replace("box_","dataTr_");
		    $("#"+trId).trigger("click").trigger("mouseover");
	   }
   })
   
   /** 绑定操作 */
   $("#bindPopedom").click(function(){
	   /** 选中需要绑定的权限 */
	   var checkedBoxs = dataBoxs.filter(":checked");
	   var params = checkedBoxs.map(function(){
		   return this.value;
	   });
	   window.location = "${ctx}/identity/popedom/bindPopedom?codes="+params.get()+"&parentCode=${parentCode}&id=${role.id}&name=${role.name}&moduleName=${moduleName}";
   })
});


</script>

</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
	<div>
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<div style="padding: 5px;">
					<a  id="back" class="btn btn-primary"><span class="glyphicon glyphicon-hand-left"></span>&nbsp;返回</a>
				    <a  id="bindPopedom" class="btn btn-success"><span class="glyphicon glyphicon-copy"></span>&nbsp;绑定权限</a>
					<button type="button" disabled="disabled" class="btn btn-default">
  							<span  class="glyphicon glyphicon-user" aria-hidden="true"></span> <span style="font-style: italic;">当前角色:${role.name}</span>
				    </button>
			</div>
			
			<div class="panel-heading" style="background-color: #11a9e2;">
				<h3 class="panel-title"><label style="color: white;font-size: 15px;"><span class="glyphicon glyphicon-yen"></span>&nbsp;${moduleName.replaceAll('-','')}</label></h3>
			</div>
			
			<div class="panel-body" id="table" style="overflow:scroll;overflow-x:hidden;"  >
				<table class="table table-bordered" >
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;"><input type="checkbox" id="checkAll"/></th>
							<th style="text-align: center;">编号</th>
							<th style="text-align: center;">名称</th>
<!-- 							<th>备注</th> -->
							<th style="text-align: center;">链接</th>
							<th style="text-align: center;">创建日期</th>
							<th style="text-align: center;">创建人</th>
<!-- 							<th>修改日期</th> -->
							<th style="text-align: center;">修改人</th>
						</tr>
					</thead>
					  <c:forEach items="${modules}" var="module" varStatus="stat">
				        <tr align="center" id="dataTr_${stat.index}">
							<td><input type="checkbox" name="box" id="box_${stat.index}" value="${module.code}"/></td>
							<td>${module.code}</td>
							<td>${module.name.replaceAll("-","")}</td>
<%-- 							<td>${module.remark}</td> --%>
							<td>${module.url}</td>
							<td><fmt:formatDate value="${module.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${module.creater.name }</td>
<%-- 							<td><fmt:formatDate value="${module.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
							<td>${module.modifier.name }</td>
						</tr>
				    </c:forEach>
				</table>
			</div>
		</div>
	</div>
		<div id="divDialog" style="display: none;" >
			 <!-- 放置一个添加用户的界面  -->
			 <iframe id="iframe" frameborder="0" style="width: 100%;height: 100%;"></iframe>
		</div>
	
</body>
</html>