package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class ReqAutoSign {
    private final String[] docMakerEmails;
    private final String[] signerEmails;

    public ReqAutoSign(String[] docMakerEmails, String[] signerEmails) {
        this.docMakerEmails = docMakerEmails;
        this.signerEmails = signerEmails;
    }

    public String[] getDocMakerEmails() {
        return docMakerEmails;
    }

    public String[] getSignerEmails() {
        return signerEmails;
    }

    public JSONObject toJson() {
        return new JSONObject() {{
            if (docMakerEmails != null) {
                put("doc_maker_emails", new JSONArray(Arrays.asList(docMakerEmails)));
            }
            if (signerEmails != null) {
                put("signer_emails", new JSONArray(Arrays.asList(signerEmails)));
            }
        }};
    }
}
