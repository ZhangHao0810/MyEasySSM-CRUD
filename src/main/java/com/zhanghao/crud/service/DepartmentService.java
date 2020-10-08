package com.zhanghao.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghao.crud.bean.Department;
import com.zhanghao.crud.dao.DepartmentMapper;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;

	public List<Department> getDepts() {
		
		//查出所有部门信息。
		List<Department> lsitDepartments = departmentMapper.selectByExample(null);

		return lsitDepartments;
	}

}
