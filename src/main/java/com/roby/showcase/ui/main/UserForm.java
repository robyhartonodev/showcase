package com.roby.showcase.ui.main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.roby.showcase.model.User;
import com.roby.showcase.model.enums.Role;
import com.roby.showcase.service.implementation.UserServiceImpl;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class UserForm extends FormLayout {
	private UserServiceImpl userServiceImpl;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	private MainView mainView;

	private TextField username = new TextField("Username");
	private EmailField email = new EmailField("Email");
	private PasswordField password = new PasswordField("Password");
	private ComboBox<Role> role = new ComboBox<>("Role");

	private Button saveBtn = new Button("Save", VaadinIcon.PLUS.create());
	private Button deleteBtn = new Button("Delete", VaadinIcon.TRASH.create());

	private Binder<User> binder = new Binder<>(User.class);

	public UserForm(MainView mainView, UserServiceImpl userServiceImpl) {
		this.mainView = mainView;
		this.userServiceImpl = userServiceImpl;

		// Set value to the combo box
		role.setItems(Role.values());

		// Create horizontal layout for the buttons
		HorizontalLayout buttons = new HorizontalLayout(saveBtn, deleteBtn);

		// Add the components to the form
		add(username, email, password, role, buttons);

		binder.bindInstanceFields(this);

		saveBtn.addClickListener(event -> save());
		deleteBtn.addClickListener(event -> delete());
	}

	/**
	 * Save and refresh the view
	 */
	private void save() {
		User user = binder.getBean();

		// Bcrypt hash the given password
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		userServiceImpl.addUser(user);
		mainView.listUsers(null);
		setUser(user);
	}

	/**
	 * Delete and refresh the view
	 */
	private void delete() {
		User user = binder.getBean();
		userServiceImpl.deleteUser(user.getId());
		mainView.listUsers(null);
		setUser(null);
	}

	public void setUser(User user) {
		binder.setBean(user);

		if (user == null) {
			setVisible(false);
		} else {
			setVisible(true);
			username.focus();
		}
	}

}
