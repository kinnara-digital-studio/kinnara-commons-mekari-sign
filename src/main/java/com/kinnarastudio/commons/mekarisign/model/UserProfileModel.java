package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

import org.json.JSONObject;

public class UserProfileModel {
    private final ProfileData data;

    public UserProfileModel(JSONObject fromJson) throws ParseException {
        data = new ProfileData(fromJson.getJSONObject("data"));
    }

    public UserProfileModel(ProfileData data)
    {
        this.data = data;
    }

    public ProfileData getData()
    {
        return data;
    }
}
