package com.kh.jdbc.day04.pstmt.employee.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.jdbc.day04.pstmt.employee.model.vo.Employee;

public class EmployeeDAO {
	
	private static Properties prop;
	private static final String FILE_NAME = "resources/query.properties";
	
	public EmployeeDAO() {
		try {
			prop = new Properties();
			Reader reader = new FileReader(FILE_NAME);
			prop.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int insertEmployee(Connection conn, Employee emp) throws SQLException {
			PreparedStatement pstmt = null;
			int result = 0;
			String query = prop.getProperty("insertEmployee");
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, emp.getEmpId());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getJobCode());
			pstmt.setString(5, emp.getSalLevel());
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
	//		conn.close();
			
			return result;
		}

	public List<Employee> selectList(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		List<Employee> eList = null;
		String query = prop.getProperty("selectList");	
		try {
//			conn = new JDBCTemplate().getConnection();
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			// rsetToEmployee
			eList = new ArrayList<Employee>();
			// result 값이 존재할 때까지
			while (rset.next()) {
				Employee emp = rsetToEmployee(rset);
				eList.add(emp);
			}
		} 
//		catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} 
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
			 rset.close();
			 stmt.close();
//			 conn.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
		}
		
		return eList;
	}

	public int deleteEmployee(Connection conn, String empId) throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteEmployee");
		pstmt = conn.prepareStatement(query); // 워크시트
		pstmt.setString(1, empId);
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
//		conn.close();
		
		return result;
	}
	
	public int updateEmployee(Connection conn, Employee emp) throws SQLException {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("updateEmployee");
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, emp.getEmail());
		pstmt.setString(2, emp.getPhone());
		pstmt.setString(3, emp.getDeptCode());
		pstmt.setInt(4, emp.getSalary());
		pstmt.setDouble(5, emp.getBonus());
		pstmt.setString(6, emp.getManagerId());
		pstmt.setString(7, emp.getEmpId());
		
		result = pstmt.executeUpdate();
		pstmt.close();
//		conn.close();
		return result;
	}

	public Employee selectOne(Connection conn, String empId) throws SQLException {
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			Employee emp = null;
			String query = prop.getProperty("selectOne");
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, empId);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				emp = rsetToEmployee(rset);
			}
			pstmt.close();
			rset.close();
	//		conn.close();
			return emp;
		}

	private Employee rsetToEmployee(ResultSet rset) throws SQLException {
		Employee emp = new Employee();
		emp.setEmpId(rset.getString("EMP_ID"));
		emp.setEmpName(rset.getString("EMP_NAME"));
		emp.setEmpNo(rset.getString("EMP_NO"));
		emp.setEmail(rset.getString("EMAIL"));
		emp.setPhone(rset.getString("PHONE"));
		emp.setDeptCode(rset.getString("DEPT_CODE"));
		emp.setJobCode(rset.getString("JOB_CODE"));
		emp.setSalLevel(rset.getString("SAL_LEVEL"));
		emp.setSalary(rset.getInt("SALARY")); // Employee vo에서 salary 필드 int -> getInt()
		emp.setBonus(rset.getDouble("BONUS")); // Employee vo에서 bonus 필드 double -> getDouble()
		emp.setManagerId(rset.getString("MANAGER_ID"));
		emp.setHireDate(rset.getDate("HIRE_DATE")); // Employee vo에서 hireDate 필드 Date -> getDate()
		emp.setEntDate(rset.getDate("ENT_DATE")); // Employee vo에서 entDate 필드 Date -> getDate()
		emp.setEntYn(rset.getString("ENT_YN"));
		return emp;
	}

}



