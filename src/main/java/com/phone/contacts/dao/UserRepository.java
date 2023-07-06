package com.phone.contacts.dao;

import com.phone.contacts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {


    // user request
    @Query("select u from User u where u.emails=:email")
    public User getUserByUserName(@Param("email") String email);
}
