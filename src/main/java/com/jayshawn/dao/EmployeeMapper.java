package com.jayshawn.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import com.jayshawn.model.Employee;

public interface EmployeeMapper {
	
	public Employee getEmpById(Integer id);
	
	public void addEmp(Employee employee);
	
	public void deleteEmpById(Employee employee);
	
	public void updateEmp(Employee employee);
	
	public void addAllEmp(List<Employee> employees);
	
	public List<Employee> getAllEmp();
	
	public Map<String,Object> getMapById(Integer id);
	
	@MapKey("id")
	public Map<Integer, Employee> getMap();
	
	// 查询Employee的同时查询出关联的Department字段
	public Employee getEmpDep(Integer id);
	
	// 分步查询，第一步先查出Employee，第二步根据查询得到的部门id查出Department
	public Employee getEmpByStep(Integer id);
	
	public List<Employee> getEmpByDepId(Integer id);
	
	// 只要对应的属性不为空，就把对应的属性作为条件加到SQL语句中
	public List<Employee> getEmpByConditionIf(Employee employee);
	
	public List<Employee> getEmpByTrim(Employee employee);
	
	public List<Employee> getEmpByChoose(Employee employee);
	
	// EmployeeMapper.java
	public void updateEmpBySet(Employee employee);
	
	public List<Employee> getEmpByForEach(List<Integer> ids);
	
	public void addEmpStatus(Employee employee);
	
	
}
