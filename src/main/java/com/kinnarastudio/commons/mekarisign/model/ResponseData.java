package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;

public class ResponseData {
    private final String id;
    private final String type;
    private final SignResponseAttributes attributes;

    public ResponseData(JSONObject fromJson) throws ParseException {
        id = fromJson.getString("id");
        type = fromJson.getString("type");
        attributes = new SignResponseAttributes(fromJson.getJSONObject("attributes"));
    }

    public ResponseData(String id, String type, SignResponseAttributes attributes) {
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
}
