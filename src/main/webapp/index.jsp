<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 引入这个标签库 就可以c: foreach了. -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<!-- 用Java给当前页面放一个属性,  request.getContextPath() 返回值 以斜线开始, 不以斜线结束.  -->
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>

<!--  用Json来返回数据给浏览器. -->
<!-- 
web路径：
不以/开始的相对路径，找资源，以当前资源的路径为基准，经常容易出问题。
以/开始的相对路径，找资源，是以服务器的路径为标准(http://localhost:3306)；需要加上项目名crud
		http://localhost:3306/crud
 -->
<script type="text/javascript"
	src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
<link
	href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>



<!-- 员工添加的模态框 -->
<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">员工添加</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
		  <div class="form-group">
		    <label class="col-sm-2 control-label">empName</label>
		    <div class="col-sm-10">
		      <input type="text" name="empName" class="form-control" id="empName_add_input" placeholder="empName">
		      <span class="help-block"></span>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">email</label>
		    <div class="col-sm-10">
		      <input type="text" name="email" class="form-control" id="email_add_input" placeholder="email@atguigu.com">
		      <span class="help-block"></span>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">gender</label>
		    <div class="col-sm-10">
		      <label class="radio-inline">
				  <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
				</label>
				<label class="radio-inline">
				  <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
				</label>
		    </div>
		  </div>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">deptName</label>
		    <div class="col-sm-4">
		    	<!-- 部门提交部门id即可  要和bean对应-->
		      <select class="form-control" name="dId">
		      </select>
		    </div>
		  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
      </div>
    </div>
  </div>
</div>


	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>MyEasySSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
				<button class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
				<thead>
						<tr>
							<th>
								<input type="checkbox" id="check_all"/>
							</th>
							<th>#</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>deptName</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
		</div>

		<!-- 显示分页信息 -->
		<div class="row">
			<!--分页文字信息  -->
			<div class="col-md-6" id="page_info_area"></div>
			<!-- 分页条信息 -->
			<div class="col-md-6"  id="page_nav_area"></div>
		</div>

	</div>

	<script type="text/javascript">
		//1.页面加载之后, 发送一个ajax请求 要到分页数据.
		$(function() {
			//页面默认是首页。
			to_page(1);
		});
		
		
		function to_page(pn){
			$.ajax({
				url : "${APP_PATH}/emps",
				data : "pn="+pn,
				type : "GET",
				success : function(result) {
					//console.log(result); 
					//1.解析并显示员工数据
					build_emps_table(result);
					//2.解析并显示分页信息.
					build_page_info(result);
					//3 解析显示分页条
					build_poge_nav(result);
				}
			});
		}

		function build_emps_table(result) {
			//清空table表格。（不然每次执行jQuery 就会将信息累加， 页面会不断边长。）
			$("#emps_table tbody").empty();
			var emps = result.extend.PageInfo.list;
			$.each(emps, function(index, item) {//每一个item就是一个list对象， 即表格中的一行数据。 
				//alert(item.empName);
				//构建tbody标签。
				var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>");
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var genderTd = $("<td></td>").append(
						item.gender == "M" ? "男" : "女");
				var emailTd = $("<td></td>").append(item.email);
				var deptNameTd = $("<td></td>")
						.append(item.department.deptName);

				var editBtn = $("<button></button>").addClass(
						"btn btn-primary btn-sm")
						.append($("<span></span>").addClass("glyphicon glyphicon-pencil"))
						.append(" 编辑");
				var delBtn = $("<button></button>").addClass(
				"btn btn-danger btn-sm")
				.append(
						$("<span></span>").addClass(
								"glyphicon glyphicon-trash")).append(
						" 删除");

				var btnTd =$("<td></td>").append(editBtn).append(delBtn);
				//append方法执行之后 还是返回原来的元素。
				$("<tr></tr>").append(checkBoxTd)
						.append(empIdTd)
						.append(empNameTd)
						.append(genderTd)
						.append(emailTd)
						.append(deptNameTd)
						.append(btnTd)
						.appendTo("#emps_table tbody");

			});
		}

		//解析显示分页信息
		function build_page_info(result){
			$("#page_info_area").empty();
			$("#page_info_area").append("当前 "+result.extend.PageInfo.pageNum +
					"页，总 "+ result.extend.PageInfo.pages+
					"页，总"+result.extend.PageInfo.total+ 
					"条记录")
			
		}
		//解析显示分页条, 点击分页要能出现动作。
		function build_poge_nav(result) {
			$("#page_nav_area").empty();
			var ul=$("<ul></ul>").addClass("pagination");
			
			var firstpageLi=$("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
			var prepageLi=$("<li></li>").append($("<a></a>").append("&laquo;"));
			if (result.extend.PageInfo.hasPreviousPage == false){
				firstpageLi.addClass("disabled");
				prepageLi.addClass("disabled");
			}else{
				//为元素添加点击翻页事件。
				firstpageLi.click(function(){
					to_page(1);
				});
				prepageLi.click(function(){
					to_page(result.extend.PageInfo.pageNum -1);
				});
			}
			
			
			
			var nextpageLi=$("<li></li>").append($("<a></a>").append("&raquo;"));
			var lastpageLi=$("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
			if (result.extend.PageInfo.hasNextPage == false){
				nextpageLi.addClass("disabled");
				lastpageLi.addClass("disabled");
			}else{
				//添加点击事件。
				nextpageLi.click(function(){
					to_page(result.extend.PageInfo.pageNum +1);
				});
				lastpageLi.click(function(){
					to_page(result.extend.PageInfo.pages);
				});
			}
			
			
			//添加首页和前一页。
			ul.append(firstpageLi).append(prepageLi);
			//遍历添加页码号 1,2,3,4,5
			$.each(result.extend.PageInfo.navigatepageNums,function(index,item){
				var numLi=$("<li></li>").append($("<a></a>").append(item));
				
				if (result.extend.PageInfo.pageNum == item){
					numLi.addClass("active");
				}
				numLi.click(function(){
					to_page(item);
				});
				ul.append(numLi);
			});
			//添加下一页和末页
			ul.append(nextpageLi).append(lastpageLi);
			
			//把ul加入到 nav元素中。 
			var navEle=$("<nav></nav>").append(ul);
			
			navEle.appendTo("#page_nav_area")
		}
		
		//点击新增按钮弹出模态框。
		$("#emp_add_modal_btn").click(function(){
/* 			//清除表单数据（表单完整重置（表单的数据，表单的样式））
			reset_form("#empAddModal form");
			//s$("")[0].reset();*/
			//发送ajax请求，查出部门信息，显示在下拉列表中  
			getDepts("#empAddModal select"); 
			//弹出模态框 
			$("#empAddModal").modal({
				backdrop:"static"
			});
		});
		
		//查出所有的部门信息并显示在下拉列表中
		function getDepts(ele){
			//清空之前下拉列表的值
			$(ele).empty();
			$.ajax({
				url:"${APP_PATH}/depts",
				type:"GET",
				success:function(result){
					//{"code":100,"msg":"处理成功！",
						//"extend":{"depts":[{"deptId":1,"deptName":"开发部"},{"deptId":2,"deptName":"测试部"}]}}
					//console.log(result);
					//显示部门信息在下拉列表中
					//$("#empAddModal select").append("")
					$.each(result.extend.depts,function(){
						var optionEle = $("<option></option>").append(this.deptName).attr("value",this.deptId);
						optionEle.appendTo(ele);
					});
				}
			});
			
		}
	</script>

</body>
</html>