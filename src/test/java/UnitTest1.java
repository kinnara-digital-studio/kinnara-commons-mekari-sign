import com.kinnarastudio.commons.mekarisign.MekariSign;
import com.kinnarastudio.commons.mekarisign.exception.BuildingException;
import com.kinnarastudio.commons.mekarisign.exception.RequestException;
import com.kinnarastudio.commons.mekarisign.model.*;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Date;
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
            final Annotation annotation2 = new Annotation(AnnotationType.SIGNATURE, 1, 50, 100, 10, 20, 100, 100);
            final RequestSigner signer = new RequestSigner("Scooby Doo", username, new Annotation[]{annotation,annotation2});
            final File file = Optional.ofNullable(getClass().getResource("/resources/testing_doc1.pdf"))
                    .map(URL::getFile)
                    .map(File::new)
                    .orElseThrow(() -> new IOException("Resource not found"));

            final MekariSign mekariSign = MekariSign.getBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setServerType(ServerType.SANDBOX)
                    .setSecretCode(code)
                    .authenticateAndBuild();

                    // try(InputStream inputStream = getClass().getResourceAsStream("/resources/testing_doc2.pdf");) {
                    //     mekariSign.globalSign(inputStream, new Date().toString() + ".pdf", new RequestSigner[] {signer});
                    // }
                mekariSign.globalSign(file, signer);

        } catch (IOException | BuildingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void downloadFile() throws RequestException {
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
            final Annotation annotation2 = new Annotation(AnnotationType.SIGNATURE, 1, 50, 100, 10, 20, 100, 100);
            final RequestSigner signer = new RequestSigner("Scooby Doo", username, new Annotation[]{annotation,annotation2});
            // final File file = Optional.ofNullable(getClass().getResource("/resources/testing_doc1.pdf"))
            //         .map(URL::getFile)
            //         .map(File::new)
            //         .orElseThrow(() -> new IOException("Resource not found"));

            // final File file = new File("/home/natsuchi/Documents/testing_doc2.pdf");
            File file = File.createTempFile("test", ".pdf", new File("/home/user/Documents/"));
            file.setWritable(true);
            final MekariSign mekariSign = MekariSign.getBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setServerType(ServerType.SANDBOX)
                    .setSecretCode(code)
                    .authenticateAndBuild();

            mekariSign.downloadDoc("01ec84e4-f8b4-449b-9429-ffff8c1a764b", file);

        } catch (IOException | BuildingException e) {
            throw new RuntimeException(e);
        }

//        try(InputStream is = getClass().getResourceAsStream("/resources/testing_doc1.pdf");
//            FileOutputStream fos = new FileOutputStream(File.createTempFile("testing_doc1", ".pdf", new File("/home/aristo/Developments/Java/kinnara-commons-mekari-sign/src/test/resources/resources/")))) {
//
//            byte[] buffer = new byte[4096];
//            int len;
//            while((len = is.read(buffer)) > 0) {
//                fos.write(buffer, 0, len);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    public void documentListRetrieval() throws RequestException, BuildingException, ParseException {
        try (final InputStream is = getClass().getResourceAsStream("/properties/secret.properties")) {

            final Properties properties = new Properties() {{
                load(is);
            }};

            final String clientId = properties.getProperty("clientId");
            final String clientSecret = properties.getProperty("clientSecret");
            final String code = properties.getProperty("secretCode");
            final String username = properties.getProperty("username");
            final String password = properties.getProperty("password");

            AuthenticationToken authToken = MekariSign.getBuilder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setServerType(ServerType.SANDBOX)
            .setSecretCode(code)
            .authenticate();

            System.out.println(authToken.getAccessToken());
            System.out.println(authToken.getTokenType());
            System.out.println(authToken.getExpired());
            System.out.println(authToken.getRefreshToken());
            System.out.println(authToken.getServerType());

            final MekariSign mekariSign = MekariSign.getBuilder()
                    .setAuthenticationToken(authToken)
                    .setServerType(ServerType.SANDBOX)
                    .authenticateAndBuild();

            mekariSign.getDoc(1, 8, null, null, null);;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void authTokenTest() throws RequestException, BuildingException, ParseException {
        try (final InputStream is = getClass().getResourceAsStream("/properties/secret.properties")) {

            final Properties properties = new Properties() {{
                load(is);
            }};

            AuthenticationToken authToken = new AuthenticationToken("RWPmuyyNokpjXW2TYgcAV136k4KDJpn5", TokenType.BEARER, 3600, "aabbcc", ServerType.SANDBOX);

            final MekariSign mekariSign = MekariSign.getBuilder()
                    .setAuthenticationToken(authToken)
                    .authenticateAndBuild();
            
            for(int i = 0; i < 10; i++)
            {
                System.out.println(mekariSign.getDoc(1, 10, null, null, null).getRespData()[i].getId());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void documentListDetail() throws RequestException, BuildingException, ParseException {
        try (final InputStream is = getClass().getResourceAsStream("/properties/secret.properties")) {

            final Properties properties = new Properties() {{
                load(is);
            }};

            AuthenticationToken authToken = new AuthenticationToken("ObZpjqI1kEL8nehrB9FTiAMQLiDPcC0t", TokenType.BEARER, 3600, "aabbcc", ServerType.SANDBOX);

            final MekariSign mekariSign = MekariSign.getBuilder()
                    .setAuthenticationToken(authToken)
                    .authenticateAndBuild();

            mekariSign.getDocDetail("01ec84e4-f8b4-449b-9429-ffff8c1a764b");;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void userProfile() throws RequestException, BuildingException, ParseException {
        try (final InputStream is = getClass().getResourceAsStream("/properties/secret.properties")) {

            final Properties properties = new Properties() {{
                load(is);
            }};

            final String clientId = properties.getProperty("clientId");
            final String clientSecret = properties.getProperty("clientSecret");
            final String code = properties.getProperty("secretCode");
            final String username = properties.getProperty("username");
            final String password = properties.getProperty("password");

            final MekariSign mekariSign = MekariSign.getBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setServerType(ServerType.SANDBOX)
                    .setSecretCode(code)
                    .authenticateAndBuild();

            mekariSign.getProfile();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
