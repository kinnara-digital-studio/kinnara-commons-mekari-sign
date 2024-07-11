package com.kinnarastudio.commons.mekarisign.service;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.util.stream.Collectors;

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
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.model.SigningStatus;
import com.kinnarastudio.commons.mekarisign.model.StampingStatus;
import com.kinnarastudio.commons.mekarisign.model.TokenType;

public class DocumentListService {
    private static DocumentListService instance = null;

    private DocumentListService() {
    }

    public static DocumentListService getInstance() {
        if (instance == null) {
            instance = new DocumentListService();
        }

        return instance;
    }

    public GetDocumentListBody requestDocs(ServerType serverType, AuthenticationToken token, int page, int limit, DocumentCategory category, SigningStatus status, StampingStatus stamping) throws RequestException, ParseException {
        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

            final URL baseUrl = serverType.getApiBaseUrl();
            String urlGlobal = String.format("%s/v%d/esign/v%d/documents?page=%d&limit=%d" , baseUrl, serverType.getApiVersion(), serverType.getEsignVersion(), page, limit);

            if (category != null) {
                urlGlobal += "&category=" + category;
            }

            if (status != null) {
                urlGlobal += "&signing_status=" + status;
            }

            if (stamping != null) {
                urlGlobal += "&stamping_status=" + stamping;
            }

            final HttpGet get = new HttpGet(urlGlobal) {{
                if (token.getTokenType() == TokenType.BEARER) {
                    addHeader("Authorization", "Bearer " + token.getAccessToken());
                }
            }};

            final HttpResponse response = httpClient.execute(get);

            try (final InputStream is = response.getEntity().getContent();
                 final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RequestException("HTTP response code [" + statusCode + "] response [" + responsePayload + "]");
                }

                final JSONObject jsonRespArray = new JSONObject(responsePayload);
                final DocumentListResponse documentListResponse = new DocumentListResponse(jsonRespArray);
                return documentListResponse.getData();
            }
        } catch (IOException | JSONException e) {
            throw new RequestException(e);
        }
    }
}
