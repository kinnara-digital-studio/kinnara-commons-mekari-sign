package com.kinnarastudio.commons.mekarisign.service;

import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.AutoSignResponse;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.model.TokenType;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class DeleteAutoSign {
    private static DeleteAutoSign instance = null;

    private DeleteAutoSign() {
    }

    public static DeleteAutoSign getInstance() {
        if (instance == null) {
            instance = new DeleteAutoSign();
        }
        return instance;
    }

    public AutoSignResponse deleteAutoSign(ServerType serverType, AuthenticationToken token, String autoSignId) throws RequestException, IOException {
        try (final CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            final URL baseUrl = serverType.getBaseUrl();
            final String delAutoSign = baseUrl + "/v2/esign/v1/auto_sign/" + autoSignId;

            final HttpDelete delete = new HttpDelete(delAutoSign);
            if (token.getTokenType() == TokenType.BEARER) {
                delete.addHeader("Authorization", "Bearer " + token.getAccessToken());
            }

            // Membuat format tanggal sesuai dengan standar HTTP
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            String httpDate = dateFormat.format(Calendar.getInstance().getTime());
            delete.addHeader("Date", httpDate);

            final HttpResponse response = httpClient.execute(delete);

            try (final Reader reader = new InputStreamReader(response.getEntity().getContent());
                 final BufferedReader bufferedReader = new BufferedReader(reader)) {

                final String responsePayload = bufferedReader.lines().collect(Collectors.joining());
                System.out.println(responsePayload);

                final int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RequestException("HTTP response code [" + statusCode + "] response [" + responsePayload + "]");
                }

                final JSONObject jsonResponsePayload = new JSONObject(responsePayload);
                return new AutoSignResponse(jsonResponsePayload);
            }
        } catch (IOException | ParseException e) {
            throw new RequestException(e.getMessage(), e);
        }
    }
}
