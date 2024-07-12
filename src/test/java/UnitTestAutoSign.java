import com.kinnarastudio.commons.mekarisign.MekariSign;
import com.kinnarastudio.commons.mekarisign.exception.BuildingException;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.ReqAutoSign;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

public class UnitTestAutoSign {
    @Test
    public void requestAutoSign() throws RequestException {
        try(final InputStream is = getClass().getResourceAsStream("/properties/secret.properties")) {

            final Properties properties = new Properties() {{
                load(is);
            }};

            final String clientId = properties.getProperty("clientId");
            final String clientSecret = properties.getProperty("clientSecret");
            final String code = properties.getProperty("secretCode");
            final String username = properties.getProperty("username");
            final String password = properties.getProperty("password");

            final String[] docMakerEmails = {"aristo.hadisoeganda@kinnarastudio.com"};
            final String[] signerEmails = {"aristo.hadisoeganda@kinnarastudio.com"};
            final ReqAutoSign req = new ReqAutoSign(docMakerEmails, signerEmails);

            final MekariSign mekariSign = MekariSign.getBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setServerType(ServerType.SANDBOX)
                    .setSecretCode(code)
                    .authenticateAndBuild();

            mekariSign.autoSign(req);
        } catch (IOException | BuildingException e) {
            throw new RuntimeException(e);
        }
    }
}
