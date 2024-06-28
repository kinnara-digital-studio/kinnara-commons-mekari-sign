package com.kinnarastudio.commons.mekarisign.service;

import com.kinnarastudio.commons.mekarisign.exception.AuthenticationException;
import com.kinnarastudio.commons.mekarisign.model.Authentication;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.TokenType;
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
    private Authenticator() {
    }

    private static Authenticator instance = null;

    public static Authenticator getInstance() {
        if(instance == null) {
            instance = new Authenticator();
        }
        return instance;
    }

    /**
     * <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#9df19971-fcaf-4cf7-bc23-9188c2ed625d">Get Authorized Code</a>
     */
    public String getAuthorizedCode(ServerType serverType, String clientId, String username, String password) throws RequestException {
        final String methodLabel = "Get Authorized Code";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final URL ssoBaseUrl = serverType.getSsoBaseUrl();
            final String url = ssoBaseUrl + "/auth?client_id=" + clientId + "&response_type=code&scope=esign&lang=id";
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

    public AuthenticationToken authenticate(ServerType serverType, Authentication authentication) throws RequestException {
        final URL ssoBaseUrl = serverType.getSsoBaseUrl();
        final String url = ssoBaseUrl + "/auth/oauth2/token";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final JSONObject jsonPayload = new JSONObject() {{

                final String clientId = authentication.getClientId();
                put("client_id", clientId);

                final String clientSecret = authentication.getClientSecret();
                put("client_secret", clientSecret);

                final String grantType = authentication.getGrantType().toString();
                put("grant_type", grantType);

                final String code = authentication.getCode();
                put("code", code);
            }};

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

                final String accessToken = jsonResponsePayload.getString("access_token");

                final String tokenType = jsonResponsePayload.getString("token_type");

                if(!tokenType.equalsIgnoreCase(TokenType.BEARER.toString())) {
                    throw new AuthenticationException();
                }

                final long expires = jsonResponsePayload.getLong("expires_in");
                final String refreshToken = jsonResponsePayload.getString("refresh_token");

                final AuthenticationToken token = new AuthenticationToken(accessToken, TokenType.BEARER, expires, refreshToken);
                return token;
            }
        } catch (IOException | JSONException | AuthenticationException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }

    public synchronized AuthenticationToken refresh(ServerType serverType, Authentication authentication, AuthenticationToken prevToken) {
        throw new IllegalArgumentException();
    }
}
