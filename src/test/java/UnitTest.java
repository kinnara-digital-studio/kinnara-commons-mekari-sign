import com.kinnarastudio.commons.mekarisign.MekariSign;
import com.kinnarastudio.commons.mekarisign.exception.BuildingException;
import com.kinnarastudio.commons.mekarisign.model.Authentication;
import com.kinnarastudio.commons.mekarisign.model.GrantType;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import org.junit.Test;

public class UnitTest {
    @Test
    public void loginTest() {
        try {
            final MekariSign.Builder builder = MekariSign.builder()
                    .setClientId("")
                    .setClientSecret("")
                    .setServerType(ServerType.SANDBOX)
                    .setCode("");

            final MekariSign signer = builder.buildAndAuthenticate();


            System.out.println("log");
        } catch (BuildingException e) {
            throw new RuntimeException(e);
        }
    }

}
