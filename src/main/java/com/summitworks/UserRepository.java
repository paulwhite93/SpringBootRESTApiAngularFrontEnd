package com.summitworks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

	@Query("from User user where user.email=:email and user.password=:password")
	public User getUserByLogin(@Param("email")String email, @Param("password")String password);
	
	@Transactional
	@Modifying()
	@Query("update User user set user.accessToken=:token where user.email=:email")
	public void setUserToken(@Param("email")String email,@Param("token")String token);
	
	@Query("from User user where user.accessToken=:token")
	public User getUserByToken(@Param("token")String token);
}
