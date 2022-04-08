package com.jonayed.model;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.jonayed.DAO.EmployeeDAOImp;

@ManagedBean(name = "employeeBeen")
@RequestScoped
public class Employee {
	
	// Employee ID, First Name, Last Name, Division, Building, Title and Room.
	private int id;
	private String firstName;
	private String lastName;
	private String division;
	private String building;
	private String title;
	private String room;
	
	public Employee() {

	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", division=" + division
				+ ", building=" + building + ", title=" + title + ", room=" + room + "]";
	}
	
	
//	public String searchEmployee(){
//		System.out.println("id : "+ id);
//		EmployeeDAOImp impdao = new EmployeeDAOImp();
//		Employee emp = impdao.searchEmployee(id);
//		Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
//		if(emp != null){
//			this.setId(emp.getId());
//		//this.id = emp.getId();
//		this.firstName = emp.getFirstName();
//		this.lastName = emp.getLastName();
//		this.division = emp.getDivision();
//		this.building = emp.getBuilding();
//		this.room = emp.getRoom();
//		this.title = emp.getTitle();
//		}
//		System.out.println(emp);
//		session.put("searchRecord", emp);
//		return "hello emp";
//	}
	

}
