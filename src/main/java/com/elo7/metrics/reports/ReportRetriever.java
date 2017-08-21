package com.elo7.metrics.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.elo7.metrics.infra.ApplicationProperties;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;

public class ReportRetriever {

	public static GetReportsResponse get(AnalyticsReporting service) throws IOException {
		// Create the DateRange object.
		DateRange dateRange = new DateRange();
		dateRange.setStartDate("1DaysAgo");
		dateRange.setEndDate("1DaysAgo");

		// Create the Metrics object.
		Metric sessions = new Metric()
				.setExpression("ga:sessions")
				.setAlias("Total de sessões");

		Metric conversionRate = new Metric()
				.setExpression("ga:transactionsPerSession")
				.setAlias("Taxa de conversão");

		Metric gmv = new Metric()
				.setExpression("ga:totalValue")
				.setAlias("GMV");

		//Create the Dimensions object.
		Dimension browser = new Dimension()
				.setName("ga:browser");

		// Create the ReportRequest object.
		ReportRequest request = new ReportRequest()
				.setViewId(ApplicationProperties.get("analytics.viewid"))
				.setDateRanges(Arrays.asList(dateRange))
				.setDimensions(Arrays.asList(browser))
				.setMetrics(Arrays.asList(sessions, conversionRate, gmv));

		ArrayList<ReportRequest> requests = new ArrayList<>();
		requests.add(request);

		// Create the GetReportsRequest object.
		GetReportsRequest getReport = new GetReportsRequest()
				.setReportRequests(requests);

		// Call the batchGet method.
		GetReportsResponse response = service.reports().batchGet(getReport).execute();

		// Return the response.
		return response;
	}
}
