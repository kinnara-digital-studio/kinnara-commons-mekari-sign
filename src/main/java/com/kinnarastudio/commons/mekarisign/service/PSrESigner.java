package com.kinnarastudio.commons.mekarisign.service;

import com.kinnarastudio.commons.mekarisign.exception.InvalidTokenException;
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

public class PSrESigner {

    private static PSrESigner instance = null;

    private PSrESigner() {
    }

    public static PSrESigner getInstance() {
        if (instance == null) {
            instance = new PSrESigner();
        }

        return instance;
    }
    public SignResponseAttributes requestSign(ServerType serverType, AuthenticationToken token, SignRequest signRequest) throws RequestException, ParseException {
        final RequestSigner[] signers = signRequest.getSigners();
        final boolean signingOrder = signRequest.isSigningOrder();
        final String callbackUrl = signRequest.getCallbackUrl();

        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            // TODO: connect to Mekari Sign server

            //Membuat JSON payload untuk permintaan POST

            // Menentukan URL server
            final JSONObject requestJson = signRequest.toJson();
            final HttpEntity httpEntity = new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON);
            final URL baseUrl = serverType.getApiBaseUrl();
            final String urlGlobal =  String.format("%s/v%d/esign/v%d/documents/request_psre_sign", baseUrl, serverType.getApiVersion(), serverType.getEsignVersion());

            final HttpPost post = new HttpPost(urlGlobal) {{
                addHeader("Authorization", "Bearer " + token.getAccessToken());
                setEntity(httpEntity);
            }};

            final HttpResponse response = httpClient.execute(post);

            try (final InputStream is = response.getEntity().getContent();
                 final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new InvalidTokenException(statusCode, responsePayload);
                }

                final JSONObject jsonResponsePayload = new JSONObject(responsePayload);
                final SignResponse signResponse = new SignResponse(jsonResponsePayload);
                return signResponse.getData().getAttributes();
            }

            // final int statusCode = response.getStatusLine().getStatusCode();

            // if (statusCode != 200) {
            //     throw new RequestException("HTTP response code [" + statusCode + "]");
            // }

        } catch (IOException | JSONException | InvalidTokenException e) {
            throw new RequestException(e);
        }
    }
}
