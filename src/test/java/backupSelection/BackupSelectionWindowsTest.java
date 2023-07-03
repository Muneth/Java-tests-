package backupSelection;

import static com.atempo.TestTags.WINDOWS_AGENT;

import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
import org.junit.jupiter.api.Tag;

@Tag(WINDOWS_AGENT)
class BackupSelectionWindowsTest extends AbstractBackupSelectionTest {

  @Override
  String getRetrieveOrCreateHost() throws Exception {
    return UtilHostTest.retrieveOrCreateHostWindows2016();
  }

  @Override
  String getBackupSelectionPath() {
    return PATH_BACKUP_SELECTION_HOST_AND_APP_FS_WINDOWS_BACKUP;
  }

  @Override
  String getOtherBackupSelectionPath() throws Exception {
    return PATH_BACKUP_SELECTION_HOST_AND_APP_FS_WINDOWS_BACKUP_OTHER;
  }

  @Override
  String getRetriveSRA() throws Exception {
    return UtilHostTest.retrieveSRAWindows2016();
  }

}