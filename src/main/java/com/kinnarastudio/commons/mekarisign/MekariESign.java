package com.kinnarastudio.commons.mekarisign;

import com.kinnarastudio.commons.mekarisign.exception.BuildingException;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.ServerType;

import java.net.URL;

public class MekariESign {
    private final URL baseUrl;
    private final String clientId;

    private final Authenticator authenticator;

    private MekariESign(URL baseUrl, String clientId, Authenticator authenticator) {
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.authenticator = authenticator;
    }

    public static MekariESign.Builder builder() {
        return MekariESign.Builder.getInstance();
    }

    public static class Builder {

        private String clientId;

        private String clientSecret;

        private String code;

        private ServerType serverType = ServerType.PRODUCTION;

        protected Builder() {
        }

        public static MekariESign.Builder getInstance() {
            return new MekariESign.Builder();
        }

        public MekariESign.Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public MekariESign.Builder setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public MekariESign.Builder setServerType(ServerType serverType) {
            this.serverType = serverType;
            return this;
        }

        /**
         * Call <a href="https://documenter.getpostman.com/view/21582074/2s93K1oecc#authentication">authentication</a> request and keep token
         *
         * @return
         */
        public MekariESign build() throws BuildingException {
            try {
                final Authenticator authenticator = new Authenticator(serverType).authenticate(clientId, clientSecret);
                return new MekariESign(serverType.getBaseUrl(), clientId, authenticator);
            } catch (RequestException e) {
                throw new BuildingException(e);
            }
        }

    }
}
