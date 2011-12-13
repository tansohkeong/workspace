package org.archisoft.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class Principal {

	private String name;
	private String password;

	protected MessageManager messageManager = new MessageManager(
			"com.bundle.messagesParam");

	public void addPrincipal() {
		HttpServletRequest request = (HttpServletRequest) (FacesContext
				.getCurrentInstance().getExternalContext().getRequestMap()
				.get("com.liferay.portal.kernel.servlet.PortletServletRequest"));
		String remoteUser = FacesContext.getCurrentInstance()
				.getExternalContext().getRemoteUser();

		if (remoteUser != null) {
			try {
				Class<?> usuClass = request
						.getClass()
						.getClassLoader()
						.loadClass("com.liferay.portal.service.UserServiceUtil");
				Method uidMethod = usuClass
						.getMethod("getUserById", long.class);

				Object userObj = uidMethod.invoke(usuClass,
						Long.parseLong(remoteUser));

				Method loginMethod = userObj.getClass().getMethod("getLogin",
						new Class[] {});
				Object loginObj = loginMethod.invoke(userObj, new Object[] {});

				name = loginObj.toString();
				password = (String) request.getSession().getAttribute(
						"USER_PASSWORD");

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			name = "roslan";
			password = "welcome1";
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
