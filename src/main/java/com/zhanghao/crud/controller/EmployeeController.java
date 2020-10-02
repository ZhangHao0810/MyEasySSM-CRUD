package com.zhanghao.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhanghao.crud.bean.Employee;
import com.zhanghao.crud.service.EmployeeService;

/**
 * 处理员工CRUD请求.
 * 
 * @author ZhangHao
 *
 */
@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	// 查询员工数据(分页查询)
	@RequestMapping("/emps")
	public String getEmps(
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer pn,
			Model model) {

		// 引入分页插件.
		// 传入页码以及每页的大小.
		PageHelper.startPage(pn, 5);
		// starPage后面紧跟着的这个查询,就是一个分页查询.
		List<Employee> empsEmployees = employeeService.getAll();
		// 使用PageInfo包装查询之后的结果. 只需要将PageInfo交给页面即可.
		// PageInfo封装了详细的分页信息, 包括有查询出来的数据. , 传入连续显示的页码数.
		PageInfo page = new PageInfo(empsEmployees, 5);
		model.addAttribute("pageInfo", page);
		
		return "list";
	}
}
