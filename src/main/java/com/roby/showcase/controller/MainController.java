package com.roby.showcase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.roby.showcase.model.User;

/**
 * Generic controller
 * 
 * @author krappa
 *
 */
@Controller
public class MainController {
	// Login form
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}

	// Login form with error
	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}

	@RequestMapping
	public String error() {
		return "error";
	}
}
