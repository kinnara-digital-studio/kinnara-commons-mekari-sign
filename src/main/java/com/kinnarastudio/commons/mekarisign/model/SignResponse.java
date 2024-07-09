package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;

public class SignResponse {
    private final ResponseData data;

    public SignResponse(JSONObject fromJson) throws ParseException {
        data = new ResponseData(fromJson.getJSONObject("data"));
    }

    public SignResponse(ResponseData data){
        this.data = data;
    }

    public ResponseData getData(){return data;}
}
