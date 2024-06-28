package com.kinnarastudio.commons.mekarisign.service;

import com.kinnarastudio.commons.mekarisign.MekariSign;
import com.kinnarastudio.commons.mekarisign.exception.BuildingException;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationRequest;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.GrantType;
import com.kinnarastudio.commons.mekarisign.model.ServerType;

public class Builder  {
    private String clientId;
    private String clientSecret;
    private String code;
    private ServerType serverType = ServerType.PRODUCTION;

    public Builder setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public Builder setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public Builder setSecretCode(String code) {
        this.code = code;
        return this;
    }

    public Builder setServerType(ServerType serverType) {
        this.serverType = serverType;
        return this;
    }

    /**
     * Call <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#authentication">authentication</a> request and keep token
     *
     * @return
     */
    public MekariSign build() throws BuildingException {
        try {
            assertNotEmpty(clientId);
            assertNotEmpty(clientSecret);
            assertNotEmpty(serverType);
            assertNotEmpty(code);

            final Authenticator authenticator = Authenticator.getInstance();

            final AuthenticationRequest authentication = new AuthenticationRequest(clientId, clientSecret, GrantType.AUTHORIZATION_CODE, code);
            final AuthenticationToken authenticationToken = authenticator.authenticate(serverType, authentication);

            return new MekariSign(serverType, authenticationToken);
        } catch (RequestException e) {
            throw new BuildingException(e);
        }
    }

    protected void assertNotEmpty(Object value) throws BuildingException {
        if(value == null || String.valueOf(value).isEmpty()) throw new BuildingException("Field has not been set");
    }
}
