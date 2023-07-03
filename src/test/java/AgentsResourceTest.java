import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AgentsResourceTest {
    public static String createTunable(String hostId,
                                       String tunableName,
                                       boolean enabled) {
        String url = "agents/hosts/" + hostId + "/tunables";
        TunableCreationInput input = new TunableCreationInput();
        input.setName(tunableName);
        input.setComment("This tunable is created by a automatic test");
        input.setEnabled(enabled);
        ArrayList<String> binaryList = new ArrayList<>();
        binaryList.add("*");
        input.setBinaryList(binaryList);
        ArrayList<String> catalogList = new ArrayList<>();
        catalogList.add("*");
        input.setCatalogList(catalogList);
        ArrayList<String> valueList = new ArrayList<>();
        valueList.add("0");
        input.setValueList(valueList);
        return getIdForCreateStatusOk(url, input);
    }

    public static List<String> tryCreateTunable(String hostId,
                                                String tunableName,
                                                boolean enabled) {
        String url = "agents/hosts/" + hostId + "/tunables";
        TunableCreationInput input = new TunableCreationInput();
        input.setName(tunableName);
        input.setComment("This tunable is created by a automatic test");
        input.setEnabled(enabled);
        ArrayList<String> binaryList = new ArrayList<>();
        binaryList.add("*");
        input.setBinaryList(binaryList);
        ArrayList<String> catalogList = new ArrayList<>();
        catalogList.add("*");
        input.setCatalogList(catalogList);
        ArrayList<String> valueList = new ArrayList<>();
        valueList.add("0");
        input.setValueList(valueList);
        return WebTestClientFactory.postForCreationStatusOther(url, input, 500);
    }

    public static String createTunableManual(String hostId,
                                             String tunableName,
                                             String value) {
        String url = "agents/hosts/" + hostId + "/tunables";
        TunableCreationInput input = new TunableCreationInput();
        input.setName(tunableName);
        input.setComment("This tunable is created by a automatic test");
        input.setEnabled(true);
        ArrayList<String> binaryList = new ArrayList<>();
        binaryList.add("*");
        input.setBinaryList(binaryList);
        ArrayList<String> catalogList = new ArrayList<>();
        catalogList.add("*");
        input.setCatalogList(catalogList);
        ArrayList<String> valueList = new ArrayList<>();
        valueList.add(value);
        input.setValueList(valueList);
        return getIdForCreateStatusOk(url, input);
    }

    public static Tunable getTunable(String hostId,
                                     String tunableId) {
        assertNotNull(tunableId);
        String url = "agents/hosts/" + hostId + "/tunables/" + tunableId;
        return WebTestClientFactory.getOneByURI(url, Tunable.class);
    }

    public static boolean verifyIsTunableCreated(String hostId,
                                                 String tunableId) {
        Tunable tunable = getTunable(hostId, tunableId);
        return tunable != null && tunable.getPublicId().equals(tunableId);
    }

    public static List<Tunable> getTunables(String hostId,
                                            String tunableId) {
        assertNotNull(tunableId);
        String url = "agents/hosts/" + hostId + "/tunables/";
        return WebTestClientFactory.getListByURI(url, Tunable.class);
    }

    public static boolean verifyIsTunableCreatedInList(String hostId,
                                                       String tunableId) {
        List<Tunable> tunables = getTunables(hostId, tunableId);
        for (Tunable tunable : tunables) {
            if (tunable != null && tunable.getPublicId().equals(tunableId)) {
                return true;
            }
        }
        return false;
    }

    public static String updateTunable(String hostId,
                                       String tunableId,
                                       String tunableName,
                                       boolean enabled) {
        assertNotNull(tunableId);
        String url = "agents/hosts/" + hostId + "/tunables/" + tunableId;

        TunableEditionInput tunable = WebTestClientFactory.getOneByURI(url, TunableEditionInput.class);
        tunable.setName(tunableName);
        tunable.setComment(UtilRequestHttp.MODIF);
        tunable.setEnabled(enabled);
        ArrayList<String> binaryList = new ArrayList<>();
        binaryList.add("*");
        tunable.setBinaryList(binaryList);
        ArrayList<String> catalogList = new ArrayList<>();
        catalogList.add("*");
        tunable.setCatalogList(catalogList);
        ArrayList<String> valueList = new ArrayList<>();
        valueList.add("0");
        tunable.setValueList(valueList);
        return getIdForUpdateStatusOk(url, tunable);
    }

    public static String editTunableManuel(String hostId,
                                           String tunableId,
                                           String tunableName,
                                           String value) {
        assertNotNull(tunableId);
        String url = "agents/hosts/" + hostId + "/tunables/" + tunableId;

        TunableEditionInput tunable = WebTestClientFactory.getOneByURI(url, TunableEditionInput.class);
        tunable.setName(tunableName);
        tunable.setComment(UtilRequestHttp.MODIF);
        tunable.setEnabled(true);
        ArrayList<String> binaryList = new ArrayList<>();
        binaryList.add("*");
        tunable.setBinaryList(binaryList);
        ArrayList<String> catalogList = new ArrayList<>();
        catalogList.add("*");
        tunable.setCatalogList(catalogList);
        ArrayList<String> valueList = new ArrayList<>();
        valueList.add(value);
        tunable.setValueList(valueList);
        return getIdForUpdateStatusOk(url, tunable);
    }


    public static boolean verifyEditTunable(String hostId,
                                            String tunableId) {
        assertNotNull(tunableId);
        String url = "agents/hosts/" + hostId + "/tunables/" + tunableId;
        Tunable tunable = WebTestClientFactory.getOneByURI(url, Tunable.class);
        return tunable.getComment().contains(UtilRequestHttp.MODIF);
    }

    public static boolean runTunableAction(String hostId,
                                           String tunableId,
                                           String actionName) {
        assertNotNull(tunableId);
        String url = "agents/hosts/" + hostId + "/tunables/actions";

        TunableActionInputDelete input = new TunableActionInputDelete();
        List<String> publicIds = new ArrayList<>();
        publicIds.add(tunableId);
        input.setPublicIds(publicIds);
        input.setActionName(actionName);
        WebTestClientFactory.putThatExpectedStatusOk(url, input);
        return true;
    }

    public static boolean deleteTunable(String hostId,
                                        String tunableId) {
        return runTunableAction(hostId, tunableId, ActionInput.TUNABLES_ACTION_DELETE);
    }


    public static boolean verifySuppressionTunable(String hostId,
                                                   String tunableId) {
        assertNotNull(tunableId);
        return !verifyIsTunableCreatedInList(hostId, tunableId);
    }

    public static boolean enableTunable(String hostId,
                                        String tunableId) {
        return runTunableAction(hostId, tunableId, ActionInput.TUNABLES_ACTION_ENABLE);
    }

    public static boolean verifyEnabledTunable(String hostId,
                                               String tunableId) {
        assertNotNull(tunableId);
        String url = "agents/hosts/" + hostId + "/tunables/" + tunableId;
        Tunable tunable = WebTestClientFactory.getOneByURI(url, Tunable.class);
        return tunable.getEnabled();
    }

    public static boolean disableTunable(String hostId,
                                         String tunableId) {
        return runTunableAction(hostId, tunableId, ActionInput.TUNABLES_ACTION_DISABLE);
    }

    public static boolean verifyDisabledTunable(String hostId,
                                                String tunableId) {
        assertNotNull(tunableId);
        return !verifyEnabledTunable(hostId, tunableId);
    }

    public static String createAlarmFilter(String hostId,
                                           String typeAlarmFilter,
                                           String path) {

        String url = "agents/hosts/" + hostId + "/alarm-filtering";
        AlarmFilterCreationInput alarmFilterInput = new AlarmFilterCreationInput();
        alarmFilterInput.setAlarmFilterType(typeAlarmFilter);
        alarmFilterInput.setComment("");
        alarmFilterInput.setEnabled(true);
        ArrayList<String> catalogs = new ArrayList<>();
        catalogs.add("*");
        alarmFilterInput.setCatalogs(catalogs);
        ArrayList<String> pathList = new ArrayList<>();
        pathList.add(path);
        alarmFilterInput.setPathList(pathList);
        return getIdForCreateStatusOk(url, alarmFilterInput);
    }

    public static AlarmFilter getAlarmFilterById(String hostId,
                                                 String alarmFilteringId) {
        assertNotNull(alarmFilteringId);
        String url = "agents/hosts/" + hostId + "/alarm-filtering/" + alarmFilteringId;
        return WebTestClientFactory.getOneByURI(url, AlarmFilter.class);
    }

    public static boolean verifyIsAlarmFilterCreated(String hostId,
                                                     String alarmFilteringId) {
        AlarmFilter alarmFilter = getAlarmFilterById(hostId, alarmFilteringId);
        return Objects.equals(alarmFilteringId, alarmFilter.getPublicId());
    }

    public static List<AlarmFilter> getAlarmFiltering(String hostId,
                                                      String alarmFilteringId) {
        assertNotNull(alarmFilteringId);
        String url = "agents/hosts/" + hostId + "/alarm-filtering/";
        return WebTestClientFactory.getListByURI(url, AlarmFilter.class);
    }

    public static boolean verifyIsAlarmFilterCreatedInList(String hostId,
                                                           String alarmFilteringId) {
        List<AlarmFilter> alarmFilters = getAlarmFiltering(hostId, alarmFilteringId);
        for (AlarmFilter alarmFilter : alarmFilters) {
            if (alarmFilteringId.equals(alarmFilter.getPublicId())) {
                return true;
            }
        }
        return false;
    }

    public static String editAlarmFilter(String hostId,
                                         String typeAlarmFilter,
                                         String alarmFilteringId,
                                         String path) {
        assertNotNull(alarmFilteringId);
        String url = "agents/hosts/" + hostId + "/alarm-filtering/" + alarmFilteringId;

        AlarmFilterEditionInput alarmFilterInput = WebTestClientFactory.getOneByURI(url, AlarmFilterEditionInput.class);
        alarmFilterInput.setAlarmFilterType(typeAlarmFilter);
        alarmFilterInput.setComment(UtilRequestHttp.MODIF);
        alarmFilterInput.setEnabled(true);
        ArrayList<String> catalogs = new ArrayList<>();
        catalogs.add("*");
        alarmFilterInput.setCatalogs(catalogs);
        alarmFilterInput.setPath(path);
        return getIdForUpdateStatusOk(url, alarmFilterInput);
    }

    public static boolean verifyEditAlarmFilter(String hostId,
                                                String alarmFilteringId) {
        assertNotNull(alarmFilteringId);
        String url = "agents/hosts/" + hostId + "/alarm-filtering/" + alarmFilteringId;
        AlarmFilter alarmFilter = WebTestClientFactory.getOneByURI(url, AlarmFilter.class);
        return alarmFilter.getComment().contains(UtilRequestHttp.MODIF);
    }

    public static boolean runAlarmFilteringAction(String hostId,
                                                  String alarmFilteringId,
                                                  String actionName) {
        assertNotNull(alarmFilteringId);
        String url = "agents/hosts/" + hostId + "/alarm-filtering/actions";

        ActionInput alarmFilterActionInput = new AlarmFilterActionInputDelete();
        List<String> publicIds = new ArrayList<>();
        publicIds.add(alarmFilteringId);
        alarmFilterActionInput.setPublicIds(publicIds);
        alarmFilterActionInput.setActionName(actionName);
        WebTestClientFactory.putThatExpectedStatusOk(url, alarmFilterActionInput);
        return true;
    }

    public static boolean deleteAlarmFilter(String hostId,
                                            String alarmFilterId) {
        return runAlarmFilteringAction(hostId, alarmFilterId, ActionInput.ALARM_FILTER_ACTION_DELETE);
    }

    public static boolean verifyDeleteAlarmFilterTest(String hostId,
                                                      String alarmFilterId) {
        assertNotNull(alarmFilterId);
        String url = "agents/hosts/" + hostId + "/alarm-filtering";

        List<AlarmFilter> alarmFilters = WebTestClientFactory.getListByURI(url, AlarmFilter.class);
        for (AlarmFilter alarmFilter : alarmFilters) {
            if (Objects.equals(alarmFilterId, alarmFilter.getPublicId())) {
                return false;
            }
        }
        return true;
    }

    public static boolean enableAlarmFilter(String hostId,
                                            String alarmFilteringId) {
        return runAlarmFilteringAction(hostId, alarmFilteringId, ActionInput.ALARM_FILTER_ACTION_ENABLE);
    }

    public static boolean verifyEnabledAlarmFilter(String hostId,
                                                   String alarmFilteringId) {
        assertNotNull(alarmFilteringId);
        String url = "agents/hosts/" + hostId + "/alarm-filtering/" + alarmFilteringId;
        AlarmFilter alarmFilter = WebTestClientFactory.getOneByURI(url, AlarmFilter.class);
        return alarmFilter.getEnabled();
    }

    public static boolean disableAlarmFilter(String hostId,
                                             String alarmFilteringId) {
        return runAlarmFilteringAction(hostId, alarmFilteringId, ActionInput.ALARM_FILTER_ACTION_DISABLE);
    }

    public static boolean verifyDisabledAlarmFilter(String hostId,
                                                    String alarmFilteringId) {
        assertNotNull(alarmFilteringId);
        return !verifyEnabledAlarmFilter(hostId, alarmFilteringId);
    }

    public static OsUsersGroups getOsUsersAndGroups(String hostId) {
        String url = "agents/hosts/" + hostId + "/os-users-groups";
        return WebTestClientFactory.getOneByURI(url, OsUsersGroups.class);
    }

    public static int getFirstSystemUserId(String hostId) {
        OsUsersGroups osUsersGroups = getOsUsersAndGroups(hostId);
        List<OsObject> users = osUsersGroups.getUsers();
        return users.get(0).getId();
    }
}
