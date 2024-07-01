import com.kinnarastudio.commons.mekarisign.MekariSign;
import com.kinnarastudio.commons.mekarisign.exception.BuildingException;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

public class UnitTest1 {
    @Test
    public void globalSignerRequestSign() throws RequestException {
        try (final InputStream is = getClass().getResourceAsStream("/properties/secret.properties")) {

            final Properties properties = new Properties() {{
                load(is);
            }};

            final String clientId = properties.getProperty("clientId");
            final String clientSecret = properties.getProperty("clientSecret");
            final String code = properties.getProperty("secretCode");
            final String username = properties.getProperty("username");
            final String password = properties.getProperty("password");

            final Annotation annotation = new Annotation(AnnotationType.SIGNATURE, 1, 25, 50, 10, 20, 100, 100);
            final RequestSigner signer = new RequestSigner("Scooby Doo", username, annotation);
            final File file = Optional.ofNullable(getClass().getResource("/resources/testing_doc.pdf"))
                    .map(URL::getFile)
                    .map(File::new)
                    .orElseThrow(() -> new IOException("Resource not found"));

            final MekariSign mekariSign = MekariSign.getBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setServerType(ServerType.SANDBOX)
                    .setSecretCode(code)
                    .build();

            mekariSign.globalSign(file, signer);

        } catch (IOException | BuildingException e) {
            throw new RuntimeException(e);
        }
    }

}
