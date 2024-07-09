package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignRequest {
    private final String doc;
    private final String filename;
    private final RequestSigner[] signers;
    private final boolean signingOrder;
    private final String callbackUrl;

    public SignRequest(String filename, String doc, RequestSigner signer) {
        this(filename, doc, new RequestSigner[]{signer});
    }

    public SignRequest(String filename, String doc, RequestSigner[] signers) {
        this(filename, doc, signers, false, "");
    }

    public SignRequest(String filename, String doc, RequestSigner[] signers, boolean signingOrder, String callbackUrl) {
        this.filename = filename;
        this.doc = doc;
        this.signers = signers;
        this.signingOrder = signingOrder;
        this.callbackUrl = callbackUrl;
    }

    public String getFilename() {
        return filename;
    }

    public String getDoc() {
        return doc;
    }

    public RequestSigner[] getSigners() {
        return signers;
    }

    public boolean isSigningOrder() {
        return signingOrder;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public JSONObject toJson() {
        return new JSONObject() {{
            put("filename", filename);
            put("doc", doc);

            if(signers != null) {
                put("signers", new JSONArray() {{
                    for (RequestSigner signer : signers) {
                        put(signer.toJson());
                    }
                }});
            }

            put("signing_order", signingOrder);
            put("callback_url", callbackUrl);
        }};
    }
}
