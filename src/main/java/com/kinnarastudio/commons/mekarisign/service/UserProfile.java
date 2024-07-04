package com.kinnarastudio.commons.mekarisign.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.ParseException;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.ProfileData;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.model.TokenType;
import com.kinnarastudio.commons.mekarisign.model.UserProfileModel;

public class UserProfile {
    private static UserProfile instance = null;
    
    private UserProfile() {
    }

    public static UserProfile getInstance() {
        if (instance == null) {
            instance = new UserProfile();
        }

        return instance;
    }

    public ProfileData requestProfile(ServerType serverType, AuthenticationToken token) throws RequestException, ParseException
    {
        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) 
        {

            final URL baseUrl = serverType.getBaseUrl();
            final String urlGlobal = baseUrl + "/v2/esign/v1/profile";

            final HttpGet get = new HttpGet(urlGlobal) {{
                if (token.getTokenType() == TokenType.BEARER) {
                    addHeader("Authorization", "Bearer " + token.getAccessToken());
                }
            }};

            final HttpResponse response = httpClient.execute(get);

            try (final Reader reader = new InputStreamReader(response.getEntity().getContent());
                 final BufferedReader bufferedReader = new BufferedReader(reader)) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());

                System.out.println(responsePayload);

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RequestException("HTTP response code [" + statusCode + "] response [" + responsePayload + "]");
                }

                final JSONObject jsonRespArray = new JSONObject(responsePayload);
                final UserProfileModel userProfileResponse = new UserProfileModel(jsonRespArray);
                return userProfileResponse.getData();
            }
        } catch (IOException | JSONException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }
}
