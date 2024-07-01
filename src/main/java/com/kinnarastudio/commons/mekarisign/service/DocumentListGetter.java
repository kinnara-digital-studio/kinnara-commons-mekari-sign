package com.kinnarastudio.commons.mekarisign.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.ParseException;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.GetDocumentListsResponse;
import com.kinnarastudio.commons.mekarisign.model.ResponseData;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.model.TokenType;

public class DocumentListGetter {
    private static DocumentListGetter instance = null;
    
    private DocumentListGetter() {
    }

    public static DocumentListGetter getInstance() {
        if (instance == null) {
            instance = new DocumentListGetter();
        }

        return instance;
    }

    public GetDocumentListsResponse requestDocs(ServerType serverType, AuthenticationToken token) throws RequestException
    {
        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) 
        {

            final URL baseUrl = serverType.getBaseUrl();
            final String urlGlobal = baseUrl + "/v2/esign/v1/documents?page=1&limit=8&category=global&signing_status=completed&stamping_status=none";

            final HttpGet get = new HttpGet(urlGlobal) {{
                if (token.getTokenType() == TokenType.BEARER) {
                    addHeader("Authorization", "Bearer " + token.getAccessToken());
                }
            }};

            final HttpResponse response = httpClient.execute(get);



            // try (final Reader reader = new InputStreamReader(response.getEntity().getContent());
            //      final BufferedReader bufferedReader = new BufferedReader(reader)) {

            //     final String responsePayload = bufferedReader.lines().collect(Collectors.joining());

            //     final int statusCode = response.getStatusLine().getStatusCode();
            //     if (statusCode != 200) {
            //         throw new RequestException("HTTP response code [" + statusCode + "] response [" + responsePayload + "]");
            //     }

            //     final JSONObject jsonResponsePayload = new JSONObject(responsePayload);
            //     final ResponseData globalSignResponse = new ResponseData(jsonResponsePayload);
            //     return globalSignResponse.getAttributes();
            // }
            return null;
        } catch (IOException | JSONException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }
}
