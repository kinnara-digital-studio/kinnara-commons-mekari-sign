package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;

public class RespKYC {
    private final RespDataKYC dataKYC;

    public RespKYC(RespDataKYC dataKYC) {
        this.dataKYC = dataKYC;
    }

    public RespKYC(JSONObject kycResp) throws ParseException {
        dataKYC = new RespDataKYC(kycResp.getJSONObject("data"));
    }

    public RespDataKYC getDataKYC() {
        return dataKYC;
    }
}
