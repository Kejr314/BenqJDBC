package com.kh.jdbc.day04.pstmt.employee.model.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kh.jdbc.day04.pstmt.common.JDBCTemplate;
import com.kh.jdbc.day04.pstmt.employee.model.dao.EmployeeDAO;
import com.kh.jdbc.day04.pstmt.employee.model.vo.Employee;

public class EmployeeServie {
	
//	private JDBCTemplate jdbcTemplate; // 연결 -> static 메소드를 만들었기 때문에 객체 생성할 필요 없음
	private EmployeeDAO eDao; // 포함 상속
	
	public EmployeeServie() { // 객체 생성
//		jdbcTemplate = new JDBCTemplate();
		eDao = new EmployeeDAO();
	}
	
	public List<Employee> selectList(){
		Connection conn = null;
		List<Employee> eList = null;
		try {
			conn = JDBCTemplate.getConnection();
			eList = eDao.selectList(conn);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		return eList;
	}
	
	public int insertEmployee(Employee emp) {
		Connection conn;
		int result = 0;
		try {
			conn = JDBCTemplate.getConnection();
			result = eDao.insertEmployee(conn, emp);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int deleteEmployee(String empId) {
		Connection conn = null;
		int result = 0;
		try {
			conn = JDBCTemplate.getConnection();
			result = eDao.deleteEmployee(conn, empId);
			
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Employee selectOne(String empId) {
		Connection conn = null;
		Employee emp = null;
				try {
					conn = JDBCTemplate.getConnection();
					emp = eDao.selectOne(conn, empId);
				} catch (ClassNotFoundException | SQLException | IOException e) {
					e.printStackTrace();
				}
		return emp;
	}
	
	public int updateEmployee(Employee emp) {
		Connection conn = null;
		int result = 0;
		try {
			conn = JDBCTemplate.getConnection();
			result = eDao.updateEmployee(conn, emp);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
