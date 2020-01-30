package com.roby.showcase.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.roby.showcase.ui.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = Lumo.class, variant = Lumo.DARK)
public class NavigationLayout extends AppLayout implements BeforeEnterObserver {

	private Tabs tabs = new Tabs();
	private Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();
	private final Tabs menu;

	public NavigationLayout() {
		menu = createMenuTabs();
		
		this.addToNavbar(true, menu);

		this.setDrawerOpened(false);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
	}

	private static Tabs createMenuTabs() {
		final Tabs tabs = new Tabs();
		tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
		tabs.add(getAvailableTabs());
		tabs.setFlexGrowForEnclosedTabs(1);
		return tabs;
	}

	private static Tab[] getAvailableTabs() {
		final List<Tab> tabs = new ArrayList<>();

		tabs.add(createTab(VaadinIcon.USER, "User", MainView.class));

		final String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
		final Tab logoutTab = createTab(createLogoutLink(contextPath));
		tabs.add(logoutTab);
		return tabs.toArray(new Tab[tabs.size()]);
	}

	private static Tab createTab(VaadinIcon icon, String title, Class<? extends Component> viewClass) {
		return createTab(populateLink(new RouterLink(null, viewClass), icon, title));
	}

	private static Tab createTab(Component content) {
		final Tab tab = new Tab();
		tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
		tab.add(content);
		return tab;
	}

	private static Anchor createLogoutLink(String contextPath) {
		final Anchor a = populateLink(new Anchor(), VaadinIcon.ARROW_RIGHT, "Logout");
		a.setHref(contextPath + "/logout");
		return a;
	}

	private static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
		a.add(icon.create());
		a.add(title);
		return a;
	}
}
