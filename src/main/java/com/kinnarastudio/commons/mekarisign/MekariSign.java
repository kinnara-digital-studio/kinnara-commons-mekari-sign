package com.kinnarastudio.commons.mekarisign;

import com.kinnarastudio.commons.mekarisign.exception.BuildingException;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.Authentication;
import com.kinnarastudio.commons.mekarisign.model.AuthenticationToken;
import com.kinnarastudio.commons.mekarisign.model.GrantType;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import com.kinnarastudio.commons.mekarisign.service.Authenticator;

import java.io.File;

public class MekariSign {
    private final ServerType serverType;
    private final AuthenticationToken authenticationToken;

    private MekariSign(ServerType serverType, AuthenticationToken authenticationToken) {
        this.serverType = serverType;
        this.authenticationToken = authenticationToken;
    }

    public void globalSign(File file) {

    }

    public void digistamp(File file) {

    }

    public final static class Builder {

        private String clientId;

        private String clientSecret;

        private String code;

        private ServerType serverType = ServerType.PRODUCTION;

        private Builder() {
        }

        public static MekariSign.Builder getInstance() {
            return new MekariSign.Builder();
        }

        public MekariSign.Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public MekariSign.Builder setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public MekariSign.Builder setServerType(ServerType serverType) {
            this.serverType = serverType;
            return this;
        }

        /**
         * Call <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#authentication">authentication</a> request and keep token
         *
         * @return
         */
        public MekariSign buildAndAuthenticate() throws BuildingException {
            try {
                final Authenticator authenticator = Authenticator.getInstance();
                final Authentication authentication = new Authentication(clientId, clientSecret, GrantType.AUTHORIZATION_CODE, code);
                final AuthenticationToken token = authenticator.authenticate(serverType, authentication);

                return new MekariSign(serverType, token);
            } catch (RequestException e) {
                throw new BuildingException(e);
            }
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }
    }
}
