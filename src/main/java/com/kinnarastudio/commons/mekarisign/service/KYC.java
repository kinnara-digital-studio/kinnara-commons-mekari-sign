package com.kinnarastudio.commons.mekarisign.service;

import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.stream.Collectors;

public class KYC {
    private static KYC instance = null;

    private KYC() {
    }

    public static KYC getInstance() {
        if(instance == null) {
            instance = new KYC();
        }
        return instance;
    }

    public RespAttributesKYC requestKYC(ServerType serverType, AuthenticationToken token, RequestKYC requestKYC) throws IOException, RequestException {
        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            final URL baseUrl = serverType.getApiBaseUrl();
            final String urlGlobal = String.format("%s/v%d/esign/v%d/ekyc_request", baseUrl, serverType.getApiVersion(), serverType.getEsignVersion());
            final JSONObject requestJson = requestKYC.toJson();

            final HttpPost post = new HttpPost(urlGlobal) {{
                if (token.getTokenType() == TokenType.BEARER) {
                    addHeader("Authorization", "Bearer " + token.getAccessToken());
                }

                final HttpEntity httpEntity = new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON);
                setEntity(httpEntity);
            }};
            final HttpResponse response = httpClient.execute(post);

            try (final InputStream is = response.getEntity().getContent();
                 final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RequestException("HTTP response code [" + statusCode + "] response [" + responsePayload + "]");
                }

                final JSONObject jsonResponsePayload = new JSONObject(responsePayload);
                final RespKYC respKYC = new RespKYC(jsonResponsePayload);
                return respKYC.getDataKYC().getAttributesKYC();
            }
        } catch (IOException | JSONException | ParseException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }
}
