package com.kinnarastudio.commons.mekarisign.service;

import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.GlobalSignRequest;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.Base64;
import java.util.logging.Logger;

public class GlobalSigner {
    public void requestSign(ServerType serverType, AuthenticationToken token, GlobalSignRequest globalSignRequest) {
        final File pdfFile = globalSignRequest.getFile();
        final String filename = pdfFile.getName();

        try(final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            final FileInputStream fis = new FileInputStream(pdfFile)) {

            final StringBuilder sb = new StringBuilder();
            final Base64.Encoder encoder = Base64.getEncoder();

            int len = Math.toIntExact(pdfFile.length());
            final byte[] buffer = new byte[len];

            while(fis.read(buffer) > 0) {
                final String base64encoded = encoder.encodeToString(buffer);
                sb.append(base64encoded);
            }

            final String encodedFile = sb.toString();

            // TODO: connect to Mekari Sign server
            // TODO: Andhela
        } catch (IOException e) {

        }

    }
}
