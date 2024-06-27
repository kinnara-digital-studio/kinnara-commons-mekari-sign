import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.*;
import com.kinnarastudio.commons.mekarisign.service.Authenticator;
import com.kinnarastudio.commons.mekarisign.service.GlobalSigner;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
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

            final Authentication authentication = new Authentication(clientId, clientSecret, GrantType.AUTHORIZATION_CODE, code);
            final AuthenticationToken token = Authenticator.getInstance().authenticate(ServerType.SANDBOX, authentication);
            final Annotation annotation = new Annotation(AnnotationType.SIGNATURE, 1, 50, 50, 100, 100, 100, 100);
            final RequestSigner signer = new RequestSigner("Scoobydoo", username, annotation);

            final File file = new File(getClass().getResource("/resources/testing_doc.pdf").getFile());
            final String filename = file.getName();

            final StringBuilder sb = new StringBuilder();
            try (final FileInputStream fis = new FileInputStream(file)) {
                final Base64.Encoder encoder = Base64.getEncoder();

                int len = Math.toIntExact(file.length());
                final byte[] buffer = new byte[len];

                while (fis.read(buffer) > 0) {
                    final String base64encoded = encoder.encodeToString(buffer);
                    sb.append(base64encoded);
                }
            }
            final String doc = sb.toString();

            final GlobalSignRequest globalSignRequest = new GlobalSignRequest(filename, doc, signer);
            final GlobalSigner globalSigner = new GlobalSigner();
            globalSigner.requestSign(ServerType.SANDBOX, token, globalSignRequest);

//            final MekariSign mekariSign = MekariSign.Builder.getInstance()
//                    .setClientId(clientSecret)
//                    .setClientSecret(clientSecret)
//                    .setServerType(ServerType.SANDBOX)
//                    .setCode(code)
//                    .buildAndAuthenticate();
//
//            mekariSign.globalSign(file);
//            mekariSign.digistamp(file);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
