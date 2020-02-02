package com.roby.showcase.service;

import java.util.List;

import com.roby.showcase.model.User;

/**
 * Interface for user
 * 
 * @author Roby Hartono
 *
 */
public interface UserService {
	// Get all users
	List<User> getAllUsers();

	// Get all users by username with ignore case
	List<User> getAllUsersByUsernameStartsWithIgnoreCase(String username);

	// Get a user by id
	User getUserById(Integer id);

	// Add a user
	void addUser(User user);

	// Update a user
	void updateUser(User user, Integer id);

	// Delete a user
	void deleteUser(Integer id);
}
