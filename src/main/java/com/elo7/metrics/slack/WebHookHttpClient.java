package com.elo7.metrics.slack;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.elo7.metrics.infra.ApplicationProperties;

public class WebHookHttpClient {

	private final CloseableHttpClient client;

	public WebHookHttpClient() throws IOException {
		client = HttpClientBuilder.create().build();
	}

	public void send(ReportWebHook report) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(ApplicationProperties.get("slack.webhook.url"));
		post.setEntity(new StringEntity(report.getWebhookBody(), "UTF-8"));
		post.setHeader("Content-Type", "application/json");
		try {
			CloseableHttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception(("Couldn't send webhook"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
