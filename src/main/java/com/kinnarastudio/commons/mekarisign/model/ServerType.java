package com.kinnarastudio.commons.mekarisign.model;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#environments">Environments</a>
 */
public enum ServerType {
    PRODUCTION("https://api.mekari.com/v2/esign/v1", "https://account.mekari.com"),
    SANDBOX("https://sandbox-app-sign.mekari.io", "https://sandbox-account.mekari.com"),
    STAGING("https://api.mekari.io/v2/esign/v1", "https://account.mekari.io"),
    MOCK("https://58fdbadf-ae2d-4965-b892-10aa4ca64ebd.mock.pstmn.io", "https://account.mekari.io");

    private final URL baseUrl;
    private final URL ssoBaseUrl;

    ServerType(String baseUrl, String ssoBaseUrl) {
        try {
            this.baseUrl = new URL(baseUrl);
            this.ssoBaseUrl = new URL(ssoBaseUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    public URL getBaseUrl() {
        return baseUrl;
    }

    public URL getSsoBaseUrl() {
        return ssoBaseUrl;
    }
}
