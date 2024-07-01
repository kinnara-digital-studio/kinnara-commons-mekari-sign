package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.io.File;

public class GlobalSignResponse {
    private final String id;
    private final String type;
    private final SignResponseAttributes attributes;

    public GlobalSignResponse(String id, String type, SignResponseAttributes attributes) {
        this.id = id;
        this.type = type;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public SignResponseAttributes getAttributes() {
        return attributes;
    }

    public GlobalSignResponse(JSONObject signResp){
        id = signResp.getString("id");
        type = signResp.getString("type");
        // Menginisialisasi objek SignResponseAttributes dari JSONObject
        JSONObject attributesObject = signResp.getJSONObject("attributes");
        attributes = new SignResponseAttributes(attributesObject);
    }
}
