package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONObject;

public class ResponseSigner {
    private final String name;
    private final String email;
    private final int order;
    private final SignerStatus status;
    private final String signingUrl;

    public ResponseSigner(JSONObject jsonRes){
        name = jsonRes.getString("name");
        email = jsonRes.getString("email");
        order = jsonRes.getInt("order");
        if(jsonRes.getString("status").equals("your_turn")){
            status = SignerStatus.YOUR_TURN;
        }
        else {
            status = SignerStatus.AWAITING;
        }
        signingUrl = jsonRes.getString("signing_url");

    }
    public ResponseSigner(String name, String email, int order, SignerStatus status, String signingUrl) {
        this.name = name;
        this.email = email;
        this.order = order;
        this.status = status;
        this.signingUrl = signingUrl;
    }

    public String getName(){return name;}

    public String getEmail(){return email;}

    public SignerStatus getStatus(){return status;}

    public String getSignStatus(){return signingUrl;}


}
