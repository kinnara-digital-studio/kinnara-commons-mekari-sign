package com.kinnarastudio.commons.mekarisign.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;

import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.model.TokenType;

public class DocumentDownloader {
    private static DocumentDownloader instance = null;

    private DocumentDownloader() {
    }

    public static DocumentDownloader getInstance() {
        if (instance == null) {
            instance = new DocumentDownloader();
        }

        return instance;
    }

    public InputStream downloadFile(ServerType serverType, AuthenticationToken token, String id) throws RequestException {
        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            final URL baseUrl = serverType.getBaseUrl();
            String urlGlobal = baseUrl + "/v2/esign/v1/documents/" + id + "/download";

            final HttpGet get = new HttpGet(urlGlobal) {{
                if (token.getTokenType() == TokenType.BEARER) {
                    addHeader("Authorization", "Bearer " + token.getAccessToken());
                }
            }};

            final HttpResponse response = httpClient.execute(get);

            return response.getEntity().getContent();

        } catch (IOException | JSONException e) {
            throw new RequestException(e.getMessage(), e);
        }
    }
}
