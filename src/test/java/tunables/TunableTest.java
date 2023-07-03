
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
@Tag(CATALOG)
class TunableTest extends Setup {
    private static String tunableId;
    // Use a name that will not conflict with already existing tunable on agent
    private static final String TUNABLE_NAME = "TEST_TUNABLE_NAME_";

    //---------------------
    // Test creation tunable enabled
    // ---------------------
    @Test
    @Order(1)
    void createTunable() throws Exception {
        tunableId = AgentsResourceTest.createTunable(UtilHostTest.retrieveOrCreateHostLinux(), TUNABLE_NAME, true);
        assertNotNull(tunableId);
    }

    //---------------------
    // Test creation same tunable disabled
    // ---------------------
    @Test
    @Order(3)
    void createTunableDisabled() throws Exception {
        String host = UtilHostTest.retrieveOrCreateHostLinux();
        assertNotNull(host);
        List<String> errors = AgentsResourceTest.tryCreateTunable(host, TUNABLE_NAME, false);
        WebTestClientFactory.expectedErrors(errors, 10000, "Cannot create new Tunable");
    }

    //---------------------
    // Test verification tunable by Id
    // ---------------------
    @Test
    @Order(5)
    void verifyCreateTunableById() throws Exception {
        assertTrue(AgentsResourceTest.verifyIsTunableCreated(UtilHostTest.retrieveOrCreateHostLinux(), tunableId));
    }

    //---------------------
    // Test verification creation tunable by list
    // ---------------------
    @Test
    @Order(10)
    void verifyCreateTunableByList() throws Exception {
        assertTrue(AgentsResourceTest.verifyIsTunableCreatedInList(UtilHostTest.retrieveOrCreateHostLinux(), tunableId));
    }

    //---------------------
    // Test edition tunable
    // ---------------------
    @Test
    @Order(20)
    void editTunable() throws Exception {
        tunableId =
                AgentsResourceTest.updateTunable(UtilHostTest.retrieveOrCreateHostLinux(), tunableId, TUNABLE_NAME, true);
        assertNotNull(tunableId);
    }

    //---------------------
    // Test verification edition tunable
    // ---------------------
    @Test
    @Order(30)
    void verifyEditTunable() throws Exception {
        assertTrue(AgentsResourceTest.verifyEditTunable(UtilHostTest.retrieveOrCreateHostLinux(), tunableId));
    }

    //---------------------
    // Test disabling tunable
    // ---------------------
    @Test
    @Order(40)
    void disablingTunable() throws Exception {
        assertTrue(AgentsResourceTest.disableTunable(UtilHostTest.retrieveOrCreateHostLinux(), tunableId));
    }

    //---------------------
    // Test Verification disabling tunable
    // ---------------------
    @Test
    @Order(50)
    void verifyDisablingTunable() throws Exception {
        assertTrue(AgentsResourceTest.verifyDisabledTunable(UtilHostTest.retrieveOrCreateHostLinux(), tunableId));
    }

    //---------------------
    // Test suppression tunable
    // ---------------------
    @Test
    @Order(60)
    void suppressionTunable() throws Exception {
        assertTrue(AgentsResourceTest.deleteTunable(UtilHostTest.retrieveOrCreateHostLinux(), tunableId));
    }

    //---------------------
    // Test Verification suppression tunable
    // ---------------------
    @Test
    @Order(70)
    void verifySuppressionTunable() throws Exception {
        assertTrue(AgentsResourceTest.verifySuppressionTunable(UtilHostTest.retrieveOrCreateHostLinux(), tunableId));
    }
}