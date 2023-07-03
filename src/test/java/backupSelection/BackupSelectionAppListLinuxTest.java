package backupSelection;

import static com.atempo.TestTags.APP_LIST;
import static com.atempo.TestTags.LINUX_AGENT;

import com.atempo.tina.testAuto.restAPI.resources.AgentsResourceTest;
import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

@Tag(LINUX_AGENT)
@Tag(APP_LIST)
@TestMethodOrder(OrderAnnotation.class)
class BackupSelectionAppListLinuxTest extends AbstractBackupSelectionTest {


  @Override
  String getRetrieveOrCreateHost() throws Exception {
    return UtilHostTest.retrieveOrCreateHostLinux();
  }

  @Override
  String getBackupSelectionPath() throws Exception {
    return PATH_FOR_BACKUP_SELECTION_APP_LIST_LINUX_BACKUP;
  }

  @Override
  String getOtherBackupSelectionPath() {
    return PATH_FOR_BACKUP_SELECTION_APP_LIST_LINUX_BACKUP_OTHER;
  }

  @Override
  String getRetrieveOrCreateApp() {
    return AgentsResourceTest.retrieveOrCreateAppListLinux();
  }
}
