package EventNotifications;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.atempo.tina.testAuto.restAPI.resources.ConfigurationResourceTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class EventNotificationTest extends EventNotificationsTemplate {
  static String type = "STDOUT";
  static String name = "standard_output_curl";

  public EventNotificationTest() {
    super(type, name);
  }

  @Override
  @Test
  @Order(2)
  void createEvent() {
    event_id = ConfigurationResourceTest.createEventNotifications(name, type);
    assertNotNull(event_id);
  }

  @Override
  @Test
  @Order(5)
  void editEvent() {
    event_id = ConfigurationResourceTest.updateEventNotification(event_id, name, type);
    assertNotNull(event_id);
  }
}