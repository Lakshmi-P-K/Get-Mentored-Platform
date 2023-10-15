package com.nineleaps.authentication.jwt.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.nineleaps.authentication.jwt.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User,String> {

	
    @Query("select u from User u where u.userMail=:email")
    public User getUserEmail(@Param("email") String email);


}
