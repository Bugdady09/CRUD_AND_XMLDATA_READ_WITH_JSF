package com.jonayed.DAO;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jonayed.Connection.DBConnection;
import com.jonayed.model.Employee;

@ManagedBean(name = "employeeDAOImp")
@SessionScoped
public class EmployeeDAOImp implements EmployeeDAO{
	
	private Connection con; 

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		try {
			con = new DBConnection().getConnection();
			String sql = "SELECT * FROM emp";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				Employee emp = new Employee();
				emp.setId(rs.getInt(1));
				emp.setFirstName(rs.getString(2));
				emp.setLastName(rs.getString(3));
				emp.setDivision(rs.getString(4));
				emp.setBuilding(rs.getString(5));
				emp.setTitle(rs.getString(6));
				emp.setRoom(rs.getString(7));
				
				employees.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(employees);
		return employees;
	}

	@Override
	public String addEmployee(Employee employee) {
		int insertrecord = 0;
		try {
			con = new DBConnection().getConnection();
			System.out.println("add employee called....");
			System.out.println(employee);
			String sql = "insert into emp (firstName, lastName, division, building, title, room) values (?, ?,?,?, ?, ?)";
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, employee.getFirstName());
			pst.setString(2, employee.getLastName());
			pst.setString(3, employee.getDivision());
			pst.setString(4, employee.getBuilding());
			pst.setString(5, employee.getTitle());
			pst.setString(6, employee.getRoom());
			
			insertrecord = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(insertrecord>0){
			return "index";
		} else
			return "";
	}

	@Override
	public String deleteEmployee(String empid) {
		int updaterecord = 0;
		try {
			System.out.println("deleting employee , id : " + empid);
			con = new DBConnection().getConnection();
			String sql = "delete from emp where id = "+empid;
			PreparedStatement pst = con.prepareStatement(sql);
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(updaterecord>0)
			return "index";
		else
			return "";
	}

	@Override
	public String gotoUpdateEmployee(String firstName, String lastName) {
		System.out.println("called gotoupdatedemployee....");
		Employee emp = new Employee();
		Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		try {
			con = new DBConnection().getConnection();
			String sql = "SELECT * FROM emp WHERE firstName = ? and lastName = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, firstName);
			pst.setString(2, lastName);
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()){
				emp.setId(rs.getInt(1));
				emp.setFirstName(rs.getString(2));
				emp.setLastName(rs.getString(3));
				emp.setDivision(rs.getString(4));
				emp.setBuilding(rs.getString(5));
				emp.setTitle(rs.getString(6));
				emp.setRoom(rs.getString(7));
			}
			session.put("updatedRecord", emp);
			System.out.println(emp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(emp != null)
			return "update";
		else
			return "";
	}

	@Override
	public String update(Employee employee) {
		
		System.out.println("update called....");
		System.out.println(employee);
		int updaterecord = 0;
		try {
			con = new DBConnection().getConnection();
			String sql = "update emp set firstName=?, lastName=?, division=?, building=?, title=? , room=? where id=?";
			PreparedStatement pst = con.prepareStatement(sql);
			
			pst.setString(1, employee.getFirstName());
			pst.setString(2, employee.getLastName());
			pst.setString(3, employee.getDivision());
			pst.setString(4, employee.getBuilding());
			pst.setString(5, employee.getTitle());
			pst.setString(6, employee.getRoom());
			pst.setInt(7, employee.getId());
			
			updaterecord = pst.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(updaterecord>0)
			return "index";
		else
			return "";
	}

	@Override
	public String searchEmployee(int id) {
		List<Employee> employee = new ArrayList<>();
		System.out.println("secarch employee by id : " + id);
		Employee emp = new Employee();
		Map<String, Object> flash = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		try {
			con = new DBConnection().getConnection();
			String sql = "SELECT * FROM emp WHERE id = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			
			ResultSet rs =  pst.executeQuery();
			while (rs.next()){
				emp.setId(rs.getInt(1));
				emp.setFirstName(rs.getString(2));
				emp.setLastName(rs.getString(3));
				emp.setDivision(rs.getString(4));
				emp.setBuilding(rs.getString(5));
				emp.setTitle(rs.getString(6));
				emp.setRoom(rs.getString(7));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		employee.add(emp);
		flash.put("searchRecord", employee);
		System.out.println(employee);
		return "search?faces-redirect=true";
	}
	
	@Override
	public String gotoSearch() {
		System.out.println("called gotosearch...");
		Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		List<Employee> employee = new ArrayList<>();
		System.out.println(employee);
		session.put("searchRecord", employee);
		return "search?faces-redirect=false";
	}

	@Override
	public String readXMlFileData() throws  Exception {
		System.out.println("readXmlfiledata called.....");
		
		File file = new File("C:/Users/HP/Desktop/Assainment/EmployeeManagement/employee.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document document = builder.parse(file);
			document.getDocumentElement().normalize();
			
			
	        NodeList list = document.getDocumentElement().getChildNodes();
	        
	        for(int i = 0; i<list.getLength(); i++){
	        	Node node = list.item(i);
	        	
	        	if (node.getNodeType() == Node.ELEMENT_NODE) {
	        		Element element = (Element) node;
	        		String id = element.getAttribute("id");
	        		String firstname = element.getElementsByTagName("firstname").item(0).getTextContent();
	        		String lastname = element.getElementsByTagName("lastname").item(0).getTextContent();
	        		String title = element.getElementsByTagName("title").item(0).getTextContent();
	        		String division = element.getElementsByTagName("division").item(0).getTextContent();
	        		String building = element.getElementsByTagName("building").item(0).getTextContent();
	        		String room = element.getElementsByTagName("room").item(0).getTextContent();
	        		
	        		System.out.println("id : " + id);
	        		System.out.println("firstName : " + firstname);
	        		System.out.println("lastname : " + lastname);
	        		System.out.println("title : " + title);
	        		System.out.println("division : " + division);
	        		System.out.println("building : " + building);
	        		System.out.println("room : " + room);
	        		
	        		
	        		con = new DBConnection().getConnection();
	    			System.out.println("add employee called....");
	    			
	    			String sql = "insert into emp (id, firstName, lastName, division, building, title, room) values (?,?,?,?, ?,?,?)";
	    			PreparedStatement pst = con.prepareStatement(sql);
	    			
	    			pst.setInt(1, Integer.parseInt(id));
	    			pst.setString(2, firstname);
	    			pst.setString(3, lastname);
	    			pst.setString(4, division);
	    			pst.setString(5, building);
	    			pst.setString(6, title);
	    			pst.setString(7, room);
	    			
	    			pst.executeUpdate();
	        		
	        		
	        		
	        	}
	        }
	        
			
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		}
		return "index";
	}

}
