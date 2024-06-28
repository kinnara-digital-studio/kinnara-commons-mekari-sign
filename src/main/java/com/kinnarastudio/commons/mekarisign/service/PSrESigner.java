package com.kinnarastudio.commons.mekarisign.service;

import com.kinnarastudio.commons.mekarisign.exception.AuthenticationException;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.stream.Collectors;

public class PSrESigner {
    public void requestSign(ServerType serverType, AuthenticationToken token, GlobalSignRequest globalSignRequest) throws RequestException {
        final RequestSigner[] signers = globalSignRequest.getSigners();
        final boolean signingOrder = globalSignRequest.isSigningOrder();
        final String callbackUrl = globalSignRequest.getCallbackUrl();

        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            // TODO: connect to Mekari Sign server
            // TODO: Andhela

            //Membuat JSON payload untuk permintaan POST
            JSONObject requestJson = globalSignRequest.toJson();

            // Menentukan URL server
            final HttpEntity httpEntity = new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON);
            final URL baseUrl = serverType.getBaseUrl();
            final String urlGlobal =  baseUrl + "/v2/esign/v1/documents/request_psre_sign";

            final HttpPost post = new HttpPost(urlGlobal) {{
                addHeader("Authorization", "Bearer " + token.getAccessToken());
                setEntity(httpEntity);
            }};

            final HttpResponse response = httpClient.execute(post);

            // Mengirim permintaan POST dan menerima response
            try (Reader reader = new InputStreamReader(response.getEntity().getContent());
                 BufferedReader bufferedReader = new BufferedReader(reader)) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());
                final JSONObject jsonResponsePayload = new JSONObject(responsePayload);

                final String id = jsonResponsePayload.getString("id");

                final String tokenType = jsonResponsePayload.getString("token_type");

                if (!tokenType.equalsIgnoreCase(TokenType.BEARER.toString())) {
                    throw new AuthenticationException();
                }
            }

            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                throw new RequestException("HTTP response code [" + statusCode + "]");
            }

        } catch (IOException | JSONException | AuthenticationException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }
}
