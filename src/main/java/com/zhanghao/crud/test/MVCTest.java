package com.zhanghao.crud.test;

import java.util.List;

/**
 * 	使用Spring测试模块提供的 测试请求功能.  测试CRUD.
 * @author ZhangHao
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.mysql.cj.jdbc.SuspendableXAConnection;
import com.zhanghao.crud.bean.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // 有了这个注解, 就能把web ioc容器拿过来了,
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml" })
public class MVCTest {
	// 传入 SpringMVC的 ioc
	@Autowired // 自动装配, 只能装配ioc容器里面的, 想要把容器都装配, 需要类前面加上 @WebAppConfiguration 注解.
	WebApplicationContext context;
	// 虚拟 MVC请求, 获取处理结果.
	MockMvc mockMvc;

	@Before // 每次之前 都要初始化,
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	// =====================开始正式编写 模拟 MVC 的测试方法.
	@Test
	public void testPage() throws Exception {
		// andReturn()拿到返回值.
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.get("/emps").param("pageNumber", "5"))
				.andReturn();

		// 成功以后, 请求域中会有PageInfo, 我们可以取出PageInof进行验证.
		MockHttpServletRequest request = result.getRequest();
		PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
		System.out.println("当前页码:" + pageInfo.getPageNum());
		System.out.println("总页码: " + pageInfo.getPages());
		System.out.println("总记录数: " + pageInfo.getTotal());
		System.out.println("在页面需要连续显示的页码 :");
		int[] nums = pageInfo.getNavigatepageNums();
		for (int i : nums) {
			System.out.print(" " + i);
		}
		System.out.println();
		// 获取员工数据.

		List<Employee> list = pageInfo.getList();
		for (Employee employee : list) {
			System.out.println("ID:" + employee.getEmpId() + "  name: "
					+ employee.getEmpName());
		}
	}

}
