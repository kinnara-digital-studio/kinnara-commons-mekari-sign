package com.kinnarastudio.commons.mekarisign.model;

import com.kinnarastudio.commons.Try;
import com.kinnarastudio.commons.jsonstream.JSONStream;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

public class SignResponseAttributes {
    private final String filename;
    private final AttributeCategory category;
    private final String docUrl;
    private final SigningStatus signingStatus;
    private final String stampingStatus;
    private final String typeOfMaterai;
    private final ResponseSigner[] responseSigner;
    private final Date createdAt = new Date();
    private final Date updatedAt = new Date();

    public SignResponseAttributes(String filename, AttributeCategory category, String docUrl, SigningStatus signingStatus, String stampingStatus, String typeOfMaterai, ResponseSigner[] responseSigner, Date createdAt, Date updatedAt) {
        this.filename = filename;
        this.category = category;
        this.docUrl = docUrl;
        this.signingStatus = signingStatus;
        this.stampingStatus = stampingStatus;
        this.typeOfMaterai = typeOfMaterai;
        this.responseSigner = responseSigner;
    }

    public SignResponseAttributes(JSONObject fromJson) throws ParseException {
        filename = fromJson.getString("filename");
        category = AttributeCategory.parse(fromJson.getString("category"));
        docUrl = fromJson.getString("doc_url");
        signingStatus = SigningStatus.parse(fromJson.getString("signing_status"));
        stampingStatus = fromJson.getString("stamping_status");
        typeOfMaterai = fromJson.optString("type_of_materai");
        responseSigner = JSONStream.of(fromJson.getJSONArray("signers"), JSONArray::getJSONObject)
                .map(Try.onFunction(ResponseSigner::new))
                .toArray(ResponseSigner[]::new);
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
}
