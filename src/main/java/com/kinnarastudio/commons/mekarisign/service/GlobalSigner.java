package com.kinnarastudio.commons.mekarisign.service;

import com.kinnarastudio.commons.mekarisign.exception.AuthenticationException;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GlobalSigner {
    public void requestSign(ServerType serverType, AuthenticationToken token, GlobalSignRequest globalSignRequest) throws RequestException {
        final File pdfFile = globalSignRequest.getFile();
        final String filename = pdfFile.getName();
        final Signer[] signer = globalSignRequest.getSigners();
        final boolean signingOrder = globalSignRequest.isSigningOrder();
        final String callbackUrl = globalSignRequest.getCallbackUrl();

        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             final FileInputStream fis = new FileInputStream(pdfFile)) {

            final StringBuilder sb = new StringBuilder();
            final Base64.Encoder encoder = Base64.getEncoder();

            int len = Math.toIntExact(pdfFile.length());
            final byte[] buffer = new byte[len];

            while (fis.read(buffer) > 0) {
                final String base64encoded = encoder.encodeToString(buffer);
                sb.append(base64encoded);
            }

            final String encodedFile = sb.toString();

            // TODO: connect to Mekari Sign server
            // TODO: Andhela

            //Membuat JSON payload untuk permintaan POST
            JSONObject json = new JSONObject();
            json.put("filename", filename);
            json.put("file", encodedFile);
            json.put("signer", signer);
            json.put("signing_order", signingOrder);
            json.put("callback_url", callbackUrl);

            // Menentukan URL server
            final HttpEntity httpEntity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
            final URL baseUrl = serverType.getSsoBaseUrl();
            final String urlGlobal =  baseUrl + "/v2/esign/v1/documents/request_global_sign";
            final HttpPost post = new HttpPost(urlGlobal);
            post.setEntity(httpEntity);
            final HttpResponse response = httpClient.execute(post);
            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                throw new RequestException("HTTP response code [" + statusCode + "]");
            }
            // Mengirim permintaan POST dan menerima response
            try (Reader reader = new InputStreamReader(response.getEntity().getContent());
                 final BufferedReader bufferedReader = new BufferedReader(reader)) {

                // TODO : check with server

                final JSONObject jsonResponsePayload = new JSONObject(bufferedReader.lines().collect(Collectors.joining()));

                final String id = jsonResponsePayload.getString("id");

                final String tokenType = jsonResponsePayload.getString("token_type");

                if (!tokenType.equalsIgnoreCase(TokenType.BEARER.toString())) {
                    throw new AuthenticationException();
                }
            }

        } catch (IOException | JSONException | AuthenticationException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }
}
