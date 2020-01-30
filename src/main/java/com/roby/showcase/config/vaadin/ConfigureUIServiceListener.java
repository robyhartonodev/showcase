package com.roby.showcase.config.vaadin;

import org.springframework.stereotype.Component;

import com.roby.showcase.ui.login.LoginView;
import com.roby.showcase.util.SecurityUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@Component	
public class ConfigureUIServiceListener implements VaadinServiceInitListener {
	

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::beforeEnter);
		});
	}

	/**
	 * Reroutes the user if (s)he is not authorized to access the view.
	 *
	 * @param event
	 *            before navigation event with event details
	 */
	private void beforeEnter(BeforeEnterEvent event) {
		if (!LoginView.class.equals(event.getNavigationTarget())
		    && !SecurityUtils.isUserLoggedIn()) {
			event.rerouteTo("login");
		}
	}

}
