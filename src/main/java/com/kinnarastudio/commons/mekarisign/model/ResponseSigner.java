package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;

public class ResponseSigner {
    private final String name;
    private final String email;
    private final int order;
    private final SignerStatus status;
    private final String signingAt;
    private final String signingUrl;

    public ResponseSigner(JSONObject fromJson) throws ParseException {
        name = fromJson.getString("name");
        email = fromJson.getString("email");
        order = fromJson.getInt("order");
        status = SignerStatus.parse(fromJson.getString("status"));
        signingAt = fromJson.optString("signing_at");
        signingUrl = fromJson.optString("signing_url");
    }

    public ResponseSigner(String name, String email, int order, SignerStatus status, String signingAt, String signingUrl) {
        this.name = name;
        this.email = email;
        this.order = order;
        this.status = status;
        this.signingAt = signingAt;
        this.signingUrl = signingUrl;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public SignerStatus getStatus() {
        return status;
    }

    public String getSignStatus() {
        return signingUrl;
    }

    public int getOrder() {
        return order;
    }

    public String getSigningAt() {
        return signingAt;
    }
}
