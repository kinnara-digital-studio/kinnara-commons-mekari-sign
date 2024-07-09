package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

public class ReqKYC {
    private final String email;
    private final String callbackUrl;
    private final boolean sendEmail;

    public ReqKYC(String email, String callbackUrl, boolean sendEmail) {
        this.email = email;
        this.callbackUrl = callbackUrl;
        this.sendEmail = sendEmail;
    }


    public String getEmail() {
        return email;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public JSONObject toJson() {
        return new JSONObject() {{
            put("email", email);
            put("callback_url", callbackUrl);
            put("send_email", sendEmail);
        }};
    }
}
