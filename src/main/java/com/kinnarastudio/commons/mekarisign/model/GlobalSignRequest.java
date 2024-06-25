package com.kinnarastudio.commons.mekarisign.model;

import java.io.File;

public class GlobalSignRequest {
    private final File file;
    private final Signer[] signers;
    private final boolean signingOrder;
    private final String callbackUrl;

    public GlobalSignRequest(File file, Signer signer) {
        this(file, new Signer[]{signer});
    }

    public GlobalSignRequest(File file, Signer[] signers) {
        this(file, signers, false, "");
    }

    public GlobalSignRequest(File file, Signer[] signers, boolean signingOrder, String callbackUrl) {
        this.file = file;
        this.signers = signers;
        this.signingOrder = signingOrder;
        this.callbackUrl = callbackUrl;
    }

    public File getFile() {
        return file;
    }

    public Signer[] getSigners() {
        return signers;
    }

    public boolean isSigningOrder() {
        return signingOrder;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }
}
