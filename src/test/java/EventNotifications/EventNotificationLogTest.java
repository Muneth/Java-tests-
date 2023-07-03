package EventNotifications;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.atempo.tina.testAuto.restAPI.resources.ConfigurationResourceTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class EventNotificationLogTest extends EventNotificationsTemplate {
  static String type = "SYSLOG";
  static String name = "system_log_curl";

  public EventNotificationLogTest() {
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