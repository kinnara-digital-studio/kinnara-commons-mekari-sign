package com.kinnarastudio.commons.mekarisign.service;

import java.io.*;
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

public class UserProfileService {
    private static UserProfileService instance = null;

    private UserProfileService() {
    }

    public static UserProfileService getInstance() {
        if (instance == null) {
            instance = new UserProfileService();
        }

        return instance;
    }

    public ProfileData requestProfile(ServerType serverType, AuthenticationToken token) throws RequestException, ParseException {
        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            final URL baseUrl = serverType.getApiBaseUrl();
            final String urlGlobal = String.format("%s/v%d/esign/v%d/profile", baseUrl, serverType.getApiVersion(), serverType.getEsignVersion());

            final HttpGet get = new HttpGet(urlGlobal) {{
                if (token.getTokenType() == TokenType.BEARER) {
                    addHeader("Authorization", "Bearer " + token.getAccessToken());
                }
            }};

            final HttpResponse response = httpClient.execute(get);

            try (final InputStream is = response.getEntity().getContent();
                 final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RequestException("HTTP response code [" + statusCode + "] response [" + responsePayload + "]");
                }

                final JSONObject jsonRespArray = new JSONObject(responsePayload);
                final UserProfileModel userProfileResponse = new UserProfileModel(jsonRespArray);
                return userProfileResponse.getData();
            }
        } catch (IOException | JSONException e) {
            throw new RequestException(e);
        }
    }
}
