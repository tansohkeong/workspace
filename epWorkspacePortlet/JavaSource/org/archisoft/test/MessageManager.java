package org.archisoft.test;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

	protected String messagePropertyFile;
	protected ResourceBundle bundle;
	
	public MessageManager(String messageFile) {
		this.messagePropertyFile = messageFile;
	}
	
	public String getMessage(String tag) {
		if(bundle == null) {
			bundle = getResourceBundle(messagePropertyFile);
			if(bundle == null) {
				return "";
			}
		}
		
		return bundle.getString(tag);	
	}
	
	private ResourceBundle getResourceBundle(String baseName) {
		return getResourceBundle(baseName, null);
	}
	
	private ResourceBundle getResourceBundle(String baseName, Locale locale) {
		if(locale == null) {
			return ResourceBundle.getBundle(baseName);
		} else {
			return ResourceBundle.getBundle(baseName, locale);
			
		}
	}
}
