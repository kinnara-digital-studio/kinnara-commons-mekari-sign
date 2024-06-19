package com.kinnarastudio.commons.mekarisign;

import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.stream.Collectors;

public class Authenticator {
    private final ServerType serverType;

    private String token = null;

    public Authenticator(ServerType serverType) {
        this.serverType = serverType;
    }
    /**
     * <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#9df19971-fcaf-4cf7-bc23-9188c2ed625d">Get Authorized Code</a>
     */
    public String getAuthorizedCode(String clientId) throws RequestException {
        final String methodLabel = "Get Authorized Code";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final URL ssoBaseUrl = serverType.getSsoBaseUrl();
            final String url = ssoBaseUrl + "/auth?client_id=" + clientId + "&response_type=code&scope=esign";
            final HttpGet request = new HttpGet(url);
            final HttpResponse response = client.execute(request);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RequestException("HTTP response code [" + statusCode + "]");
            }

            try (Reader reader = new InputStreamReader(response.getEntity().getContent());
                 BufferedReader bufferedReader = new BufferedReader(reader)) {

                // TODO : check with server

                final JSONObject jsonResponsePayload = new JSONObject(bufferedReader.lines().collect(Collectors.joining()));
                final String code = jsonResponsePayload.getString("code");
                return code;
            }
        } catch (IOException e) {
            throw new RequestException("Error executing [" + methodLabel + "]", e);
        }
    }

    public Authenticator authenticate(String clientId, String clientSecret) throws RequestException {
        final String authorizedCode = getAuthorizedCode(clientId);
        final URL ssoBaseUrl = serverType.getSsoBaseUrl();
        final String url = ssoBaseUrl + "/auth/oauth2/token";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final JSONObject jsonPayload = new JSONObject();
            jsonPayload.put("client_id", clientId);
            jsonPayload.put("client_secret", clientSecret);
            jsonPayload.put("grant_type", "authorization_code");
            jsonPayload.put("code", authorizedCode);

            final HttpEntity httpEntity = new StringEntity(jsonPayload.toString(), ContentType.APPLICATION_JSON);
            final HttpPost request = new HttpPost(url);
            request.setEntity(httpEntity);
            final HttpResponse response = client.execute(request);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new RequestException("HTTP response code [" + statusCode + "]");
            }

            try (Reader reader = new InputStreamReader(response.getEntity().getContent());
                 BufferedReader bufferedReader = new BufferedReader(reader)) {

                // TODO : check with server

                final JSONObject jsonResponsePayload = new JSONObject(bufferedReader.lines().collect(Collectors.joining()));
                token = jsonResponsePayload.getString("token");
                return this;
            }
        } catch (IOException | JSONException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }

    public String getToken() {
        return token;
    }
}
