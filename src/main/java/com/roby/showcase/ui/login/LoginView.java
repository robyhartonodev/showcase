package com.roby.showcase.ui.login;

import com.roby.showcase.ui.main.MainView;
import com.roby.showcase.util.SecurityUtils;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "login")
@PageTitle("Login")
public class LoginView extends LoginOverlay implements AfterNavigationObserver, BeforeEnterObserver {

	public LoginView() {
		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setHeader(new LoginI18n.Header());
		i18n.getHeader().setTitle("CRUD Showcase");
		i18n.getHeader().setDescription("username: admin \n password: password");
		i18n.setAdditionalInformation(null);
		i18n.setForm(new LoginI18n.Form());
		i18n.getForm().setSubmit("Sign in");
		i18n.getForm().setTitle("Sign in");
		i18n.getForm().setUsername("Username");
		i18n.getForm().setPassword("Password");
		setI18n(i18n);
		setForgotPasswordButtonVisible(false);
		setAction("login");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (SecurityUtils.isUserLoggedIn()) {
			event.forwardTo(MainView.class);
		} else {
			setOpened(true);
			setError(true);
		}
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
	}
}
