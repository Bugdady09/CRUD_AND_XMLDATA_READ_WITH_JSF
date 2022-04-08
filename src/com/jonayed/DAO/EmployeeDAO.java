package com.jonayed.DAO;

import java.util.List;
import com.jonayed.model.Employee;



public interface EmployeeDAO {

	public List<Employee> getAllEmployees();
	public String addEmployee(Employee employee);
	public String deleteEmployee(String empid);
	public String gotoUpdateEmployee(String firstName, String lastName);
	public String update(Employee employee);
	public String searchEmployee(int id);
	public String gotoSearch();
	public String readXMlFileData() throws Exception;

}
