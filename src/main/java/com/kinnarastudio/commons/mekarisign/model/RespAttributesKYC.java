package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RespAttributesKYC {
    private final String email;
    private final String status;
    private final String tilakaStatus;
    private final String tilakaName;
    private final Date createdAt;

    public RespAttributesKYC(String email, String status, String tilakaStatus, String tilakaName, Date createdAt) {
        this.email = email;
        this.status = status;
        this.tilakaStatus = tilakaStatus;
        this.tilakaName = tilakaName;
        this.createdAt = createdAt;
    }

    public RespAttributesKYC(JSONObject respAttributesKYC) throws ParseException {
        email = respAttributesKYC.getString("email");
        status = respAttributesKYC.getString("status");
        tilakaStatus = respAttributesKYC.optString("tilaka_status", null);
        tilakaName = respAttributesKYC.optString("tilaka_name", null);
        createdAt = Date.from(Instant.parse(respAttributesKYC.getString("created_at")));
    }
    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getTilakaStatus() {
        return tilakaStatus;
    }

    public String getTilakaName() {
        return tilakaName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    //Metode untuk mengonversi Date ke format ISO 8601
    public static String convertDateToISO(Date date) { return DateTimeFormatter.ISO_INSTANT.format(date.toInstant());}

    //Metode untuk mendapatkan createdAt dalam format ISO 8601
    public String getCreatedAtISO(){ return convertDateToISO(createdAt); }
}
