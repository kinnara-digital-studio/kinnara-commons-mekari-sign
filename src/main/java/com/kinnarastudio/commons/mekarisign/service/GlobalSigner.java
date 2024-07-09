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

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.util.stream.Collectors;

public class GlobalSigner {
    private static GlobalSigner instance = null;

    private GlobalSigner() {
    }

    public static GlobalSigner getInstance() {
        if (instance == null) {
            instance = new GlobalSigner();
        }

        return instance;
    }

    public SignResponseAttributes requestSign(ServerType serverType, AuthenticationToken token, SignRequest signRequest) throws RequestException {
        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            final URL baseUrl = serverType.getApiBaseUrl();
            final String urlGlobal = baseUrl + "/v2/esign/v1/documents/request_global_sign";
            final JSONObject requestJson = signRequest.toJson();

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
                final SignResponse signResponse = new SignResponse(jsonResponsePayload);
                return signResponse.getData().getAttributes();
            }
        } catch (IOException | JSONException | ParseException e) {
            throw new RequestException(e);
        }
    }
}
