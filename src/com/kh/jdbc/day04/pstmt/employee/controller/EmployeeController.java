package com.kh.jdbc.day04.pstmt.employee.controller;

//import java.sql.Connection;
import java.util.List;

//import com.kh.jdbc.day04.pstmt.employee.model.dao.EmployeeDAO;
import com.kh.jdbc.day04.pstmt.employee.model.service.EmployeeServie;
import com.kh.jdbc.day04.pstmt.employee.model.vo.Employee;

public class EmployeeController {
	
//	private EmployeeDAO eDao;
	private EmployeeServie eService;
	
	public EmployeeController() {
		eService = new EmployeeServie();
	}

	public List<Employee> printAllEmp() {
//		List<Employee> eList = eDao.selectList();
		List<Employee> eList = eService.selectList();
		return eList;
	}

	public int insertEmpoyee(Employee emp) {
//		int result = eDao.insertEmployee(emp);
		int result = eService.insertEmployee(emp);
		return result;
	}

	public int deleteEmployee(String empId) {
		int result = eService.deleteEmployee(empId);
		return result;
	}

	public Employee selectOneById(String empId) {
		Employee emp = eService.selectOne(empId);
		return emp;
	}
	
	public int updateEmployee(Employee emp) {
		int result = eService.updateEmployee(emp);
		return result;
	}

}
