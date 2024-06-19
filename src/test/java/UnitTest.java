import com.kinnarastudio.commons.mekarisign.MekariESign;
import com.kinnarastudio.commons.mekarisign.exception.BuildingException;
import com.kinnarastudio.commons.mekarisign.model.ServerType;
import org.junit.Test;

public class UnitTest {
    @Test
    public void loginTest() {
        try {
            final MekariESign sign = MekariESign.builder()
                    .setClientId("")
                    .setClientSecret("")
                    .setServerType(ServerType.MOCK)
                    .build();
            System.out.println("log");
        } catch (BuildingException e) {
            throw new RuntimeException(e);
        }
    }
}
