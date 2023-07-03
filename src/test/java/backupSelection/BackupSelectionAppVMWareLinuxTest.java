package backupSelection;

import static com.atempo.TestTags.APP_VMWARE;
import static com.atempo.TestTags.LINUX_AGENT;

import com.atempo.tina.testAuto.restAPI.resources.AgentsResourceTest;
import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
import org.junit.jupiter.api.Tag;

@Tag(LINUX_AGENT)
@Tag(APP_VMWARE)
class BackupSelectionAppVMWareLinuxTest extends AbstractBackupSelectionTest {

  @Override
  String getRetrieveOrCreateHost() throws Exception {
    return UtilHostTest.retrieveOrCreateHostLinux();
  }

  @Override
  String getBackupSelectionPath() {
    return PATH_BACKUP_SELECTION_APP_VMWARE_LINUX;
  }

  @Override
  String getOtherBackupSelectionPath() throws Exception {
    return PATH_BACKUP_SELECTION_APP_VMWARE_LINUX_OTHER;
  }

  @Override
  String getRetrieveOrCreateApp() {
    return AgentsResourceTest.retrieveOrCreateAppVMWare(27100);
  }

}