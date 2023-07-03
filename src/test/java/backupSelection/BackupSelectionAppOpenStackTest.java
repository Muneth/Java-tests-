package backupSelection;

import static com.atempo.TestTags.LINUX_AGENT;
import static com.atempo.TestTags.OPENSTACK;

import com.atempo.tina.testAuto.restAPI.resources.AgentsResourceTest;
import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
import com.atempo.tina.testAuto.restAPI.utils.UtilRequestHttp;
import org.json.JSONObject;
import org.junit.jupiter.api.Tag;

@Tag(LINUX_AGENT)
@Tag(OPENSTACK)
class BackupSelectionAppOpenStackTest extends AbstractBackupSelectionTest {

  @Override
  String getRetrieveOrCreateHost() throws Exception {
    return UtilHostTest.retrieveOrCreateHostLinux();
  }

  @Override
  String getBackupSelectionPath() throws Exception {
    String data =
        new JSONObject().put("isDirectoryOnly", false).put("presentationPathFull", "/Virtual Machines").toString();
    return UtilRequestHttp.recupPathBackup(SERVER_URL, AgentsResourceTest.retrieveOrCreateAppOpenStack(), getToken(),
                                           USER_LINUX, LINUX_USER_PASSWORD, data, OPENSTACK_BACKUP_VM_NAME);
  }

  @Override
  String getOtherBackupSelectionPath() throws Exception {
    String data = new JSONObject().put("isDirectoryOnly", false).put("presentationPathFull", "/Virtual Machines")
                                  .toString(); // path discovery using the web ui in the test auto, browse TEST_AppOpenStackLinux to create a backup selection
    return UtilRequestHttp.recupPathBackup(SERVER_URL, AgentsResourceTest.retrieveOrCreateAppOpenStack(), getToken(),
                                           USER_LINUX, LINUX_USER_PASSWORD, data, OPENSTACK_BACKUP_VM_NAME_OTHER);
  }

  @Override
  String getRetrieveOrCreateApp() {
    return AgentsResourceTest.retrieveOrCreateAppOpenStack();
  }

}