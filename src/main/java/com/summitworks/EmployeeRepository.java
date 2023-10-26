package com.summitworks;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer>{
	
	@Query("from Employee e where e.id=:id")
	public Employee getEmployeeById(@Param("id")int id);
	
	@Transactional
	@Modifying()
	@Query("delete Employee e where e.id=:id")
	public void deleteEmployeeById(@Param("id")int id);
	
	@Transactional
	@Modifying()
	@Query("update Employee e set e.salary=:salary, "
			+ "e.firstName=:firstName, "
			+ "e.lastName=:lastName, "
			+ "e.department=:department "
			+ "where e.id=:id")
	public void updateEmployeeById(
			@Param("id")int id,
			@Param("salary") int salary,
			@Param("firstName") String firstName,
			@Param("lastName") String lastName,
			@Param("department") String department
			);
	
	@Query("from Employee")
	public List<Employee> getAllEmployees();
}
