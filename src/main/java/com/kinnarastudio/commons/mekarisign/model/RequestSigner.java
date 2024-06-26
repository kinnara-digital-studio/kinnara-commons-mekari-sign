package com.kinnarastudio.commons.mekarisign.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestSigner {
    private final String name;
    private final String email;
    private final Annotation[] annotations;

    public RequestSigner(String name, String email, Annotation annotation) {
        this(name, email, new Annotation[]{annotation});
    }

    public RequestSigner(String name, String email, Annotation[] annotations) {
        this.name = name;
        this.email = email;
        this.annotations = annotations;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Annotation[] getAnnotations() {
        return annotations.clone();
    }

    public JSONObject toJson() {
        final JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("email", email);

        if(annotations != null) {
            final JSONArray jsonAnnotations = new JSONArray();
            for (Annotation annotation : annotations) {
                jsonAnnotations.put(annotation.toJson());
            }
            json.put("annontations", jsonAnnotations);
        }
        return json;
    }
}
