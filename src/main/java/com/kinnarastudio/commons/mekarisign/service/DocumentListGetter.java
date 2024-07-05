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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.DocumentCategory;
import com.kinnarastudio.commons.mekarisign.model.DocumentListResponse;
import com.kinnarastudio.commons.mekarisign.model.GetDocumentListBody;
import com.kinnarastudio.commons.mekarisign.model.ResponseData;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.model.SignResponseAttributes;
import com.kinnarastudio.commons.mekarisign.model.SignerStatus;
import com.kinnarastudio.commons.mekarisign.model.SigningStatus;
import com.kinnarastudio.commons.mekarisign.model.StampingStatus;
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

    public GetDocumentListBody requestDocs(ServerType serverType, AuthenticationToken token, int page, int limit, DocumentCategory category, SigningStatus status, StampingStatus stamping) throws RequestException, ParseException
    {
        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) 
        {

            final URL baseUrl = serverType.getBaseUrl();
            String urlGlobal = baseUrl + "/v2/esign/v1/documents?page="+ page +"&limit="+ limit;

            System.out.println("Status: " + status);
            if (category != null)
            {
                urlGlobal += "&category="+ category;
            }

            if (status != null)
            {
                urlGlobal += "&signing_status="+ status;
            }

            if (stamping != null)
            {
                urlGlobal += "&stamping_status=" + stamping;
            }

            final HttpGet get = new HttpGet(urlGlobal) {{
                if (token.getTokenType() == TokenType.BEARER) {
                    addHeader("Authorization", "Bearer " + token.getAccessToken());
                }
            }};

            final HttpResponse response = httpClient.execute(get);

            try (final Reader reader = new InputStreamReader(response.getEntity().getContent());
                 final BufferedReader bufferedReader = new BufferedReader(reader)) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());

                System.out.println(responsePayload);

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RequestException("HTTP response code [" + statusCode + "] response [" + responsePayload + "]");
                }

                final JSONObject jsonRespArray = new JSONObject(responsePayload);
                final DocumentListResponse documentListResponse = new DocumentListResponse(jsonRespArray);
                return documentListResponse.getData();
            }
        } catch (IOException | JSONException e) {
            throw new RequestException("Error authenticating : " + e.getMessage(), e);
        }
    }
}
