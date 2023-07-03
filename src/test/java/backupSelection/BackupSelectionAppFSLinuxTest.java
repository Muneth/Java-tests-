package backupSelection;

import static com.atempo.TestTags.APP_FS;
        import static com.atempo.TestTags.LINUX_AGENT;

        import com.atempo.tina.testAuto.restAPI.resources.AgentsResourceTest;
        import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
        import org.junit.jupiter.api.Tag;


@Tag(LINUX_AGENT)
@Tag(APP_FS)
class BackupSelectionAppFSLinuxTest extends AbstractBackupSelectionTest {

  @Override
  String getRetrieveOrCreateHost() throws Exception {
    return UtilHostTest.retrieveOrCreateHostLinux();
  }

  @Override
  String getBackupSelectionPath() {
    return PATH_BACKUP_SELECTION_HOST_AND_APP_FS_NDMP_LINUX_BACKUP;
  }

  @Override
  String getOtherBackupSelectionPath() {
    return PATH_BACKUP_SELECTION_HOST_AND_APP_FS_NDMP_LINUX_BACKUP_OTHER;
  }

  @Override
  String getRetrieveOrCreateApp() {
    return AgentsResourceTest.retrieveOrCreateAppFSLinux();
  }

}
