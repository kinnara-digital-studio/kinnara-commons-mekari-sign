package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;

public class ResponseData {
    private final SignResponseAttributes attributes;

    public ResponseData(JSONObject fromJson) throws ParseException {
        attributes = new SignResponseAttributes(fromJson.getJSONObject("attributes"));
    }

    public ResponseData(SignResponseAttributes attributes) {
        this.attributes = attributes;
    }

    public SignResponseAttributes getAttributes() {
        return attributes;
    }
}
