package com.atempo.tina.testAuto.restAPI.resources;

import static com.atempo.tina.testAuto.restAPI.resources.WebTestClientFactory.getIdForCreateStatusOk;
import static com.atempo.tina.testAuto.restAPI.resources.WebTestClientFactory.getIdForUpdateStatusOk;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.atempo.tina.restapi.model.ActionInput;
import com.atempo.tina.restapi.model.configuration.EventNotification;
import com.atempo.tina.restapi.model.configuration.EventNotificationCreationInput;
import com.atempo.tina.restapi.model.configuration.EventNotificationEditionInput;
import com.atempo.tina.restapi.model.swagger.EventNotificationsActionInputDelete;
import com.atempo.tina.testAuto.restAPI.utils.UtilRequestHttp;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationResourceTest {

    public static String createEventNotifications(String nameNotification,
                                                  String typeNotification) {

        String url = "configuration/event-notifications";
        EventNotificationCreationInput eventNotificationInput = new EventNotificationCreationInput();
        eventNotificationInput.setName(nameNotification);
        eventNotificationInput.setNotificationType(typeNotification);
        eventNotificationInput.setComment("");
        eventNotificationInput.setEnabled(true);
        eventNotificationInput.setQuickTextEncoding(6);
        eventNotificationInput.setText("%s$raw");
        eventNotificationInput.setTextEncoding("UTF-8");
        ArrayList<String> alarmIds = new ArrayList<>();
        alarmIds.add("*");
        eventNotificationInput.setAlarmIds(alarmIds);
        ArrayList<String> userNames = new ArrayList<>();
        userNames.add("*");
        eventNotificationInput.setUserNames(userNames);
        ArrayList<String> classes = new ArrayList<>();
        classes.add("*");
        eventNotificationInput.setClasses(classes);
        ArrayList<String> catalogNames = new ArrayList<>();
        catalogNames.add("*");
        eventNotificationInput.setCatalogNames(catalogNames);
        ArrayList<String> programs = new ArrayList<>();
        programs.add("*");
        eventNotificationInput.setPrograms(programs);
        ArrayList<String> applicationNames = new ArrayList<>();
        applicationNames.add("*");
        eventNotificationInput.setApplicationNames(applicationNames);
        ArrayList<String> hostNames = new ArrayList<>();
        hostNames.add("*");
        eventNotificationInput.setHostNames(hostNames);
        ArrayList<String> severities = new ArrayList<>();
        severities.add("ALARMMINOR");
        severities.add("ALARMMAJOR");
        severities.add("ALARMCRITICAL");
        eventNotificationInput.setSeverities(severities);
        return getIdForCreateStatusOk(url, eventNotificationInput);
    }


    public static String createEventWithPath(String nameNotification,
                                             String typeNotification,
                                             String path) {

        String url = "configuration/event-notifications";
        EventNotificationCreationInput eventNotificationInput = new EventNotificationCreationInput();
        eventNotificationInput.setName(nameNotification);
        eventNotificationInput.setNotificationType(typeNotification);
        eventNotificationInput.setComment("");
        eventNotificationInput.setEnabled(true);
        eventNotificationInput.setQuickTextEncoding(6);
        eventNotificationInput.setText("%s$raw");
        eventNotificationInput.setTextEncoding("UTF-8");
        eventNotificationInput.setPath(path);
        ArrayList<String> alarmIds = new ArrayList<>();
        alarmIds.add("*");
        eventNotificationInput.setAlarmIds(alarmIds);
        ArrayList<String> userNames = new ArrayList<>();
        userNames.add("*");
        eventNotificationInput.setUserNames(userNames);
        ArrayList<String> classes = new ArrayList<>();
        classes.add("*");
        eventNotificationInput.setClasses(classes);
        ArrayList<String> catalogNames = new ArrayList<>();
        catalogNames.add("*");
        eventNotificationInput.setCatalogNames(catalogNames);
        ArrayList<String> programs = new ArrayList<>();
        programs.add("*");
        eventNotificationInput.setPrograms(programs);
        ArrayList<String> applicationNames = new ArrayList<>();
        applicationNames.add("*");
        eventNotificationInput.setApplicationNames(applicationNames);
        ArrayList<String> hostNames = new ArrayList<>();
        hostNames.add("*");
        eventNotificationInput.setHostNames(hostNames);
        ArrayList<String> severities = new ArrayList<>();
        severities.add("ALARMMINOR");
        severities.add("ALARMMAJOR");
        severities.add("ALARMCRITICAL");
        eventNotificationInput.setSeverities(severities);
        return getIdForCreateStatusOk(url, eventNotificationInput);

    }

    public static boolean verifyCreateEventById(String eventId) {
        assertNotNull(eventId);
        String url = "configuration/event-notifications";
        EventNotification eventNotification = WebTestClientFactory.getOneByURI(url, EventNotification.class);
        return eventId.equals(eventNotification.getPublicId());
    }

    public static boolean verifyCreateEventByList(String eventId) {
        assertNotNull(eventId);
        String url = "configuration/event-notifications";
        List<EventNotification> eventNotificationList = WebTestClientFactory.getListByURI(url, EventNotification.class);
        for (EventNotification eventNotification : eventNotificationList) {
            if (eventId.equals(eventNotification.getPublicId())) {
                return true;
            }
        }
        return false;
    }

    public static String updateEventNotification(String eventId,
                                                 String nameNotification,
                                                 String typeNotification) {
        assertNotNull(eventId);
        String url = "configuration/event-notifications/" + eventId;

        EventNotificationEditionInput eventNotificationInput =
                WebTestClientFactory.getOneByURI(url, EventNotificationEditionInput.class);
        eventNotificationInput.setName(nameNotification);
        eventNotificationInput.setNotificationType(typeNotification);
        eventNotificationInput.setComment(UtilRequestHttp.MODIF);
        eventNotificationInput.setEnabled(true);
        eventNotificationInput.setQuickTextEncoding(6);
        eventNotificationInput.setText("%s$raw");
        eventNotificationInput.setTextEncoding("UTF-8");
        ArrayList<String> alarmIds = new ArrayList<>();
        alarmIds.add("*");
        eventNotificationInput.setAlarmIds(alarmIds);
        ArrayList<String> userNames = new ArrayList<>();
        userNames.add("*");
        eventNotificationInput.setUserNames(userNames);
        ArrayList<String> classes = new ArrayList<>();
        classes.add("*");
        eventNotificationInput.setClasses(classes);
        ArrayList<String> catalogNames = new ArrayList<>();
        catalogNames.add("*");
        eventNotificationInput.setCatalogNames(catalogNames);
        ArrayList<String> programs = new ArrayList<>();
        programs.add("*");
        eventNotificationInput.setPrograms(programs);
        ArrayList<String> applicationNames = new ArrayList<>();
        applicationNames.add("*");
        eventNotificationInput.setApplicationNames(applicationNames);
        ArrayList<String> hostNames = new ArrayList<>();
        hostNames.add("*");
        eventNotificationInput.setHostNames(hostNames);
        ArrayList<String> severities = new ArrayList<>();
        severities.add("ALARMMINOR");
        severities.add("ALARMMAJOR");
        severities.add("ALARMCRITICAL");
        eventNotificationInput.setSeverities(severities);
        return getIdForUpdateStatusOk(url, eventNotificationInput);

    }

    public static String editEventWithPath(String eventId,
                                           String nameNotification,
                                           String typeNotification,
                                           String path) {
        assertNotNull(eventId);
        String url = "configuration/event-notifications/" + eventId;

        EventNotificationEditionInput eventNotificationInput =
                WebTestClientFactory.getOneByURI(url, EventNotificationEditionInput.class);
        eventNotificationInput.setName(nameNotification);
        eventNotificationInput.setNotificationType(typeNotification);
        eventNotificationInput.setComment(UtilRequestHttp.MODIF);
        eventNotificationInput.setEnabled(true);
        eventNotificationInput.setQuickTextEncoding(6);
        eventNotificationInput.setText("%s$raw");
        eventNotificationInput.setTextEncoding("UTF-8");
        eventNotificationInput.setPath(path);
        ArrayList<String> alarmIds = new ArrayList<>();
        alarmIds.add("*");
        eventNotificationInput.setAlarmIds(alarmIds);
        ArrayList<String> userNames = new ArrayList<>();
        userNames.add("*");
        eventNotificationInput.setUserNames(userNames);
        ArrayList<String> classes = new ArrayList<>();
        classes.add("*");
        eventNotificationInput.setClasses(classes);
        ArrayList<String> catalogNames = new ArrayList<>();
        catalogNames.add("*");
        eventNotificationInput.setCatalogNames(catalogNames);
        ArrayList<String> programs = new ArrayList<>();
        programs.add("*");
        eventNotificationInput.setPrograms(programs);
        ArrayList<String> applicationNames = new ArrayList<>();
        applicationNames.add("*");
        eventNotificationInput.setApplicationNames(applicationNames);
        ArrayList<String> hostNames = new ArrayList<>();
        hostNames.add("*");
        eventNotificationInput.setHostNames(hostNames);
        ArrayList<String> severities = new ArrayList<>();
        severities.add("ALARMMINOR");
        severities.add("ALARMMAJOR");
        severities.add("ALARMCRITICAL");
        eventNotificationInput.setSeverities(severities);
        return getIdForUpdateStatusOk(url, eventNotificationInput);

    }

    public static boolean verifyEditEvent(String eventId) {
        assertNotNull(eventId);
        String url = "configuration/event-notifications/" + eventId;
        EventNotificationEditionInput eventNotificationInput =
                WebTestClientFactory.getOneByURI(url, EventNotificationEditionInput.class);
        return eventNotificationInput.getComment().contains(UtilRequestHttp.MODIF);
    }

    public static boolean runEventNotificationsAction(String eventId,
                                                      String actionName) {
        assertNotNull(eventId);
        String url = "configuration/event-notifications/actions";

        EventNotificationsActionInputDelete input = new EventNotificationsActionInputDelete();
        List<String> publicIds = new ArrayList<>();
        publicIds.add(eventId);
        input.setPublicIds(publicIds);
        input.setActionName(actionName);
        WebTestClientFactory.putThatExpectedStatusOk(url, input);
        return true;
    }

    public static boolean deleteEventNotification(String eventId) {
        return runEventNotificationsAction(eventId, ActionInput.EVENT_NOTIFICATIONS_ACTION_DELETE);
    }

    public static boolean verifySuppressionEventNotification(String eventId) {
        assertNotNull(eventId);
        return !verifyCreateEventByList(eventId);
    }

    public static boolean enableEventNotification(String eventId) {
        return runEventNotificationsAction(eventId, ActionInput.EVENT_NOTIFICATIONS_ACTION_ENABLE);
    }

    public static boolean verifyEnabledEventNotification(String eventId) {
        assertNotNull(eventId);
        String url = "configuration/event-notifications/" + eventId;
        EventNotification eventNotification = WebTestClientFactory.getOneByURI(url, EventNotification.class);
        return eventNotification.isEnabled();
    }

    public static boolean disableEventNotification(String eventId) {
        return runEventNotificationsAction(eventId, ActionInput.EVENT_NOTIFICATIONS_ACTION_DISABLE);
    }

    public static boolean verifyDisabledEventNotification(String eventId) {
        assertNotNull(eventId);
        return !verifyEnabledEventNotification(eventId);
    }
}
