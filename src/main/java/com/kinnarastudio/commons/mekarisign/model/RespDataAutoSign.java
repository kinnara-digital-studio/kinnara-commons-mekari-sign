package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;

public class RespDataAutoSign {
    private final String id;
    private final String type;
    private final AutoSignResponseAttributes attributes;

    public RespDataAutoSign(JSONObject fromJson) throws ParseException {
        id = fromJson.getString("id");
        type = fromJson.getString("type");
        attributes = new AutoSignResponseAttributes(fromJson.getJSONObject("attributes"));
    }

    public RespDataAutoSign(String id, String type, AutoSignResponseAttributes attributes) {
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

    public AutoSignResponseAttributes getAttributes() {
        return attributes;
    }
}
