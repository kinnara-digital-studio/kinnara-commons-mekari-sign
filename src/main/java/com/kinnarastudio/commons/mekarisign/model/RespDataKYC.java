package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;

public class RespDataKYC {
    private final String id;
    private final String type;
    private final RespAttributesKYC attributesKYC;

    public RespDataKYC(JSONObject fromJson) throws ParseException {
        id = fromJson.getString("id");
        type = fromJson.getString("type");
        attributesKYC = new RespAttributesKYC(fromJson.getJSONObject("attributes"));
    }

    public RespDataKYC(String id, String type, RespAttributesKYC attributesKYC) {
        this.id = id;
        this.type = type;
        this.attributesKYC = attributesKYC;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public RespAttributesKYC getAttributesKYC() {
        return attributesKYC;
    }
}
