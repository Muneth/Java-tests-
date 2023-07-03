
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import com.atempo.tina.testAuto.restAPI.resources.StorageResourceTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag(CATALOG)
class ArchiveTest extends Setup {
    private static String archiveId;

    @Test
    @Order(10)
    void createArchive() throws Exception {
        archiveId =
                ArchivesResourceTest.createArchive(UtilHostTest.retrieveOrCreateHostLinux(), retrieveOrCreatePoolVLSLinux());
        assertNotNull(archiveId);
    }

    @Test
    @Order(20)
    void verifyCreateArchiveById() {
        assumeTrue(archiveId != null);
        assertTrue(ArchivesResourceTest.existArchiveById(archiveId));
    }

    @Test
    @Order(30)
    void verifyCreateArchiveByList() {
        List<ArchiveFolder> archiveFolders = ArchivesResourceTest.getArchiveFolderByHostName(HOST_NAME_LINUX);
        assertFalse(archiveFolders.isEmpty());
        boolean found = false;
        for (ArchiveFolder archiveFolder : archiveFolders) {
            if (archiveFolder.getPublicId().contains(archiveId)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    @Order(40)
    void editArchive() throws Exception {
        assumeTrue(archiveId != null);
        archiveId =
                ArchivesResourceTest.updateArchive(UtilHostTest.retrieveOrCreateHostLinux(), retrieveOrCreatePoolVLSLinux(),
                        archiveId);
        assertNotNull(archiveId);
    }

    @Test
    @Order(50)
    void verifyEditArchive() {
        assumeTrue(archiveId != null);
        assertTrue(ArchivesResourceTest.verifyUpdateArchive(archiveId));
    }

    @Test
    @Order(60)
    void suppressionArchive() {
        assumeTrue(archiveId != null);
        assertTrue(ArchivesResourceTest.deleteArchive(archiveId));
    }

    @Test
    @Order(70)
    void verifySuppressionArchive() {
        assumeTrue(archiveId != null);
        assertTrue(ArchivesResourceTest.verifyDeleteArchive(archiveId));
    }

    @Test
    @Order(80)
    void deletePool() throws Exception {
        assumeTrue(CLEAN_AFTER_TEST);
        assertTrue(UtilPool.deletePool(UtilPool.retrieveOrCreatePoolVLSLinux()));
    }

    @Test
    @Order(90)
    void deleteStorage() throws Exception {
        assumeTrue(CLEAN_AFTER_TEST);
        assertTrue(StorageResourceTest.deleteStorage(StorageResourceTest.retrieveOrCreateStorageVLSLinux()));
    }

    @Test
    @Order(100)
    void deleteHost() throws Exception {
        assumeTrue(CLEAN_AFTER_TEST);
        assertTrue(UtilHostTest.deleteHost(UtilHostTest.retrieveOrCreateHostLinux()));
    }
}
