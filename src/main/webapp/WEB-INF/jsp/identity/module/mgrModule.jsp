<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>办公管理系统-菜单管理</title>
	<%@ include file="taglib1.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
	<meta name="Keywords" content="keyword1,keyword2,keyword3" />
	<meta name="Description" content="网页信息的描述" />
	<link href="${ctx}/fkjava.ico" rel="shortcut icon" type="image/x-icon" />
	<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
	<script type="text/javascript" src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/resources/dtree/dtree.css"/>
	<script type="text/javascript" src="${ctx}/resources/dtree/dtree.js"></script>
	<script type="text/javascript">
     	$(function(){
    	 	refreshTree();
     	});
     function refreshTree(){
    	 /** 创建一个树对象  */
		 d = new dTree('d',"${ctx}/resources/dtree/");
	        //      自己的id 父节点id  节点名称   节点请求地址
		 d.add(-2,-1,'系统模块树');
		 d.add(0,-2,'全部',"javascript:refreshSonModules('${ctx}/identity/module/getModulesByParent')");
		 	
    	 /** 异步加载模块树 */
     	 $.ajax({
    		url : "${ctx}/identity/module/loadAllModuleTrees",
    		type : "post",
    		dataType : "json",
    		async : true ,
    		success : function(data){
    			  /** 
    			      -> [{id : , pid : , name },{id : , pid : , name },{id : , pid : , name },...]
    			  */
    			  $.each(data,function(){
//     				   d.add(id, pid, name, url, title, target, icon, iconOpen, open)
                       var url = "${ctx}/identity/module/getModulesByParent?parentCode="+this.id;
    				   d.add(this.id,this.pid,this.name.replace(/-/g,""),"javascript:refreshSonModules('"+url+"');",this.name);
    			  });
    			  $("#tree").html(d.toString());
    		},error : function(){
    			$.messager.alert("用户提示","加载部门失败了!","error");
    		}
    	 })
     }
     
     function refreshSonModules(url){
    	 $("#sonModules").attr("src",url);
     }

</script>
</head>
    <body class="easyui-layout" style="width:100%;height:100%;">
			<div id="tree" data-options="region:'west'" title="菜单模块树" style="width:20%;padding:10px">
				 <!-- 展示所有的模块树  -->
				
			</div>
			
			<div data-options="region:'center'" title="模块菜单"  >
			     <!-- 展示当前模块下的子模块  -->
			     <iframe src="${ctx}/identity/module/getModulesByParent" frameborder="0" id="sonModules"  width="100%" height="100%" ></iframe>
			</div>
	</body>
</html>
