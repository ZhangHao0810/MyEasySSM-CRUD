package com.zhanghao.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhanghao.crud.bean.Employee;
import com.zhanghao.crud.bean.EmployeeExample;
import com.zhanghao.crud.bean.EmployeeExample.Criteria;
import com.zhanghao.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeMapper employeeMapper;

	//查询所有员工
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

	/**
	 *  	员工保存。
	 * @param employee
	 */
	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	/**
	 * 	检验用户名是否可用
	 * @param empName
	 * @return true 可用， false 不可用。
	 */
	public boolean checkUser( String empName) {
		//新建一个Example对象。
		EmployeeExample example = new EmployeeExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEmpNameEqualTo(empName);
		
		long count = employeeMapper.countByExample(example);
		return count==0;
	}

	/**
	 * 	按照员工Id 查询员工。
	 * @param id
	 * @return
	 */
	public Employee getEmp(Integer id) {
		Employee selectByPrimaryKey = employeeMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}

	
	/**
	 * 	员工更新的方法
	 * @param employee
	 */
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

}
