package com.jayshawn.dao;

import com.jayshawn.model.Department;

public interface DepartmentMapper {

	public Department getDepById(Integer id);
	
	public Department getDepEmp(Integer id);
	
	public Department getDepByStep(Integer id);
	
	public void updateDepById(Department department);
	
	
}
