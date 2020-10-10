package com.zhanghao.crud.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	 * 检查用户名是否已经重复。
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkUser(@RequestParam("empName") String empName) {

		// 先判断用户名是否是合法的表达式;
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if (!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "后端格式校验：用户名必须是6-16位数字和字母的组合或者2-5位中文");
		}

		// 数据库用户名重复校验。
		boolean b = employeeService.checkUser(empName);
		if (b) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "后端校验：用户名数据库重复");
		}
	}

	/**
	 * 保存员工信息 1.支持JSR303校验 2.导入Hibernate-Validatator
	 * 
	 * 
	 * 
	 * POST 请求。
	 * 
	 * @return
	 */
	@RequestMapping(value = "/emps", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee, BindingResult result) {

		if (result.hasErrors()) {
			// 校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String, Object> map = new HashMap<>();

			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				System.out.println("错误的字段名：" + fieldError.getField());
				System.out.println("错误信息：" + fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		} else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}

	}

	/**
	 * ResponseBody想要能工作, 要导入Jackson依赖.
	 * 
	 * @param pn
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody // 返回Json数据.
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		
		// 引入分页插件.
		// 传入页码以及每页的大小.
		PageHelper.startPage(pn, 5);
		// starPage后面紧跟着的这个查询,就是一个分页查询. 只查当前页的信息。牛皮。
		List<Employee> empsEmployees = employeeService.getAll();

		Iterator it1 = empsEmployees.iterator();
		while (it1.hasNext()) {
			System.out.println(it1.next());
		}

		// 使用PageInfo包装查询之后的结果. 只需要将PageInfo交给页面即可.
		// PageInfo封装了详细的分页信息, 包括有查询出来的数据. , 传入连续显示的页码数.
		PageInfo page = new PageInfo(empsEmployees, 5);
		return Msg.success().add("PageInfo", page);
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
