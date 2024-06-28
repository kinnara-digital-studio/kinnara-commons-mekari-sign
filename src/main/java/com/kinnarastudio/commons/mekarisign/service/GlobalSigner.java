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
    public void requestSign(ServerType serverType, AuthenticationToken token, GlobalSignRequest signRequest) throws RequestException {
        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            final JSONObject requestJson = signRequest.toJson();
            final HttpEntity httpEntity = new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON);
            final URL baseUrl = serverType.getBaseUrl();
            final String urlGlobal = baseUrl + "/v2/esign/v1/documents/request_global_sign";

            final HttpPost post = new HttpPost(urlGlobal) {{
                addHeader("Authorization", "Bearer " + token.getAccessToken());
                setEntity(httpEntity);
            }};

            final HttpResponse response = httpClient.execute(post);

            // Mengirim permintaan POST dan menerima response
            try (final Reader reader = new InputStreamReader(response.getEntity().getContent());
                 final BufferedReader bufferedReader = new BufferedReader(reader)) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());
                final JSONObject jsonResponsePayload = new JSONObject(responsePayload);
                final GlobalSignResponse globalSignResponse = new GlobalSignResponse(jsonResponsePayload);
            }

            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                throw new RequestException("HTTP response code [" + statusCode + "]");
            }

        } catch (IOException | JSONException | ParseException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }
}
