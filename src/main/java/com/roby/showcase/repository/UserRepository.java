package com.roby.showcase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roby.showcase.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String userName);
	
	Optional<User> findOneById(Integer id); 
	
	List<User> findByUsernameStartsWithIgnoreCase(String userName); 
}
