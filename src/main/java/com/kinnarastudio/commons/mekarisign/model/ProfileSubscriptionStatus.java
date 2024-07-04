package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import org.json.JSONObject;

public class ProfileSubscriptionStatus {
    private final String plan;
    private final String status;
    private final Date expiredAt;

    public ProfileSubscriptionStatus (JSONObject fromJson) throws ParseException {
        plan = fromJson.getString("plan");
        status = fromJson.getString("status");
        expiredAt = Date.from(Instant.parse(fromJson.getString("expired_at")));
    }

    public ProfileSubscriptionStatus (String plan, String status, Date expiredAt)
    {
        this.plan = plan;
        this.status = status;
        this.expiredAt = expiredAt;
    }
}
