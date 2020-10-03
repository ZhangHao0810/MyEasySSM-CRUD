package com.zhanghao.crud.test;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhanghao.crud.bean.Department;
import com.zhanghao.crud.bean.Employee;
import com.zhanghao.crud.dao.DepartmentMapper;
import com.zhanghao.crud.dao.EmployeeMapper;

/**
 * 测试dao层的工作
 * @author ZhangHao
 *推荐Spring的项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 *1、导入SpringTest模块
 *2、@ContextConfiguration指定Spring配置文件的位置
 *3、直接autowired要使用的组件即可
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	DepartmentMapper departmentMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	SqlSession sqlSession;//生成批量的sqlSession  在applicationContext中配置.

	/**
	 * 测试DepartmentMapper
	 */
	@Test
	public void testCRUD() {
		
		/*	//1、创建SpringIOC容器
		ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
		//2、从容器中获取mapper
		DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);*/
			
//		System.out.println(departmentMapper);
		
		
//		//1、插入几个部门
//		departmentMapper.insertSelective(new Department(null,"9999999999"));
//		departmentMapper.insertSelective(new Department(null,"w器"));
		
		
		//2.生成员工数据 测试员工插入.
//		employeeMapper.insertSelective(new Employee(null,"wq","M","mmwq@91pron.com",1));
		
		//3.批量插入多个;
		
//	    用for循环来做的话, 效能很差.
//		for() {
//			employeeMapper.insertSelective(new Employee(null,"wq","M","mmwq@91pron.com",1));
//		}
		//批量的插入.
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 1000; i++) {
			String uid = UUID.randomUUID().toString().substring(0, 5)+i;
			mapper.insertSelective(new Employee(null,i+"-王棋","M",uid+":swq@91pron.com",1));
		}
		System.out.println("批量插入完成.");
	}
}
