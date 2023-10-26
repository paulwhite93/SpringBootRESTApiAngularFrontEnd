package com.summitworks;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/")
public class EmployeeController {
	
	@Autowired
	private EmployeeService es;
	
	@Autowired
	private UserService us;
	
	@RequestMapping(value="/getEmployees",method=RequestMethod.GET)
	public List<Employee> getEmployees(@RequestHeader(HttpHeaders.AUTHORIZATION)String accessToken){
		System.out.println(accessToken);
		if(us.checkToken(accessToken)!= null) {
			return es.select();
		}
		return null;
	}
	@RequestMapping(value="/addEmployee",method=RequestMethod.POST)
	public Employee addEmployee(
			@RequestBody Employee e,
			@RequestHeader(HttpHeaders.AUTHORIZATION)String accessToken) {
		if(us.checkToken(accessToken)!= null) {
			es.insert(e);
			return e;
		}
		return null;
	}
	@RequestMapping(value="/getEmployee/{id}",method=RequestMethod.GET)
	public Employee getEmployee(
			@PathVariable int id,
			@RequestHeader(HttpHeaders.AUTHORIZATION)String accessToken) {
		if(us.checkToken(accessToken)!= null) {
			Employee emp = es.selectById(id);
			System.out.println(emp);
			if (emp != null) {
				return emp;
			}
		}
		return null;
	}
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public void deleteEmployee(
			@PathVariable int id,
			@RequestHeader(HttpHeaders.AUTHORIZATION)String accessToken) {
		if(us.checkToken(accessToken)!= null)
			es.delete(id);
	}
	@RequestMapping(value="/patch",method=RequestMethod.PATCH)
	public Employee patchEmployee(
			@RequestBody Employee e,
			@RequestHeader(HttpHeaders.AUTHORIZATION)String accessToken) {
		if(us.checkToken(accessToken)!= null) {
			es.update(e);
			return e;
		}
		return null;
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public void registerUser(@RequestBody User user) {
		us.insert(user);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public User loginUser(@RequestBody User user) {
		System.out.println(user.getEmail()+" "+user.getPassword());
		User user1 = us.checkCredentials(user.getEmail(), user.getPassword());
		return user1;
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.PATCH)
	public void logoutUser() {
		
	}
}
