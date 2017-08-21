package com.elo7.metrics.credentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;

public class AnalyticsReportingServiceFactory {

	public static AnalyticsReporting create() throws GeneralSecurityException, IOException {
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credentials = GoogleCredential.fromStream(new FileInputStream("credentials.json")).createScoped(AnalyticsReportingScopes.all());

		return new AnalyticsReporting.Builder(httpTransport, GsonFactory.getDefaultInstance(), credentials)
				.setApplicationName("metrics").build();
	}

}
