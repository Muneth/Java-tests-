
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.util.ArrayList;
import java.util.List;
public class ArchivesResourceTest {

    public static List<ArchiveFolder> getArchiveList() {
        String url = "archives";
        return WebTestClientFactory.getListByURI(url, ArchiveFolder.class);
    }

    public static List<ArchiveFolder> getArchiveFolderByHostName(String hostName) {
        List<ArchiveFolder> allArchives = getArchiveList();
        List<ArchiveFolder> hostArchives = new ArrayList<>();
        allArchives.forEach((archiveFolder) -> {
            if (archiveFolder.getName().contains(hostName)) {
                hostArchives.add(archiveFolder);
            }
        });
        return hostArchives;
    }


    public static ArchiveFolder getArchiveById(String archiveId) {
        String url = "archives/" + archiveId;
        assertNotNull(archiveId);
        return WebTestClientFactory.getOneByURI(url, ArchiveFolder.class);
    }

    public static boolean existArchiveById(String archiveId) {
        ArchiveFolder archiveFolder = getArchiveById(archiveId);
        return archiveFolder != null && archiveFolder.getPublicId().equals(archiveId);
    }


    public static String createArchive(String linuxHostId,
                                       String poolId) {
        int userId = getFirstSystemUserId(linuxHostId);
        OsType osType = new OsType();
        osType.setIsUnix(true);
        osType.setName("linux");
        osType.setType(2);
        String url = "archives";
        ArchiveFolderCreationInput archiveInput = new ArchiveFolderCreationInput();
        archiveInput.setName(ARCHIVE_NAME);
        archiveInput.setHostId(linuxHostId);
        archiveInput.setArchiveType(ArchiveFolderType.ARCHIVE_FOLDER_TYPE_TINA);
        archiveInput.setDescription("test");
        archiveInput.setOsType(osType);
        archiveInput.setFormatCart(Job.FORMATCART_SIDF);
        archiveInput.setUseCacheOnServer(false);
        archiveInput.setMainPoolId(poolId);
        archiveInput.setOwnerId(userId);
        archiveInput.setGroupId(userId);
        return getIdForCreateStatusOk(url, archiveInput);
    }

    public static String updateArchive(String linuxHostId,
                                       String poolId,
                                       String archiveId) {
        String url = "archives/" + archiveId;
        assertNotNull(archiveId);
        int user_id = getFirstSystemUserId(linuxHostId);
        OsType osType = new OsType();
        osType.setIsUnix(true);
        osType.setName("linux");
        osType.setType(2);
        ArchiveFolderEditionInput archiveInput = WebTestClientFactory.getOneByURI(url, ArchiveFolderEditionInput.class);
        archiveInput.setName(ARCHIVE_NAME);
        archiveInput.setArchiveType(ArchiveFolderType.ARCHIVE_FOLDER_TYPE_TINA);
        archiveInput.setDescription(UtilRequestHttp.MODIF);
        archiveInput.setOsType(osType);
        archiveInput.setFormatCart(Job.FORMATCART_SIDF);
        archiveInput.setUseCacheOnServer(false);
        archiveInput.setMainPoolId(poolId);
        archiveInput.setOwnerId(user_id);
        archiveInput.setGroupId(user_id);
        return getIdForUpdateStatusOk(url, archiveInput);
    }

    public static boolean verifyUpdateArchive(String archiveId) {
        String url = "archives/" + archiveId;
        ArchiveFolder archive = WebTestClientFactory.getOneByURI(url, ArchiveFolder.class);
        assertTrue(archive.getDescription().contains(UtilRequestHttp.MODIF));
        return true;
    }

    public static boolean runArchiveAction(String archiveId,
                                           String actionName) {
        assertNotNull(archiveId);
        String url = "archives/actions";
        ActionInput archivesActionInputDelete = new ArchivesActionInputDelete();
        List<String> publicIds = new ArrayList<>();
        publicIds.add(archiveId);
        archivesActionInputDelete.setPublicIds(publicIds);
        archivesActionInputDelete.setActionName(actionName);
        WebTestClientFactory.putThatExpectedStatusOk(url, archivesActionInputDelete);
        return true;
    }

    public static boolean deleteArchive(String archiveId) {
        assumeTrue(CLEAN_AFTER_TEST);
        return runArchiveAction(archiveId, ActionInput.ARCHIVE_ACTION_DELETE);
    }

    public static boolean verifyDeleteArchive(String archiveId) {
        assertNotNull(archiveId);
        String url = "archives" + archiveId;
        List<String> errors = WebTestClientFactory.getListByURI(url, String.class, 404);
        assertTrue(WebTestClientFactory.expectedErrors(errors, 404, "Not Found"));
        return true;
    }

    public static boolean deleteAllArchiveForHostName(String hostName) {
        boolean hasDeleteSomething = false;
        List<ArchiveFolder> archiveFolders = getArchiveFolderByHostName(hostName);
        for (ArchiveFolder archiveFolder : archiveFolders) {
            deleteArchive(archiveFolder.getPublicId());
            hasDeleteSomething = true;
        }
        return hasDeleteSomething;
    }
}
