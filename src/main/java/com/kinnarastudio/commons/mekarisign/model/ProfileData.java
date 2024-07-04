package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import org.json.JSONObject;

public class ProfileData {
    private final String id;
    private final String type;
    private final ProfileAttributes attributes;

    public ProfileData (JSONObject fromJson) throws ParseException {
        id = fromJson.getString("id");
        type = fromJson.getString("type");
        attributes = new ProfileAttributes(fromJson.getJSONObject("attributes"));
    }

    public ProfileData (String id, String type, ProfileAttributes attributes) {
        this.id = id;
        this.type = type;
        this.attributes = attributes;
    }
}
