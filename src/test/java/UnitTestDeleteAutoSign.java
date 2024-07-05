import com.kinnarastudio.commons.mekarisign.MekariSign;
import com.kinnarastudio.commons.mekarisign.exception.BuildingException;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import org.junit.Test;

import java.io.IOException;

import java.io.InputStream;
import java.util.Properties;

public class UnitTestDeleteAutoSign {
    @Test
    public void reqDeleteAuto() throws RequestException {
        try (final InputStream is = getClass().getResourceAsStream("/properties/secret.properties")) {
            final Properties properties = new Properties() {{
                load(is);
            }};

            final String clientId = properties.getProperty("clientId");
            final String clientSecret = properties.getProperty("clientSecret");
            final String code = properties.getProperty("secretCode");
            final String username = properties.getProperty("username");
            final String password = properties.getProperty("password");

            final String id = "0641041a-bb38-4c1e-86cc-512012befadd";
            final MekariSign mekariSign = MekariSign.getBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setServerType(ServerType.SANDBOX)
                    .setSecretCode(code)
                    .build();

            mekariSign.deleteAutoSign(id);
        } catch (IOException | BuildingException e) {
            throw new RuntimeException(e);
        }
    }
}
