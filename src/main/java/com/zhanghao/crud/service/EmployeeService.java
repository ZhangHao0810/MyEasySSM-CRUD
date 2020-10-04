package com.zhanghao.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghao.crud.bean.Employee;
import com.zhanghao.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeMapper employeeMapper;

	//查询所有员工
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}

}
