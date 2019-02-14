<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<%@ taglib prefix="chen" uri="/pager-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>OA办公管理系统-用户管理</title>
<%@ include file="taglib1.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
<!-- 导入bootStrap的库 -->
<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
<script type="text/javascript">
var showTip = function(tip){
	 $.messager.show({
			title:'添加用户',
			msg:"<span style='color:red;'>"+tip+"</span>",
			showType:'slide'
	});
	parent.refreshTree();
};

$(function(){
	
	// 如果有提示就弹出来 
	if("${tip}"){
		$.messager.show({
			title:'操作提示',
			msg:"<font color='red'>${tip}</font>",
			timeout:2000,
			showType:'slide'
		});
		 parent.refreshTree();
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
	
	/** 全选*/
	/**得到所有数据行的选项按钮*/
	var dataBoxs = $("input[name='box'][id^='box_']");
	$("#checkAll").click(function(){
		dataBoxs.attr("checked",this.checked);
		/** 全选如果被选中,则所有行的背景色被选中 ,反之*/
		dataTrs.trigger(this.checked?"mouseover":"mouseout");
	});
	
	/** 如果没有全部选中那么全选按钮也应该不选中*/
	var boxSize = dataBoxs.length;
	/** 给每个单选绑定点击事件*/
	dataBoxs.on("click",function(event){
		/** 取消单选事件的传播,单选点击完成以后,事件就结束了*/
		event.stopPropagation();
		/** 拿到当前选中的单选*/
		var checkedBoxs = dataBoxs.filter(":checked");
		$("#checkAll").attr("checked",checkedBoxs.length == boxSize);
	});
	
   /** 为所有数据行绑定点击事件*/
   dataTrs.click(function(){
		/** 得到当前所点击行的对应单选按钮对象 */
	    var trBoxId = this.id.replace("dataTr_","box_");
			var trBox = $("#"+trBoxId);
			trBox.trigger("click");
   });
   
   /** 删除用户*/
   $("#deleteModule").on("click",function(){
	   /** 获取所有选中的数据行的id 传输到后台删除数据*/
	   /** 拿到当前选中的单选*/
		 var checkedBoxs = dataBoxs.filter(":checked");
	   /** admin,liqin */
	   if(checkedBoxs.length > 0 ){
		   $.messager.confirm('用户提示', '您确认删除吗?', function(r){
				if (r){
					 /** 真正删除*/
					var maps = checkedBoxs.map(function(){
						 return this.value;
					});
					// alert(maps.get());
					window.location = "${ctx}/identity/module/deleteModules?ids="+maps.get()
							+"&pageIndex=${pageModel.pageIndex}&parentCode=${parentCode}";
				}
			});

	   }else{
		   $.messager.alert("用户提示","请选择您要删除的菜单!","error");
	   }
   })
	
   /** 添加模块信息 */
   $("#addModule").click(function(){
	   $("#divDialog").dialog({
			title : "添加模块", // 标题
			cls : "easyui-dialog", // class
			width : 450, // 宽度
			height : 280, // 高度
			maximizable : true, // 最大化
			minimizable : false, // 最小化
			collapsible : true, // 可伸缩
			modal : true, // 模态窗口
			onClose : function(){ // 关闭窗口
				window.location = "${ctx}/identity/module/getModulesByParent"
				+"?pageIndex=${pageModel.pageIndex}&parentCode=${parentCode}";
			}
	    });
	 /** 为此窗口的iframe触发界面请求 */
	 $("#iframe").attr("src","${ctx}/identity/module/showAddModule?parentCode=${parentCode}");
   })
});

function updateModule(code){
	  $("#divDialog").dialog({
			title : "修改模块", // 标题
			cls : "easyui-dialog", // class
			width : 450, // 宽度
			height : 280, // 高度
			maximizable : true, // 最大化
			minimizable : false, // 最小化
			collapsible : true, // 可伸缩
			modal : true, // 模态窗口
			onClose : function(){ // 关闭窗口
				window.location = "${ctx}/identity/module/getModulesByParent"
				+"?pageIndex=${pageModel.pageIndex}&parentCode=${parentCode}";
			}
	    });
	 /** 为此窗口的iframe触发界面请求 */
	 $("#iframe").attr("src","${ctx}/identity/module/showUpdateModule?code="+code);
}

</script>

</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
	<div>
	
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<div style="padding: 5px;">
				<a  id="addModule" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
				<a  id="deleteModule" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
			</div>
			
			<div class="panel-body">
				<table class="table table-bordered" style="float: right;">
					<thead>
						<tr style="font-size: 12px;" align="center">
							<th style="text-align: center;"><input type="checkbox" id="checkAll"/></th>
							<th style="text-align: center;">编号</th>
							<th style="text-align: center;">名称</th>
<!-- 							<th>备注</th> -->
							<th style="text-align: center;">链接</th>
							<th style="text-align: center;">操作</th>
<!-- 							<th style="text-align: center;">创建日期</th> -->
<!-- 							<th style="text-align: center;">创建人</th> -->
<!-- 							<th>修改日期</th> -->
							<th style="text-align: center;">修改人</th>
							<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					  <c:forEach items="${modules}" var="module" varStatus="stat">
				        <tr align="center" id="dataTr_${stat.index}">
							<td><input type="checkbox" name="box" id="box_${stat.index}" value="${module.code}"/></td>
							<td>${module.code}</td>
							<td>${module.name.replaceAll("-","")}</td>
<%-- 							<td>${module.remark}</td> --%>
							<td>${module.url}</td>
							<td><span class="label label-success"><a href="${ctx}/identity/module/getModulesByParent?parentCode=${module.code}" style="color: white;">查看下级</a></span></td>
<%-- 							<td><fmt:formatDate value="${module.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
<%-- 							<td>${module.creater.name }</td> --%>
<%-- 							<td><fmt:formatDate value="${module.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
							<td>${module.modifier.name }</td>
							<td><span class="label label-info"><a href="javascript:updateModule('${module.code}');" style="color: white;">修改</a></span></td>
						</tr>
				    </c:forEach>
				</table>
					<!-- 分页标签区 -->
				<chen:pager pageIndex="${pageModel.pageIndex}"
				  pageSize="${pageModel.pageSize}" 
				  recordCount="${pageModel.recordCount}" 
				  submitUrl="${ctx}/identity/module/getModulesByParent?pageIndex={0}&parentCode=${parentCode}"/>
			</div>
			
		</div>
	</div>
		<div id="divDialog" style="display: none;" >
			 <!-- 放置一个添加用户的界面  -->
			 <iframe id="iframe" frameborder="0" style="width: 100%;height: 100%;"></iframe>
		</div>
	
</body>
</html>