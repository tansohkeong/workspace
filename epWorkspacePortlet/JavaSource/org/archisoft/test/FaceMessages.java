package org.archisoft.test;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class FaceMessages {
	

	public String setValidationMessage(String msg, Severity severity) {
		FacesMessage fm = new FacesMessage(msg);
		fm.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(msg, fm);
		return "error";
	}

}
