package com.roby.showcase.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.roby.showcase.model.User;
import com.roby.showcase.service.implementation.UserServiceImpl;

/**
 * User Controller
 * 
 * @author krappa
 *
 */
@Controller
public class UserMvcController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@GetMapping("/")
	public String home(Model model) {
		List<User> listUsers = userServiceImpl.getAllUsers();

		model.addAttribute("users", listUsers);

		return "home";
	}

	@GetMapping(path = "/create")
	public String createNewUser(Model model) {
		model.addAttribute("user", new User());
		return "create-user";
	}

	@GetMapping(path = "/edit/{id}")
	public String editUserById(Model model, @PathVariable("id") Optional<Integer> id) {
		if (id.isPresent()) {
			User editUser = userServiceImpl.getUserById(id.get());
			model.addAttribute("user", editUser);
		}
		return "edit-user";
	}

	@RequestMapping(path = "/delete/{id}")
	public String deleteUserById(Model model, @PathVariable("id") Integer id) {
		userServiceImpl.deleteUser(id);
		return "redirect:/";
	}

	@RequestMapping(path = "/updateUser", method = RequestMethod.POST)
	public String updateUser(User user) {
		userServiceImpl.createOrUpdateUser(user);
		return "redirect:/";
	}

	@RequestMapping(path = "/createUser", method = RequestMethod.POST)
	public String createUser(User user) {
		userServiceImpl.addUserPasswordHashed(user);
		return "redirect:/";
	}

}
