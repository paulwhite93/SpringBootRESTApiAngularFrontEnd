package com.summitworks;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository er;
	
	public void insert(Employee e) {
		er.save(e);
	}
	
	public List<Employee> select(){
		Comparator<Employee> sortedEmployees = Comparator.comparingInt(Employee::getId);
		return er.getAllEmployees().stream().sorted(sortedEmployees).collect(Collectors.toList());
	}
	
	public void delete(int id) {
		er.deleteEmployeeById(id);
	}
	public void update(Employee e) {
		er.updateEmployeeById(
				e.getId(),
				e.getSalary(), 
				e.getFirstName(), 
				e.getLastName(),
				e.getDepartment()
				);
	}
	public Employee selectById(int id) {
		return er.getEmployeeById(id);
	}
}
