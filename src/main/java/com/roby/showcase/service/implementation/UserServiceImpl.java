package com.roby.showcase.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.roby.showcase.model.User;
import com.roby.showcase.repository.UserRepository;
import com.roby.showcase.service.UserService;
import com.roby.showcase.util.NullPropertiesUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Integer id) {
		return userRepository.findOneById(id).orElse(null);
	}

	@Override
	public List<User> getAllUsersByUsernameStartsWithIgnoreCase(String userName) {
		return userRepository.findByUsernameStartsWithIgnoreCase(userName);
	}

	@Override
	public void addUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void updateUser(User user, Integer id) {
		// Get existed user from db based on the id
		User oldUser = userRepository.findById(id).orElse(null);
		// Copy the properties values of posted User object that is not null to the old
		// one
		NullPropertiesUtils.copyNonNullProperties(user, oldUser);
		userRepository.save(oldUser);

	}

	@Override
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	public void addUserPasswordHashed(User user) {
		// hash the password before sending it 
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public User createOrUpdateUser(User userEntity) {
		if (userEntity.getId() == null) {
			userEntity = userRepository.save(userEntity);

			return userEntity;
		} else {
			Optional<User> user = userRepository.findById(userEntity.getId());

			if (user.isPresent()) {
				User newEntity = user.get();

				newEntity.setEmail(userEntity.getEmail());
				newEntity.setUsername(userEntity.getUsername());
				newEntity.setPassword(userEntity.getPassword());
				newEntity.setRole(userEntity.getRole());

				newEntity = userRepository.save(newEntity);

				return newEntity;
			} else {
				userEntity = userRepository.save(userEntity);

				return userEntity;
			}
		}
	}

}
