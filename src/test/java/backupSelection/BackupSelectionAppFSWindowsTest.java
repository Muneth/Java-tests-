package backupSelection;

import static com.atempo.TestTags.APP_FS;
import static com.atempo.TestTags.WINDOWS_AGENT;

import com.atempo.tina.testAuto.restAPI.resources.AgentsResourceTest;
import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestMethodOrder;

@Tag(WINDOWS_AGENT)
@Tag(APP_FS)
@TestMethodOrder(OrderAnnotation.class)
class BackupSelectionAppFSWindowsTest extends AbstractBackupSelectionTest {

  @Override
  String getRetrieveOrCreateHost() throws Exception {
    return UtilHostTest.retrieveOrCreateHostWindows2016();
  }

  @Override
  String getBackupSelectionPath() throws Exception {
    return PATH_BACKUP_SELECTION_HOST_AND_APP_FS_WINDOWS_BACKUP;
  }

  @Override
  String getOtherBackupSelectionPath() {
    return PATH_BACKUP_SELECTION_HOST_AND_APP_FS_WINDOWS_BACKUP_OTHER;
  }

  @Override
  String getRetrieveOrCreateApp() {
    return AgentsResourceTest.retrieveOrCreateAppFSWindows();
  }

  @Override
  String getRetriveSRA() throws Exception {
    return UtilHostTest.retrieveSRAWindows2016();
  }

}