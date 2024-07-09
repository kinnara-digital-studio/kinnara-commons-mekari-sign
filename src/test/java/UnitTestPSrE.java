import com.kinnarastudio.commons.mekarisign.MekariSign;
import com.kinnarastudio.commons.mekarisign.exception.BuildingException;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.Annotation;
import com.kinnarastudio.commons.mekarisign.model.AnnotationType;
import com.kinnarastudio.commons.mekarisign.model.RequestSigner;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Optional;
import java.util.Properties;

public class UnitTestPSrE {

    @Test
    public void psreReqSign () throws RuntimeException, ParseException {
        try (final InputStream is = getClass().getResourceAsStream("/properties/secret.properties")) {
            final Properties properties = new Properties() {{
                load(is);
            }};

            final String clientId = properties.getProperty("clientId");
            final String clientSecret = properties.getProperty("clientSecret");
            final String code = properties.getProperty("secretCode");
            final String username = "signer2@yopmail.com";
            final String password = properties.getProperty("password");

            final Annotation annotation = new Annotation(AnnotationType.SIGNATURE, 1, 25, 50, 10, 20, 100, 100);
            final Annotation annotation2 = new Annotation(AnnotationType.SIGNATURE, 1, 50, 100, 10, 20, 100, 100);
            final RequestSigner signer = new RequestSigner("Shaggy Doo", "signer2@yopmail.com", new Annotation[]{annotation,annotation2});
            final File file = Optional.ofNullable(getClass().getResource("/resources/testing_doc1.pdf"))
                    .map(URL::getFile)
                    .map(File::new)
                    .orElseThrow(() -> new IOException("Resource not found"));

            final MekariSign mekariSign = MekariSign.getBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setServerType(ServerType.SANDBOX)
                    .setSecretCode(code)
                    .build();

            mekariSign.psreSign(file, signer);

        } catch (IOException | BuildingException | RequestException e) {
            throw new RuntimeException(e);
        }
    }
}
