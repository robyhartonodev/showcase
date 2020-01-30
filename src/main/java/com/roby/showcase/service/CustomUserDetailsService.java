package com.roby.showcase.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.roby.showcase.model.User;
import com.roby.showcase.repository.UserRepository;


@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Create an user instance / object based on the username
		User user = userRepository.findByUsername(username).orElse(null);

		// If user not found then throw the exception
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		// Setting authority for the user
		// Authority is needed to restrict user for accessing certain pages
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));

		// return UserDetails object
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}
}
