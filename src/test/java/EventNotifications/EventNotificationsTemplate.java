package EventNotifications;

import static com.atempo.TestTags.CATALOG;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.atempo.tina.testAuto.restAPI.Setup;
import com.atempo.tina.testAuto.restAPI.resources.ConfigurationResourceTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
@Tag(CATALOG)
abstract class EventNotificationsTemplate extends Setup {
  static String event_id;
  static String type;
  static String name;

  public EventNotificationsTemplate(String type,
                                    String name) {
    EventNotificationsTemplate.type = type;
    EventNotificationsTemplate.name = name;
  }

  @Test
  @Order(10)
  abstract void createEvent();

  @Test
  @Order(20)
  void verifyCreateEventById() {
    assertTrue(ConfigurationResourceTest.verifyCreateEventById(event_id));
  }

  @Test
  @Order(30)
  void verifyCreateEventByList() {
    assertTrue(ConfigurationResourceTest.verifyCreateEventByList(event_id));
  }

  @Test
  @Order(40)
  abstract void editEvent();

  @Test
  @Order(50)
  void verifyEditEvent() {
    assertTrue(ConfigurationResourceTest.verifyEditEvent(event_id));
  }

  @Test
  @Order(60)
  void enablingEvent() {
    assumeTrue(event_id != null);
    assertTrue(ConfigurationResourceTest.enableEventNotification(event_id));
  }

  @Test
  @Order(70)
  void verifyEnablingEvent() {
    assumeTrue(event_id != null);
    assertTrue(ConfigurationResourceTest.verifyEnabledEventNotification(event_id));
  }

  @Test
  @Order(80)
  void disablingEvent() {
    assumeTrue(event_id != null);
    assertTrue(ConfigurationResourceTest.disableEventNotification(event_id));
  }

  @Test
  @Order(90)
  void verifyDisablingEvent() {
    assumeTrue(event_id != null);
    assertTrue(ConfigurationResourceTest.verifyDisabledEventNotification(event_id));
  }

  @Test
  @Order(100)
  void suppressionEvent() {
    assumeTrue(CLEAN_AFTER_TEST);
    assumeTrue(event_id != null);
    assertTrue(ConfigurationResourceTest.deleteEventNotification(event_id));
  }

  @Test
  @Order(110)
  void verifySuppressionEvent() {
    assumeTrue(CLEAN_AFTER_TEST);
    assumeTrue(event_id != null);
    assertTrue(ConfigurationResourceTest.verifySuppressionEventNotification(event_id));
  }
}
