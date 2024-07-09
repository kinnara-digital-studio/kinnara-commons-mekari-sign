package com.kinnarastudio.commons.mekarisign.model;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#environments">Environments</a>
 */
public enum ServerType {
    PRODUCTION("https://api.mekari.com", "https://account.mekari.com", 2, 1),
    SANDBOX("https://sandbox-api.mekari.com", "https://sandbox-account.mekari.com", 2, 1),
    STAGING("https://api.mekari.io", "https://account.mekari.io", 2, 1),
    MOCK("https://58fdbadf-ae2d-4965-b892-10aa4ca64ebd.mock.pstmn.io", "https://account.mekari.io", 2, 1);

    private final URL apiBaseUrl;
    private final URL ssoBaseUrl;
    private final int apiVersion;
    private final int esignVersion;

    ServerType(String apiBaseUrl, String ssoBaseUrl, int apiVersion, int esignVersion) {
        this.apiVersion = apiVersion;
        this.esignVersion = esignVersion;
        try {
            this.apiBaseUrl = new URL(apiBaseUrl);
            this.ssoBaseUrl = new URL(ssoBaseUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    public URL getApiBaseUrl() {
        return apiBaseUrl;
    }

    public URL getSsoBaseUrl() {
        return ssoBaseUrl;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public int getEsignVersion() {
        return esignVersion;
    }
}
