package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;

import org.json.JSONObject;

public class ProfileBalance {
    private final int remainingEmeteraiBalance;
    private final int eMeteraiUsage;
    private final int globalSignDocument;
    private final int psreSigning;
    private final int eKYCQuota;

    public ProfileBalance (JSONObject fromJson) throws ParseException {
        remainingEmeteraiBalance = fromJson.getInt("remaining_emeterai_balance");
        eMeteraiUsage = fromJson.getInt("emeterai_usage");
        globalSignDocument = fromJson.getInt("global_sign_document");
        psreSigning = fromJson.getInt("psre_signing");
        eKYCQuota = fromJson.getInt("ekyc_quota");
    }

    public ProfileBalance (int remainingEmeteraiBalance, int eMeteraiUsage, int globalSignDocument, int psreSigning, int eKYCQuota)
    {
        this.remainingEmeteraiBalance = remainingEmeteraiBalance;
        this.eMeteraiUsage = eMeteraiUsage;
        this.globalSignDocument = globalSignDocument;
        this.psreSigning = psreSigning;
        this.eKYCQuota = eKYCQuota;
    }
}
