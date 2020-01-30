package com.roby.showcase.ui.main;

import org.springframework.util.StringUtils;

import com.roby.showcase.model.User;
import com.roby.showcase.service.implementation.UserServiceImpl;
import com.roby.showcase.ui.NavigationLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "home", layout = NavigationLayout.class)
public class MainView extends VerticalLayout {

	private UserServiceImpl userServiceImpl;

	private UserForm userForm;

	private Grid<User> userGrid = new Grid<>(User.class);

	private TextField filterTextField = new TextField();

	private Button addUserButton = new Button("Add User", VaadinIcon.PLUS.create());

	public MainView(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
		userForm = new UserForm(this, userServiceImpl);

		// Button & Filter horizontal layout
		HorizontalLayout actions = new HorizontalLayout(filterTextField, addUserButton);

		// Main Content layout contain user grid and user form
		HorizontalLayout mainContent = new HorizontalLayout(userGrid, userForm);
		mainContent.setSizeFull();
		userGrid.setSizeFull();

		// Add components to the layout
		add(actions, mainContent);

		// Grid configuration
		userGrid.setHeight("300px");
		userGrid.setColumns("id", "username", "email", "password", "role");
		userGrid.getColumnByKey("id").setWidth("100px").setFlexGrow(0);

		filterTextField.setPlaceholder("Filter by username");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filterTextField.setValueChangeMode(ValueChangeMode.EAGER);
		filterTextField.addValueChangeListener(e -> listUsers(e.getValue()));

		// Set when a single row selected fill the form with the selected user
		userGrid.asSingleSelect()
				.addValueChangeListener(event -> userForm.setUser(userGrid.asSingleSelect().getValue()));

		// Configure click listener for add user button
		addUserButton.addClickListener(e -> {
			userGrid.asSingleSelect().clear();
			userForm.setUser(new User());
		});

		// Set layout size to full
		setSizeFull();

		// Initialize listing
		listUsers(null);

		// No user in the form initially
		userForm.setUser(null);
	}

	public void listUsers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			userGrid.setItems(userServiceImpl.getAllUsers());
		} else {
			userGrid.setItems(userServiceImpl.getAllUsersByUsernameStartsWithIgnoreCase(filterText));
		}
	}

}
