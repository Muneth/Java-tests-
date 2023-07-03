
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Tag(CATALOG)
@TestMethodOrder(OrderAnnotation.class)
class AlarmFilterFileTest extends Setup {
    static String alarm_filter_id;

    @Test
    @Order(3)
    void createFileAlarmFilter() {
        alarm_filter_id =
                AgentsResourceTest.createAlarmFilter(UtilHostTest.getHostServerPublicId(), "FILE", ALARM_FILTERS_PATH);
        assertNotNull(alarm_filter_id);
    }

    @Test
    @Order(5)
    void verifyCreateFileAlarmFilterById() {
        assumeTrue(alarm_filter_id != null);
        assertTrue(AgentsResourceTest.verifyIsAlarmFilterCreated(UtilHostTest.getHostServerPublicId(), alarm_filter_id));
    }

    @Test
    @Order(6)
    void verifyCreateFileAlarmFilterByList() {
        assumeTrue(alarm_filter_id != null);
        assertTrue(
                AgentsResourceTest.verifyIsAlarmFilterCreatedInList(UtilHostTest.getHostServerPublicId(), alarm_filter_id));
    }

    @Test
    @Order(7)
    void editFileAlarmFilter() {
        assumeTrue(alarm_filter_id != null);
        alarm_filter_id = AgentsResourceTest.editAlarmFilter(UtilHostTest.getHostServerPublicId(), "FILE", alarm_filter_id,
                ALARM_FILTERS_PATH);
        assertNotNull(alarm_filter_id);
    }

    @Test
    @Order(8)
    void verifyEditFileAlarmFilter() {
        assumeTrue(alarm_filter_id != null);
        assertTrue(AgentsResourceTest.verifyEditAlarmFilter(UtilHostTest.getHostServerPublicId(), alarm_filter_id));
    }

    @Test
    @Order(9)
    void enablingFileAlarmFilter() {
        assumeTrue(alarm_filter_id != null);
        assertTrue(AgentsResourceTest.enableAlarmFilter(UtilHostTest.getHostServerPublicId(), alarm_filter_id));
    }

    @Test
    @Order(10)
    void verifyEnablingFileAlarmFilter() {
        assumeTrue(alarm_filter_id != null);
        assertTrue(AgentsResourceTest.verifyEnabledAlarmFilter(UtilHostTest.getHostServerPublicId(), alarm_filter_id));
    }

    @Test
    @Order(11)
    void disablingFileAlarmFilter() {
        assumeTrue(alarm_filter_id != null);
        assertTrue(AgentsResourceTest.disableAlarmFilter(UtilHostTest.getHostServerPublicId(), alarm_filter_id));
    }

    @Test
    @Order(12)
    void verifyDisablingFileAlarmFilter() {
        assumeTrue(alarm_filter_id != null);
        assertTrue(AgentsResourceTest.verifyDisabledAlarmFilter(UtilHostTest.getHostServerPublicId(), alarm_filter_id));
    }

    @Test
    @Order(13)
    void suppressionFileAlarmFilter() {
        assumeTrue(alarm_filter_id != null);
        assertTrue(AgentsResourceTest.deleteAlarmFilter(UtilHostTest.getHostServerPublicId(), alarm_filter_id));
    }

    @Test
    @Order(14)
    void verifySuppressionFileAlarmFilter() {
        assumeTrue(alarm_filter_id != null);
        assertTrue(AgentsResourceTest.verifyDeleteAlarmFilterTest(UtilHostTest.getHostServerPublicId(), alarm_filter_id));
    }
}