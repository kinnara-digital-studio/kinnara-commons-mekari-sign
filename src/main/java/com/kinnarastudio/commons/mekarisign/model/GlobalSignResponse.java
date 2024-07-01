package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.text.ParseException;

public class GlobalSignResponse {
    private final ResponseData data;

    public GlobalSignResponse(JSONObject fromJson) throws ParseException {
        data = new ResponseData(fromJson.getJSONObject("data"));
    }

    public GlobalSignResponse(ResponseData data){
        this.data = data;
    }

    public ResponseData getData(){return data;}
}
