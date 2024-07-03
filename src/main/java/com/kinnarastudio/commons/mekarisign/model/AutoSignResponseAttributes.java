package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

import java.text.ParseException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AutoSignResponseAttributes {
    private final String docMakerId;
    private final String docMakerEmail;
    private final String signerId;
    private final String signerEmail;
    private final Date approvedAt;
    private final String createdBy;
    private final Date createdAt;
    private final Date updatedAt;
    private final String deletedBY;
    private final Date deletedAt;

    public AutoSignResponseAttributes(String docMakerId, String docMakerEmail, String signerId, String signerEmail, Date approvedAt, String createdBy, Date createdAt, Date updatedAt, String deletedBY, Date deletedAt) {
        this.docMakerId = docMakerId;
        this.docMakerEmail = docMakerEmail;
        this.signerId = signerId;
        this.signerEmail = signerEmail;
        this.approvedAt = approvedAt;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedBY = deletedBY;
        this.deletedAt = deletedAt;
    }

    public AutoSignResponseAttributes(JSONObject respAttributes) throws ParseException {
        this.docMakerId = respAttributes.getString("doc_maker_id");
        this.docMakerEmail = respAttributes.getString("doc_maker_email");
        this.signerId = respAttributes.getString("signer_id");
        this.signerEmail = respAttributes.getString("signer_email");
        this.approvedAt = Date.from(Instant.parse(respAttributes.getString("approved_at")));
        this.createdBy = respAttributes.getString("created_by");
        this.createdAt = Date.from(Instant.parse(respAttributes.getString("createdAt")));
        this.updatedAt = Date.from(Instant.parse(respAttributes.getString("updated_at")));
        this.deletedBY = respAttributes.getString("deleted_by");
        this.deletedAt = Date.from(Instant.parse(respAttributes.getString("deleted_at")));
    }

    public String getDocMakerId() {
        return docMakerId;
    }

    public String getDocMakerEmail() {
        return docMakerEmail;
    }

    public String getSignerId() {
        return signerId;
    }

    public String getSignerEmail() {
        return signerEmail;
    }

    public Date getApprovedAt() {
        return approvedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getDeletedBY() {
        return deletedBY;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    //Metode untuk mengonversi Date ke format ISO 8601
    public static String convertDateToISO(Date date) { return DateTimeFormatter.ISO_INSTANT.format(date.toInstant());}

    //Method untuk mendapatkan approvedAt dalam format ISO 8601
    public String getApprovedAtISO(){ return convertDateToISO(approvedAt);}

    //Metode untuk mendapatkan createdAt dalam format ISO 8601
    public String getCreatedAtISO(){ return convertDateToISO(createdAt); }

    //Metode untuk mendapatkan updatedAt dalam format ISO 8601
    public String getUpdatedAtISO(){ return convertDateToISO(updatedAt); }

    //Method untuk mendapatkan deletedAt dalam format ISO 8601
    public String getDeletedAtISO(){ return convertDateToISO(deletedAt);}
}
