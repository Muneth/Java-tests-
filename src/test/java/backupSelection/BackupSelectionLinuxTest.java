package backupSelection;

import static com.atempo.TestTags.LINUX_AGENT;

import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
import org.junit.jupiter.api.Tag;

@Tag(LINUX_AGENT)
class BackupSelectionLinuxTest extends AbstractBackupSelectionTest {

  @Override
  String getRetrieveOrCreateHost() throws Exception {
    return UtilHostTest.retrieveOrCreateHostLinux();
  }

  @Override
  String getBackupSelectionPath() {
    return PATH_BACKUP_SELECTION_HOST_AND_APP_FS_NDMP_LINUX_BACKUP;
  }

  @Override
  String getOtherBackupSelectionPath() throws Exception {
    return PATH_BACKUP_SELECTION_HOST_AND_APP_FS_NDMP_LINUX_BACKUP_OTHER;
  }

}
