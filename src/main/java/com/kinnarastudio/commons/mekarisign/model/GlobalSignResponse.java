package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.io.File;

public class GlobalSignResponse {
    private final int id;
    private final String type;
    private final File file;
    private final RequestSigner[] signers;

    public GlobalSignResponse(JSONObject json) {
        id = json.getInt("id");
        type = json.getString("type");

    }
    public GlobalSignResponse(int id, String type, File file, RequestSigner[] signers) {
        this.id = id;
        this.type = type;
        this.file = file;
        this.signers = signers;
    }
}
