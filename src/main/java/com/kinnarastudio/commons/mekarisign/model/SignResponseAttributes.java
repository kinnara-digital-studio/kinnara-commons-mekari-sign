package com.kinnarastudio.commons.mekarisign.model;

import com.kinnarastudio.commons.Try;
import com.kinnarastudio.commons.jsonstream.JSONStream;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SignResponseAttributes {
    private final String filename;
    private final AttributeCategory category;
    private final String docUrl;
    private final SigningStatus signingStatus;
    private final String stampingStatus;
    private final String typeOfMaterai;
    private final ResponseSigner[] responseSigner;
    private final Date createdAt;
    private final Date updatedAt;

    public SignResponseAttributes(String filename, AttributeCategory category, String docUrl, SigningStatus signingStatus, String stampingStatus, String typeOfMaterai, ResponseSigner[] responseSigner, Date createdAt, Date updatedAt, Date createdAt1, Date updatedAt1) {
        this.filename = filename;
        this.category = category;
        this.docUrl = docUrl;
        this.signingStatus = signingStatus;
        this.stampingStatus = stampingStatus;
        this.typeOfMaterai = typeOfMaterai;
        this.responseSigner = responseSigner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public SignResponseAttributes(JSONObject respAttributes) throws ParseException {
        filename = respAttributes.getString("filename");
        if (respAttributes.getString("categories").equals(AttributeCategory.GLOBAL.toString())) {
            category = AttributeCategory.GLOBAL;
        } else {
            category = AttributeCategory.PSRE;
        }
        docUrl = respAttributes.getString("doc_url");
        if (respAttributes.getString("signing_status").equals(SigningStatus.IN_PROGRESS.toString())) {
            signingStatus = SigningStatus.IN_PROGRESS;
        } else if (respAttributes.getString("signing_status").equals(SigningStatus.COMPLETED.toString())) {
            signingStatus = SigningStatus.COMPLETED;
        } else if (respAttributes.getString("signing_status").equals(SigningStatus.DECLINED.toString())) {
            signingStatus = SigningStatus.DECLINED;
        } else {
            signingStatus = SigningStatus.VOIDED;
        }
        stampingStatus = respAttributes.getString("stamping_status");
        typeOfMaterai = respAttributes.getString("type_of_materai");
        JSONArray getRespSigners = respAttributes.getJSONArray("signers");
        responseSigner = new ResponseSigner[getRespSigners.length()];
        for (int i = 0; i < getRespSigners.length(); i++) {
            JSONObject sign = getRespSigners.getJSONObject(i);
            ResponseSigner resp = new ResponseSigner(sign);
            responseSigner[i] = resp;
        }

        //Mengambil dan mengonversi createdAt dan updateAt dari JSON
        createdAt = Date.from(Instant.parse(respAttributes.getString("created_at")));
        updatedAt = Date.from(Instant.parse(respAttributes.getString("update_at")));
    }

    public String getFilename() {
        return filename;
    }

    public AttributeCategory getCategory() {
        return category;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public SigningStatus getSigningStatus() {
        return signingStatus;
    }

    public String getStampingStatus() {
        return stampingStatus;
    }

    public String getTypeOfMaterai() {
        return typeOfMaterai;
    }

    public ResponseSigner[] getResponseSigner() {
        return responseSigner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    //Metode untuk mengonversi Date ke format ISO 8601
    public static String convertDateToISO(Date date) {
        return DateTimeFormatter.ISO_INSTANT.format(date.toInstant());
    }

    //Metode untuk mendapatkan createdAt dalam format ISO 8601
    public String getCreatedAtISO() {
        return convertDateToISO(createdAt);
    }

    //Metode untuk mendapatkan updatedAt dalam format ISO 8601
    public String getUpdatedISO() {
        return convertDateToISO(updatedAt);
    }
}
