package backupSelection;

import static com.atempo.TestTags.APP_FS;
import static com.atempo.TestTags.LINUX_AGENT;

import com.atempo.tina.testAuto.restAPI.resources.AgentsResourceTest;
import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
import org.junit.jupiter.api.Tag;


@Tag(LINUX_AGENT)
@Tag(APP_FS)
class BackupSelectionWithSharpAppFSLinuxTest extends AbstractBackupSelectionTest {

  private static String backup_selection_id;

  @Override
  String getRetrieveOrCreateHost() throws Exception {
    return UtilHostTest.retrieveOrCreateHostLinux();
  }

  @Override
  String getBackupSelectionPath() {
    return PATH_BACKUP_SELECTION_HOST_AND_APP_FS_NDMP_LINUX_BACKUP + "/" + "Test#with#Sharp#In#Path";
  }

  @Override
  String getOtherBackupSelectionPath() throws Exception {
    return PATH_BACKUP_SELECTION_HOST_AND_APP_FS_NDMP_LINUX_BACKUP + "/" + "Test#with#Sharp#In#Path_other";
  }

  @Override
  String getRetrieveOrCreateApp() {
    return AgentsResourceTest.retrieveOrCreateAppFSLinux();
  }

}
