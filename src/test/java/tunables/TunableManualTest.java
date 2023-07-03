
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
@Tag(CATALOG)
class TunableManualTest extends Setup {
    static String host_id;
    static String tunable_id;
    static String name = "tunable_curl";
    static String value = "app_trace_debug";

    //---------------------
    // Test recuperation host
    // ---------------------
    @Test
    @Order(2)
    void verifyRecupHost() throws Exception {
        String url = SERVER_URL + "agents?category=host";
        String message = "Host was not found in the hosts list";
        host_id = UtilRequestHttp.recupIdByComparitionInArray(getToken(), url, "name", SERVER_NAME, message);
        assertNotNull(host_id);
    }

    //---------------------
    // Test verification recuperation host by Id
    // ---------------------
    @Test
    @Order(3)
    void verifyRecupHostById() throws Exception {
        assertTrue(UtilHostTest.verifCreateHostById(host_id));
    }

    //---------------------
    // Test creation tunable
    // ---------------------
    @Test
    @Order(4)
    void createTunable() {
        tunable_id = AgentsResourceTest.createTunableManual(host_id, name, value);
        assertNotNull(tunable_id);
    }

    //---------------------
    // Test verification tunable by Id
    // ---------------------
    @Test
    @Order(5)
    void verifyCreateTunableById() {
        assertTrue(AgentsResourceTest.verifyIsTunableCreated(host_id, tunable_id));
    }

    //---------------------
    // Test verification creation tunable by list
    // ---------------------
    @Test
    @Order(6)
    void verifyCreateTunableByList() {
        assertTrue(AgentsResourceTest.verifyIsTunableCreatedInList(host_id, tunable_id));
    }

    //---------------------
    // Test edition tunable
    // ---------------------
    @Test
    @Order(7)
    void editTunable() {
        tunable_id = AgentsResourceTest.editTunableManuel(host_id, tunable_id, name, value);
        assertNotNull(tunable_id);
    }

    //---------------------
    // Test verification edition tunable
    // ---------------------
    @Test
    @Order(8)
    void verifyEditTunable() {
        assertTrue(AgentsResourceTest.verifyEditTunable(host_id, tunable_id));
    }

    //---------------------
    // Test disabling tunable
    // ---------------------
    @Test
    @Order(9)
    void disablingTunable() {
        assertTrue(AgentsResourceTest.disableTunable(host_id, tunable_id));
    }

    //---------------------
    // Test Verification disabled tunable
    // ---------------------
    @Test
    @Order(10)
    void verifyDisabledTunable() {
        assertTrue(AgentsResourceTest.verifyDisabledTunable(host_id, tunable_id));
    }

    //---------------------
    // Test enabling tunable
    // ---------------------
    @Test
    @Order(11)
    void enablingTunable() {
        assertTrue(AgentsResourceTest.enableTunable(host_id, tunable_id));
    }

    //----------------------------------
    // Test Verification enabled tunable
    // ----------------------------------
    @Test
    @Order(12)
    void verifyEnabledTunable() {
        assertTrue(AgentsResourceTest.verifyEnabledTunable(host_id, tunable_id));
    }


    //---------------------
    // Test suppression tunable
    // ---------------------
    @Test
    @Order(13)
    void suppressionTunable() {
        assertTrue(AgentsResourceTest.deleteTunable(host_id, tunable_id));
    }

    //---------------------
    // Test Verification suppression tunable
    // ---------------------
    @Test
    @Order(14)
    void verifySuppressionTunable() {
        assertTrue(AgentsResourceTest.verifySuppressionTunable(host_id, tunable_id));
    }
}