package com.zhanghao.crud.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhanghao.crud.bean.Employee;
import com.zhanghao.crud.bean.Msg;
import com.zhanghao.crud.service.EmployeeService;

/**
 * 处理员工CRUD请求.
 * 		URI：
 * 		/emp/{id} GET请求，查询员工
 * 		/emp  POST请求，保存员工
 * 		/emp/{id} PUT请求， 修改员工
 * 		/emp/{id} DELETE请求， 删除员工。
 * 	这样只有请求不同的时候，走的controller不同，实现功能不同。这几个功能都是emp开头。
 * @author ZhangHao
 *
 */
@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	
	/**
	 * 	保存员工信息
	 *   POST 请求。
	 * 	 @return
	 */
	@RequestMapping(value="/emp",method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(Employee employee) {
		employeeService.saveEmp(employee);
		return Msg.success();
	}
	

	/**
	 * ResponseBody想要能工作, 要导入Jackson依赖.
	 * @param pn
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody // 返回Json数据.
	public Msg getEmpsWithJson(
			@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// 引入分页插件.
		// 传入页码以及每页的大小.
		PageHelper.startPage(pn, 5);
		// starPage后面紧跟着的这个查询,就是一个分页查询.
		List<Employee> empsEmployees = employeeService.getAll();
		// 使用PageInfo包装查询之后的结果. 只需要将PageInfo交给页面即可.
		// PageInfo封装了详细的分页信息, 包括有查询出来的数据. , 传入连续显示的页码数.
		PageInfo page = new PageInfo(empsEmployees, 5);
		return Msg.success().add("PageInfo",page);
	}

	// 查询员工数据(分页查询)
//	@RequestMapping("/emps")
//	public String getEmps(
//			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pn,
//			Model model) {
//
//		// 引入分页插件.
//		// 传入页码以及每页的大小.
//		PageHelper.startPage(pn, 5);
//		// starPage后面紧跟着的这个查询,就是一个分页查询.
//		List<Employee> empsEmployees = employeeService.getAll();
//		// 使用PageInfo包装查询之后的结果. 只需要将PageInfo交给页面即可.
//		// PageInfo封装了详细的分页信息, 包括有查询出来的数据. , 传入连续显示的页码数.
//		PageInfo page = new PageInfo(empsEmployees, 5);
//		model.addAttribute("pageInfo", page);
//		
//		return "list";
//	}

}
