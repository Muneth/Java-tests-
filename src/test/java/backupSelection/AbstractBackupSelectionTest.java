package backupSelection;

import static com.atempo.TestTags.BACKUP_SELECTION;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.atempo.tina.restapi.model.ActionInput;
import com.atempo.tina.testAuto.restAPI.Setup;
import com.atempo.tina.testAuto.restAPI.resources.AgentsResourceTest;
import com.atempo.tina.testAuto.restAPI.resources.BackupSelectionsResourceTest;
import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@Tag(BACKUP_SELECTION)
@TestMethodOrder(OrderAnnotation.class)
abstract class AbstractBackupSelectionTest extends Setup {

  /**
   * @return the retrieved or created host id
   */
  abstract String getRetrieveOrCreateHost() throws Exception;

  /**
   * @return the retrieved or created host id
   */
  String getRetrieveOrCreateApp() {
    return null;
  }

  /**
   * @return the backup selection path
   */
  abstract String getBackupSelectionPath() throws Exception;

  abstract String getOtherBackupSelectionPath() throws Exception;

  private static String backupSelectionId;
  private static String otherBackupSelectionId;

  String doCreateBackupSelection() throws Exception {
    String appOrHost = getRetrieveOrCreateApp();
    if (appOrHost == null) {
      appOrHost = getRetrieveOrCreateHost();
    }
    backupSelectionId = BackupSelectionsResourceTest.createBackupSelection(appOrHost, getBackupSelectionPath());
    return backupSelectionId;
  }

  String doCreateOtherBackupSelection() throws Exception {
    String appOrHost = getRetrieveOrCreateApp();
    if (appOrHost == null) {
      appOrHost = getRetrieveOrCreateHost();
    }
    otherBackupSelectionId =
        BackupSelectionsResourceTest.createBackupSelection(appOrHost, getOtherBackupSelectionPath());
    return otherBackupSelectionId;
  }


  @Test
  @Order(10)
  void createBackupSelection() throws Exception {
    assertNotNull(doCreateBackupSelection());
  }

  @Test
  @Order(11)
  void checkBackupSelectionById() {
    assertTrue(BackupSelectionsResourceTest.verifCreateBackupSelectionById(backupSelectionId));
  }

  @Test
  @Order(12)
  void checkBackupSelectionByList() {
    assertTrue(BackupSelectionsResourceTest.verifCreateBackupSelectionByList(backupSelectionId));
  }

  @Test
  @Order(15)
  void createOtherBackupSelection() throws Exception {
    assertNotNull(doCreateOtherBackupSelection());
  }

  @Test
  @Order(20)
  void editBackupSelection() {
    assertTrue(BackupSelectionsResourceTest.editBackupSelection(backupSelectionId));
  }

  @Test
  @Order(21)
  void verifEditBackupSelection() {
    assertTrue(BackupSelectionsResourceTest.verifEditBackupSelection(backupSelectionId));
  }


  @Test
  @Order(25)
  void bulkEditionBackupSelectionReplace() {
    assertTrue(
        BackupSelectionsResourceTest.bulkEditionBackupSelectionReplace(backupSelectionId, otherBackupSelectionId));
  }

  @Test
  @Order(26)
  void checkBulkEditionBackupSelectionReplace() {
    assertTrue(
        BackupSelectionsResourceTest.verifBulkEditionBackupSelectionReplace(backupSelectionId, otherBackupSelectionId));
  }

  @Test
  @Order(27)
  void bulkEditionBackupSelectionMerge() {
    assertTrue(BackupSelectionsResourceTest.bulkEditionBackupSelectionMerge(backupSelectionId, otherBackupSelectionId));
  }

  @Test
  @Order(28)
  void checkBulkEditionBackupSelectionMerge() {
    assertTrue(
        BackupSelectionsResourceTest.verifBulkEditionBackupSelectionMerge(backupSelectionId, otherBackupSelectionId));
  }


  @Test
  @Order(30)
  void deleteBackupSelection() {
    assumeTrue(CLEAN_AFTER_TEST);
    assertTrue(BackupSelectionsResourceTest.deleteBackupSelection(backupSelectionId));
  }

  @Test
  @Order(31)
  void verifDeletionBackupSelection() {
    assumeTrue(CLEAN_AFTER_TEST);
    assertTrue(BackupSelectionsResourceTest.verifDeletionBackupSelection(backupSelectionId));
  }

  /**
   * Override this if Windows Host to get SRA
   *
   * @return null or SRA id (for example using UtilHostTest.retrieveSRAWindows2016())
   */
  String getRetriveSRA() throws Exception {
    return null;
  }

  @Test
  @Order(53)
  void removeApp() {
    assumeTrue(CLEAN_AFTER_TEST);
    String app = getRetrieveOrCreateApp();
    if (app != null) {
      assertTrue(AgentsResourceTest.deleteAgent(ActionInput.AGENT_ACTION_DELETE, app));
      assertTrue(AgentsResourceTest.checkRemoveApplication(app));
    }
  }

  @Test
  @Order(90)
  void deleteSra() throws Exception {
    assumeTrue(CLEAN_AFTER_TEST);
    String sraId = getRetriveSRA();
    if (sraId != null) {
      assertTrue(UtilHostTest.deleteSra(sraId));
    }
  }

  @Test
  @Order(100)
  void deleteHost() throws Exception {
    assumeTrue(CLEAN_AFTER_TEST);
    assertTrue(UtilHostTest.deleteHost(getRetrieveOrCreateHost()));
  }

}