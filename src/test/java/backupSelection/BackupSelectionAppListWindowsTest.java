package backupSelection;

import static com.atempo.TestTags.APP_LIST;
import static com.atempo.TestTags.WINDOWS_AGENT;

import com.atempo.tina.testAuto.restAPI.resources.AgentsResourceTest;
import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
import org.junit.jupiter.api.Tag;

@Tag(WINDOWS_AGENT)
@Tag(APP_LIST)
class BackupSelectionAppListWindowsTest extends AbstractBackupSelectionTest {

  @Override
  String getRetrieveOrCreateHost() throws Exception {
    return UtilHostTest.retrieveOrCreateHostWindows2016();
  }

  @Override
  String getBackupSelectionPath() {
    return PATH_FOR_BACKUP_SELECTION_APP_LIST_WINDOWS_BACKUP;
  }

  @Override
  String getOtherBackupSelectionPath() throws Exception {
    return PATH_FOR_BACKUP_SELECTION_APP_LIST_WINDOWS_BACKUP_OTHER;
  }

  @Override
  String getRetrieveOrCreateApp() {
    return AgentsResourceTest.retrieveOrCreateAppListWindows();
  }

  @Override
  String getRetriveSRA() throws Exception {
    return UtilHostTest.retrieveSRAWindows2016();
  }

}
