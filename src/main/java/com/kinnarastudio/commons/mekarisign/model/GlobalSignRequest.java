package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class GlobalSignRequest {
    private final String doc;
    private final String filename;
    private final RequestSigner[] signers;
    private final boolean signingOrder;
    private final String callbackUrl;

    public GlobalSignRequest(String filename, String doc, RequestSigner signer) {
        this(filename, doc, new RequestSigner[]{signer});
    }

    public GlobalSignRequest(String filename, String doc, RequestSigner[] signers) {
        this(filename, doc, signers, false, "");
    }

    public GlobalSignRequest(String filename, String doc, RequestSigner[] signers, boolean signingOrder, String callbackUrl) {
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

            final JSONArray jsonSigner = new JSONArray() {{
                for (RequestSigner signer : signers) {
                    put(signer.toJson());
                }
            }};
            put("signers", jsonSigner);

            put("signing_order", signingOrder);
            put("callback_url", callbackUrl);
        }};
    }
}
