package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;

public class AutoSignResponse {
    private final RespDataAutoSign[] dataAutoSign;

    public AutoSignResponse(RespDataAutoSign[] dataAutoSign) {
        this.dataAutoSign = dataAutoSign;
    }

    public AutoSignResponse(JSONObject autoResp) throws ParseException {
        JSONArray getRespData = autoResp.getJSONArray("data");
        dataAutoSign = new RespDataAutoSign[getRespData.length()];
        for (int i = 0; i < getRespData.length(); i++){
            JSONObject data = getRespData.getJSONObject(i);
            RespDataAutoSign respData = new RespDataAutoSign(data);
            dataAutoSign[i] = respData;
        }
    }

    public RespDataAutoSign[] getRespData(){ return dataAutoSign; }
}
