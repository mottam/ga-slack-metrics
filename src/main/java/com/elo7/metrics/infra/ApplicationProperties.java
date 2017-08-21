package com.elo7.metrics.infra;

import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {

	private static ApplicationProperties instance = null;
	private final Properties properties;

	private ApplicationProperties() {
		this.properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		if (instance == null) {
			instance = new ApplicationProperties();
		}
		return (String) instance.properties.get(key);
	}

}
