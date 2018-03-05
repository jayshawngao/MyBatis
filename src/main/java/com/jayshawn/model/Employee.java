package com.jayshawn.model;

import java.io.Serializable;

// Employee.java
public class Employee implements Serializable {
	
	// 用于测试mybatis对枚举类型的处理
	public enum EmpStatus{
		A,B,C
	}
	private EmpStatus empStatus;
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Department department;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public EmpStatus getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(EmpStatus empStatus) {
		this.empStatus = empStatus;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", empStatus=" + empStatus
				+ "]";
	}

	
	

	
	

	
	
}
