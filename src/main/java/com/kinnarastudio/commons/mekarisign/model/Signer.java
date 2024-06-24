package com.kinnarastudio.commons.mekarisign.model;

public class Signer {
    private final String name;
    private final String email;
    private final Annotation[] annotations;

    public Signer(String name, String email, Annotation[] annotations) {
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
}
