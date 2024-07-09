package com.kinnarastudio.commons.mekarisign.model;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import org.json.JSONObject;

public class ProfileAttributes {
    private final String id;
    private final String fullName;
    private final String email;
    private final Date createdAt;
    private final Date updatedAt;
    private final ProfileBalance balance;
    private final ProfileSubscriptionStatus subscription;

    public ProfileAttributes (JSONObject fromJson) throws ParseException {
        id = fromJson.getString("id");
        fullName = fromJson.getString("full_name");
        email = fromJson.getString("email");
        createdAt = Date.from(Instant.parse(fromJson.getString("created_at")));
        updatedAt = Date.from(Instant.parse(fromJson.getString("updated_at")));
        balance = new ProfileBalance(fromJson.getJSONObject("balance"));
        subscription = new ProfileSubscriptionStatus(fromJson.getJSONObject("subscription"));
    }

    public ProfileAttributes (String id, String fullName, String email, Date createdAt, Date updatedAt, ProfileBalance balance, ProfileSubscriptionStatus subscription)
    {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.balance = balance;
        this.subscription = subscription;
    }
}
