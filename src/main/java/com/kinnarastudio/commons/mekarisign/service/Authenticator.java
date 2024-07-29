package com.kinnarastudio.commons.mekarisign.service;

import com.kinnarastudio.commons.mekarisign.exception.InvalidTokenException;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationRequest;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.RefreshTokenRequest;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.util.stream.Collectors;

public class Authenticator {
    private Authenticator() {
    }

    private static Authenticator instance = null;

    public static Authenticator getInstance() {
        if (instance == null) {
            instance = new Authenticator();
        }
        return instance;
    }

    public AuthenticationToken authenticate(ServerType serverType, AuthenticationRequest authentication) throws RequestException {
        final URL ssoBaseUrl = serverType.getSsoBaseUrl();
        final String url = ssoBaseUrl + "/auth/oauth2/token";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final JSONObject jsonPayload = authentication.toJson();

            final HttpPost request = new HttpPost(url) {{
                final HttpEntity httpEntity = new StringEntity(jsonPayload.toString(), ContentType.APPLICATION_JSON);
                setEntity(httpEntity);
            }};

            final HttpResponse response = client.execute(request);

            try (final InputStream is = response.getEntity().getContent();
                 final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());
                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new InvalidTokenException(statusCode, responsePayload);
                }

                final JSONObject jsonResponsePayload = new JSONObject(responsePayload);
                final AuthenticationToken token = new AuthenticationToken(jsonResponsePayload, serverType);
                return token;
            }
        } catch (IOException | JSONException | ParseException | InvalidTokenException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }

    //Request Refresh Token
    public synchronized AuthenticationToken refresh(ServerType serverType, RefreshTokenRequest reqRefToken) throws IllegalAccessException, RequestException {
        final URL ssoBaseUrl = serverType.getSsoBaseUrl();
        final String url = ssoBaseUrl + "/auth/oauth2/token";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            final JSONObject jsonPayload = reqRefToken.toJson();

            final HttpPost request = new HttpPost(url) {{
                final HttpEntity httpEntity = new StringEntity(jsonPayload.toString(), ContentType.APPLICATION_JSON);
                setEntity(httpEntity);
            }};

            final HttpResponse response = client.execute(request);

            try (final InputStream is = response.getEntity().getContent();
                 final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());
                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new InvalidTokenException(statusCode, responsePayload);
                }

                final JSONObject jsonResponsePayload = new JSONObject(responsePayload);
                final AuthenticationToken token = new AuthenticationToken(jsonResponsePayload, serverType);
                return token;
            }
        } catch (IOException | JSONException | ParseException | InvalidTokenException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }
}
