package EventNotifications;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.atempo.tina.testAuto.restAPI.resources.ConfigurationResourceTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class EventNotificationFileTest extends EventNotificationsTemplate {
  static String type = "STDOUT";
  static String name = "standard_output_curl";

  public EventNotificationFileTest() {
    super(type, name);
  }

  @Override
  @Test
  @Order(2)
  void createEvent() {
    event_id = ConfigurationResourceTest.createEventWithPath(name, type, EVENT_NOTIFICATION_PATH);
    assertNotNull(event_id);
  }

  @Override
  @Test
  @Order(5)
  void editEvent() {
    event_id = ConfigurationResourceTest.editEventWithPath(event_id, name, type, EVENT_NOTIFICATION_PATH);
    assertNotNull(event_id);
  }
}