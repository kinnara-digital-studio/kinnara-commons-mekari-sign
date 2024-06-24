package com.kinnarastudio.commons.mekarisign.service;

import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.model.Signer;

import java.io.*;
import java.util.Base64;

public class GlobalSigner {
    public void sign(ServerType serverType, AuthenticationToken token, File pdfFile, Signer[] signers) {
        final String filename = pdfFile.getName();

        try(final FileInputStream fis = new FileInputStream(pdfFile)) {

            final StringBuilder sb = new StringBuilder();
            int len = Math.toIntExact(pdfFile.length());
            byte[] buffer = new byte[len];
            while(fis.read(buffer) > 0) {
                final String base64encoded = Base64.getEncoder().encodeToString(buffer);
                sb.append(base64encoded);
            }
            final String encodedFile = sb.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
