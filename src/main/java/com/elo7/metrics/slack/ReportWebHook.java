package com.elo7.metrics.slack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.gson.Gson;
import com.sun.javafx.binding.StringFormatter;

public class ReportWebHook {

	private final String report;

	public ReportWebHook(GetReportsResponse response) {
		StringBuilder webHookText = new StringBuilder().append("Olá! Os números de ontem segundo o Google Analytics foram:\n\n");
		for (Report report: response.getReports()) {
			List<MetricHeaderEntry> metricHeaders = report.getColumnHeader().getMetricHeader().getMetricHeaderEntries();
			List<DateRangeValues> totals = report.getData().getTotals();
			for (int i = 0; i < metricHeaders.size(); i++) {
				webHookText.append(StringFormatter.format("*%s*: %s\n", metricHeaders.get(i).getName(), new BigDecimal(totals.get(0).getValues().get(i)).setScale(2, RoundingMode.HALF_DOWN).stripTrailingZeros().toPlainString()).get());
			}
		}
		this.report = webHookText.toString();
	}

	public String getWebhookBody() {
		Map<String, String> map = new HashMap<>();
		map.put("text", report);
		map.put("icon_emoji", ":disappointed:");
		return new Gson().toJson(map).toString();
	}

}
