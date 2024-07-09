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
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.util.stream.Collectors;

public class AutoSignService {
    private static AutoSignService instance = null;

    private AutoSignService() {

    }

    public static AutoSignService getInstance() {
        if (instance == null) {
            instance = new AutoSignService();
        }
        return instance;
    }

    public RespDataAutoSign[] requestAutoSign(ServerType serverType, AuthenticationToken token, ReqAutoSign reqAutoSign) throws RequestException, IOException {

        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            final URL baseUrl = serverType.getApiBaseUrl();
            final String urlAuto = String.format("%s/v%d/esign/v%d/auto_sign", baseUrl, serverType.getApiVersion(), serverType.getEsignVersion());
            final JSONObject requestJson = reqAutoSign.toJson();

            final HttpPost post = new HttpPost(urlAuto) {{
                if(token.getTokenType() == TokenType.BEARER) {
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
                final AutoSignResponse autoResponse = new AutoSignResponse(jsonResponsePayload);
                return autoResponse.getRespData();

            }
        } catch (IOException | ParseException e) {
            throw new RequestException(e);
        }
    }
}
