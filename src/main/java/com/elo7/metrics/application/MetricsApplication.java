package com.elo7.metrics.application;

import com.elo7.metrics.credentials.AnalyticsReportingServiceFactory;
import com.elo7.metrics.reports.ReportRetriever;
import com.elo7.metrics.slack.ReportWebHook;
import com.elo7.metrics.slack.WebHookHttpClient;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;

public class MetricsApplication {

	public static void main(String[] args) throws Exception {

		AnalyticsReporting service = AnalyticsReportingServiceFactory.create();
		GetReportsResponse response = ReportRetriever.get(service);

		new WebHookHttpClient().send(new ReportWebHook(response));
	}
}