package com.atempo.tina.testAuto.restAPI.resources;

import static com.atempo.tina.UtilConstants.CLOUD_AWS_S3_REPOSITORY_PRODUCT_ID;
import static com.atempo.tina.UtilConstants.XDCS_REPOSITORY_PRODUCT_ID;
import static com.atempo.tina.restapi.model.ActionInput.STORAGE_ACTION_DELETE;
import static com.atempo.tina.restapi.model.storage.library.LibraryCreationInput.PHYSICAL_LIBRARY;
import static com.atempo.tina.testAuto.restAPI.Setup.ACTIVE_SCALE_BUCKET_NAME;
import static com.atempo.tina.testAuto.restAPI.Setup.ACTIVE_SCALE_PORT;
import static com.atempo.tina.testAuto.restAPI.Setup.ACTIVE_SCALE_SECRET;
import static com.atempo.tina.testAuto.restAPI.Setup.ACTIVE_SCALE_TOKEN_ID;
import static com.atempo.tina.testAuto.restAPI.Setup.ACTIVE_SCALE_URL;
import static com.atempo.tina.testAuto.restAPI.Setup.ADE_PASSWORD;
import static com.atempo.tina.testAuto.restAPI.Setup.ADE_PORT;
import static com.atempo.tina.testAuto.restAPI.Setup.ADE_SERVER_NAME;
import static com.atempo.tina.testAuto.restAPI.Setup.ADE_USER;
import static com.atempo.tina.testAuto.restAPI.Setup.CLOUD_ACCESS_KEY_ID;
import static com.atempo.tina.testAuto.restAPI.Setup.CLOUD_ACCESS_KEY_ID_PASSWD;
import static com.atempo.tina.testAuto.restAPI.Setup.CLOUD_BUCKET_NAME;
import static com.atempo.tina.testAuto.restAPI.Setup.CLOUD_PORT;
import static com.atempo.tina.testAuto.restAPI.Setup.CLOUD_SERVER_URL;
import static com.atempo.tina.testAuto.restAPI.Setup.CLOUD_TYPE;
import static com.atempo.tina.testAuto.restAPI.Setup.DRIVE_SERIAL_NUMBER;
import static com.atempo.tina.testAuto.restAPI.Setup.DRIVE_USERNAME;
import static com.atempo.tina.testAuto.restAPI.Setup.HOST_NAME_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.LIBRARY_NEW_SERIAL_NUMBER;
import static com.atempo.tina.testAuto.restAPI.Setup.LIBRARY_SERIAL_NUMBER;
import static com.atempo.tina.testAuto.restAPI.Setup.PATH_STORAGE_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.PATH_STORAGE_WINDOWS;
import static com.atempo.tina.testAuto.restAPI.Setup.SCALITY_BUCKET_NAME;
import static com.atempo.tina.testAuto.restAPI.Setup.SCALITY_PORT;
import static com.atempo.tina.testAuto.restAPI.Setup.SCALITY_SECRET;
import static com.atempo.tina.testAuto.restAPI.Setup.SCALITY_TOKEN_ID;
import static com.atempo.tina.testAuto.restAPI.Setup.SCALITY_URL;
import static com.atempo.tina.testAuto.restAPI.Setup.SERVER_NAME;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_ACTIVE_SCALE_CLOUD_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_AWS_CLOUD_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_CLOUD_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_DEDUP_HSS_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_DEDUP_HVDS_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_DRIVE_SAN_NETWORK_NAME;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_ENHANCED_DISK_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_INTERNAL_DEVICE_LIBRARY_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_LIBRARY_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_SCALITY_CLOUD_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_SWARM_CLOUD_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_VLS_LINUX;
import static com.atempo.tina.testAuto.restAPI.Setup.STORAGE_VLS_WINDOWS;
import static com.atempo.tina.testAuto.restAPI.Setup.SWARM_BUCKET_NAME;
import static com.atempo.tina.testAuto.restAPI.Setup.SWARM_PORT;
import static com.atempo.tina.testAuto.restAPI.Setup.SWARM_SECRET;
import static com.atempo.tina.testAuto.restAPI.Setup.SWARM_TOKEN_ID;
import static com.atempo.tina.testAuto.restAPI.Setup.SWARM_URL;
import static com.atempo.tina.testAuto.restAPI.Setup.getToken;
import static com.atempo.tina.testAuto.restAPI.resources.WebTestClientFactory.getIdForCreateStatusOk;
import static com.atempo.tina.testAuto.restAPI.resources.WebTestClientFactory.getIdForUpdateStatusOk;
import static com.atempo.tina.testAuto.restAPI.resources.WebTestClientFactory.postForCreationStatusOther;
import static com.atempo.tina.testAuto.restAPI.resources.WebTestClientFactory.postForEditionStatusOther;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.atempo.tina.cpackage.TNRob;
import com.atempo.tina.restapi.model.ActionInput;
import com.atempo.tina.restapi.model.DeviceDescriptor;
import com.atempo.tina.restapi.model.EnvVar;
import com.atempo.tina.restapi.model.storage.Storage;
import com.atempo.tina.restapi.model.storage.StorageCloud;
import com.atempo.tina.restapi.model.storage.StorageDrive;
import com.atempo.tina.restapi.model.storage.VlsCreationInput;
import com.atempo.tina.restapi.model.storage.VlsEditionInput;
import com.atempo.tina.restapi.model.storage.cloud.CloudS3CreationInput;
import com.atempo.tina.restapi.model.storage.cloud.CloudS3EditionInput;
import com.atempo.tina.restapi.model.storage.deduplication.DedupCreationInput;
import com.atempo.tina.restapi.model.storage.deduplication.DedupEditionInput;
import com.atempo.tina.restapi.model.storage.drive.DriveCreationInput;
import com.atempo.tina.restapi.model.storage.drive.DriveEditionInput;
import com.atempo.tina.restapi.model.storage.enhanced.EnhancedDiskCreationInput;
import com.atempo.tina.restapi.model.storage.enhanced.EnhancedDiskEditionInput;
import com.atempo.tina.restapi.model.storage.hss.HssCreationInput;
import com.atempo.tina.restapi.model.storage.hss.HssEditionInput;
import com.atempo.tina.restapi.model.storage.library.LibraryContent;
import com.atempo.tina.restapi.model.storage.library.LibraryCreationInput;
import com.atempo.tina.restapi.model.storage.library.LibraryEditionInput;
import com.atempo.tina.restapi.model.storage.library.LibraryProperties;
import com.atempo.tina.testAuto.restAPI.utils.UtilHostTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class StorageResourceTest {

    private static final int CLOUD_TYPE_S3 = 2;

    public static final int SWARM_REPOSITORY_PRODUCT_ID = 11;
    public static final int SCALITY_REPOSITORY_PRODUCT_ID = 13;
    public static final int ACTIVE_SCALE_REPOSITORY_PRODUCT_ID = 12;
    public static final int DRIVE_TYPE_EMC_TAPE_EMULATOR = 37;
    public static final int DRIVE_TYPE_CENTERA = 52;
    public static final int DRIVE_TYPE_HPE_ULTRIUM_6 = 88;

    /**
     * Map with key Storage Name and value Storage Public Id
     */
    private static final Map<String, String> cachedStorages = new HashMap<>();

    private static String createRepositoryCloudS3(String storageName,
                                                  String serverUrlAddress,
                                                  String port,
                                                  int repositoryProductId,
                                                  String accessKeyId,
                                                  String accessKeyIdPasswd,
                                                  String bucketName) {

        CloudS3CreationInput input = new CloudS3CreationInput();
        input.setComment("Storage created by test");
        input.setName(storageName);
        input.setBucketName(bucketName);
        if (port != null && !port.isEmpty()) {
            serverUrlAddress += ":" + Integer.parseInt(port);
        }
        input.setServerUrlAddress(serverUrlAddress);
        input.setCloudType(CLOUD_TYPE_S3); // Cloud Type S3
        input.setObjectLockMode("NONE");
        input.setAccelerateNetworkName(null);
        input.setAccessKeyId(accessKeyId);
        input.setAccessKeyIdPasswd(accessKeyIdPasswd);
        input.setBucketAccelerate(false);
        input.setBucketVersioning(true); // TODO voir si on met à true ou false pour les tests
        input.setCheckIntegrityWithMd5(false);
        input.setCreateBucket(true);
        input.setDebugFile(null);
        input.setDebugLevel(null);
        input.setModifyPartSize(false);
        input.setPartSize(null);
        input.setProxyHttpNetworkName(null);
        input.setProxyHttpPasswd(null);
        input.setProxyHttpUser(null);
        input.setUseV4Signature(false);
        input.setComment("this is a comment");
        input.setRepositoryProductId(repositoryProductId);

        String id = WebTestClientFactory.getIdForCreateStatusOk("storages/cloud/s3", input);
        if (id != null) {
            cachedStorages.put(storageName, id);
        }
        return id;
    }

    private static String createStorageVls(String hostId,
                                           String pathStorage,
                                           String storageName) {
        assertNotNull(hostId);
        String uri = "storages/library/vls";
        VlsCreationInput storageCreation = new VlsCreationInput();
        storageCreation.setHostId(hostId);
        storageCreation.setName(storageName);
        storageCreation.setNbDrives(2);
        storageCreation.setNbCartridge(10);
        storageCreation.setCartridgeSize(1000);
        storageCreation.setPath(pathStorage);
        String id = WebTestClientFactory.getIdForCreateStatusOk(uri, storageCreation);
        if (id != null) {
            cachedStorages.put(storageName, id);
        }
        return id;
    }

    public static String createStorageDrive(String hostId,
                                            String pathStorage,
                                            String deviceDescriptorHostId,
                                            String storageName,
                                            String serialNumber,
                                            int driveType,
                                            boolean isWorm) throws Exception {
        assertNotNull(hostId);
        String uri = "storages/drive";
        String deviceDescriptorPath = pathStorage + "/" + getToken();
        DriveCreationInput driveCreation = new DriveCreationInput();
        driveCreation.setHostPublicId(hostId);
        driveCreation.setIsSan(false);
        driveCreation.setIndex(0);
        driveCreation.setType(driveType);
        List<DeviceDescriptor> deviceDescripterList = new ArrayList<>();
        DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
        deviceDescriptor.setIsEnabled(true);
        deviceDescriptor.setPath(deviceDescriptorPath);
        deviceDescriptor.setHostId(deviceDescriptorHostId);
        deviceDescripterList.add(deviceDescriptor);
        driveCreation.setDevicesDescriptor(deviceDescripterList);
        driveCreation.setComment("this is a comment");
        driveCreation.setName(storageName);
        driveCreation.setIsWorm(isWorm);
        driveCreation.setSerialNumber(serialNumber);
        String id = getIdForCreateStatusOk(uri, driveCreation);
        if (id != null) {
            cachedStorages.put(storageName, id);
        }
        return id;
    }

    private static String createStorageDriveSan(String networkName,
                                                String pathStorage,
                                                String deviceDescriptorHostId,
                                                String storageName,
                                                String serialNumber) throws Exception {
        assertNotNull(networkName);
        String uri = "storages/drive";
        String deviceDescriptorPath = pathStorage + "/" + getToken();
        DriveCreationInput driveCreation = new DriveCreationInput();
        driveCreation.setNetworkName(networkName);
        driveCreation.setIsSan(true);
        driveCreation.setIndex(0);
        driveCreation.setType(88);
        DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
        deviceDescriptor.setHostId(deviceDescriptorHostId);
        deviceDescriptor.setPath(deviceDescriptorPath);
        deviceDescriptor.setIsEnabled(true);
        List<DeviceDescriptor> deviceDescriptorList = new ArrayList<>();
        deviceDescriptorList.add(deviceDescriptor);
        driveCreation.setDevicesDescriptor(deviceDescriptorList);
        driveCreation.setComment("This is a comment");
        driveCreation.setName(storageName);
        driveCreation.setSerialNumber(serialNumber);
        String id = getIdForCreateStatusOk(uri, driveCreation);
        if (id != null) {
            cachedStorages.put(storageName, id);
        }
        return id;
    }

    private static String createStorageDiskDrive(String hostId,
                                                 String pathStorage,
                                                 String deviceDescriptorHostId,
                                                 String storageName) {
        assertNotNull(hostId);
        String uri = "storages/drive";
        DriveCreationInput driveCreation = new DriveCreationInput();
        driveCreation.setHostPublicId(hostId);
        driveCreation.setIsSan(false);
        driveCreation.setIndex(0);
        driveCreation.setType(13);
        driveCreation.setFileSize(256);
        DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
        deviceDescriptor.setHostId(deviceDescriptorHostId);
        deviceDescriptor.setPath(pathStorage);
        deviceDescriptor.setIsEnabled(true);
        List<DeviceDescriptor> deviceDescriptorList = new ArrayList<>();
        deviceDescriptorList.add(deviceDescriptor);
        driveCreation.setDevicesDescriptor(deviceDescriptorList);
        driveCreation.setComment("This is a comment");
        driveCreation.setName(storageName);
        String id = getIdForCreateStatusOk(uri, driveCreation);
        if (id != null) {
            cachedStorages.put(storageName, id);
        }
        return id;
    }

    private static String createStorageLibrary(String hostId,
                                               String deviceDescriptorHostId,
                                               String storageName,
                                               String internalDeviceName,
                                               String serialNumber) {
        assertNotNull(hostId);
        String uri = "storages/library";
        LibraryCreationInput storageCreation = new LibraryCreationInput();
        storageCreation.setName(storageName);
        List<DriveCreationInput> deviceList = new ArrayList<>();
        DriveCreationInput driveCreationInput = new DriveCreationInput();
        driveCreationInput.setHostPublicId(hostId);
        driveCreationInput.setIsSan(false);
        driveCreationInput.setIndex(1);
        driveCreationInput.setType(3);
        List<DeviceDescriptor> deviceDescriptorList = new ArrayList<>();
        DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
        deviceDescriptor.setPath("/tmp2");
        deviceDescriptor.setHostId(deviceDescriptorHostId);
        deviceDescriptor.setIsEnabled(true);
        deviceDescriptorList.add(deviceDescriptor);
        driveCreationInput.setDevicesDescriptor(deviceDescriptorList);
        driveCreationInput.setComment("This is a comment");
        driveCreationInput.setName(internalDeviceName);
        deviceList.add(driveCreationInput);
        storageCreation.setDeviceList(deviceList);
        storageCreation.setLibraryCategory(PHYSICAL_LIBRARY);
        storageCreation.setLibraryType(TNRob.TYPE_ROB_QUALSTAR_Q80);
        storageCreation.setSerialNumber(serialNumber);
        storageCreation.setHostName(HOST_NAME_LINUX);
        storageCreation.setDeviceDescriptor("This is a description of device");
        storageCreation.setComment("This is a comment");
        String id = getIdForCreateStatusOk(uri, storageCreation);
        if (id != null) {
            cachedStorages.put(storageName, id);
        }
        return id;
    }

    private static String createStorageDedupHss(String storageName) {

        String uri = "storages/deduplication/hss";
        HssCreationInput storageCreation = new HssCreationInput();
        storageCreation.setName(storageName);
        storageCreation.setPort(Integer.parseInt(ADE_PORT));
        storageCreation.setSize(2048);
        storageCreation.setUserName(ADE_USER);
        storageCreation.setUserPassword(ADE_PASSWORD);
        storageCreation.setCompress("Lzf");
        storageCreation.setNbDrives(4);
        storageCreation.setServerName(ADE_SERVER_NAME);
        storageCreation.setComment("this is a comment");
        String id = getIdForCreateStatusOk(uri, storageCreation);
        if (id != null) {
            cachedStorages.put(storageName, id);
        }
        return id;
    }

    private static String createStorageDedupHvds(String storageName) {

        String uri = "storages/deduplication/hvds";
        DedupCreationInput storageCreation = new DedupCreationInput();
        storageCreation.setName(storageName);
        storageCreation.setPort(Integer.parseInt(ADE_PORT));
        storageCreation.setUserName(ADE_USER);
        storageCreation.setUserPassword(ADE_PASSWORD);
        storageCreation.setServerName(ADE_SERVER_NAME);
        storageCreation.setComment("this is a comment");
        String id = getIdForCreateStatusOk(uri, storageCreation);
        if (id != null) {
            cachedStorages.put(storageName, id);
        }
        return id;
    }

    public static String createStorageEnhancedDisk(String storageName) {

        String uri = "storages/enhanced-disk";
        EnhancedDiskCreationInput storageCreation = new EnhancedDiskCreationInput();
        storageCreation.setName(storageName);
        storageCreation.setPort(6666);
        storageCreation.setRepositoryProductId(XDCS_REPOSITORY_PRODUCT_ID);
        storageCreation.setServerName(SERVER_NAME);
        storageCreation.setComment("this is a comment");
        String id = getIdForCreateStatusOk(uri, storageCreation);
        if (id != null) {
            cachedStorages.put(storageName, id);
        }
        return id;
    }

    public static List<String> tryToCreateStorageEnhancedDisk(String storageName) {

        String uri = "storages/enhanced-disk";
        EnhancedDiskCreationInput storageCreation = new EnhancedDiskCreationInput();
        storageCreation.setName(storageName);
        storageCreation.setPort(6666);
        storageCreation.setRepositoryProductId(XDCS_REPOSITORY_PRODUCT_ID);
        storageCreation.setServerName(SERVER_NAME);
        storageCreation.setComment("this is a comment");
        return postForCreationStatusOther(uri, storageCreation, 400);
    }


    public static List<Storage> getStorages() {
        String uri = "storages";
        return WebTestClientFactory.getListByURI(uri, Storage.class);
    }

    public static boolean verifyCreateStorageByList(String storageId) {
        assertNotNull(storageId);
        List<Storage> storages = getStorages();
        for (Storage storage : storages) {
            if (storage != null && storage.getPublicId().equals(storageId)) {
                return true;
            }
        }
        return false;
    }

    public static String retrieveStorageFromName(String storageName) {
        if (cachedStorages.containsKey(storageName)) {
            return cachedStorages.get(storageName);
        }
        String publicId = null;
        List<Storage> storageList = getStorages();
        for (Storage storage : storageList) {
            if (storage != null && storage.getName().equals(storageName)) {
                publicId = storage.getPublicId();
                break;
            }
        }
        return publicId;
    }

    private static Storage getStorage(String publicId) {
        assertNotNull(publicId);
        String uri = "storages/" + publicId;
        return WebTestClientFactory.getOneByURI(uri, Storage.class);
    }

    private static StorageDrive getStorageDrive(String publicId) {
        assertNotNull(publicId);
        String uri = "storages/" + publicId;
        return WebTestClientFactory.getOneByURI(uri, StorageDrive.class);
    }

    private static StorageCloud getStorageCloud(String publicId) {
        assertNotNull(publicId);
        String uri = "storages/" + publicId;
        return WebTestClientFactory.getOneByURI(uri, StorageCloud.class);
    }

    public static boolean verifyCreateStorageById(String storageId) throws Exception {
        assertNotNull(storageId);
        Storage storage = getStorage(storageId);
        return storage != null && storage.getPublicId().equals(storageId);
    }

    public static String getLibraryDriveIdByStorageId(String storageId) {
        assertNotNull(storageId);
        String uri = "storages/library/" + storageId + "/content";
        LibraryContent storageLibrary = WebTestClientFactory.getOneByURI(uri, LibraryContent.class);
        return storageLibrary.getLibraryLocationList().get(0).getDrive().getPublicId();
    }

    public static boolean editStorage(String storageId,
                                      String storageName,
                                      String hostId,
                                      String pathStorage,
                                      String modif) {
        assertNotNull(storageId, "Id du storage null, impossible de faire ce test");
        String uri = "storages/vls/" + storageId;
        VlsEditionInput storageEdition = new VlsEditionInput();
        storageEdition.setHostId(hostId);
        storageEdition.setNbDrives(2);
        storageEdition.setNbCartridge(10);
        storageEdition.setCartridgeSize(1000);
        storageEdition.setComment(modif);
        storageEdition.setPath(pathStorage);
        storageEdition.setName(storageName);
        List<EnvVar> environmentVariables = new ArrayList<>();
        storageEdition.setEnvironmentVariables(environmentVariables);
        getIdForUpdateStatusOk(uri, storageEdition);
        return true;
    }

    public static boolean editStorageDrive(String storageId,
                                           String storageName,
                                           String hostId,
                                           String pathStorage,
                                           String deviceDescriptorHostId,
                                           String modif) throws Exception {
        assertNotNull(storageId);
        String uri = "storages/drive/" + storageId;
        String deviceDescriptorPath = pathStorage + "/" + getToken();
        DriveEditionInput driveEditionInput = new DriveEditionInput();
        driveEditionInput.setHostPublicId(hostId);
        driveEditionInput.setIsSan(false);
        driveEditionInput.setIndex(0);
        driveEditionInput.setType(88);
        List<DeviceDescriptor> deviceDescripterList = new ArrayList<>();
        DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
        deviceDescriptor.setIsEnabled(true);
        deviceDescriptor.setPath(deviceDescriptorPath);
        deviceDescriptor.setHostId(deviceDescriptorHostId);
        deviceDescripterList.add(deviceDescriptor);
        driveEditionInput.setDevicesDescriptor(deviceDescripterList);
        driveEditionInput.setComment(modif);
        driveEditionInput.setUseSpecificIdentity(true);
        driveEditionInput.setName(storageName);
        driveEditionInput.setUsername(DRIVE_USERNAME);
        getIdForUpdateStatusOk(uri, driveEditionInput);
        return true;
    }

    public static boolean editStorageLibrary(String storageId,
                                             String deviceDescriptorHostId,
                                             String modif) {
        assertNotNull(storageId);
        String uri = "storages/library/" + storageId;
        LibraryEditionInput storageEdition = new LibraryEditionInput();
        storageEdition.setCleanCartUsed(10);
        storageEdition.setDeviceDescriptor(deviceDescriptorHostId);
        storageEdition.setComment(modif);
        LibraryProperties libraryProperties = new LibraryProperties();
        storageEdition.setLibraryProperties(libraryProperties);
        storageEdition.setMaxCleanCartUsed(100);
        storageEdition.setSerialNumber(LIBRARY_NEW_SERIAL_NUMBER);
        storageEdition.setSlotCleanCart(1);
        getIdForUpdateStatusOk(uri, storageEdition);
        return true;
    }

    public static boolean editStorageCloud(String storageId) {
        assertNotNull(storageId);
        String uri = "storages/cloud/s3/" + storageId;
        CloudS3EditionInput storageEdition = new CloudS3EditionInput();
        storageEdition.setName(STORAGE_CLOUD_LINUX);
        storageEdition.setCloudType(Integer.parseInt(CLOUD_TYPE));
        storageEdition.setBucketName(CLOUD_BUCKET_NAME);
        storageEdition.setAccessKeyId(CLOUD_ACCESS_KEY_ID);

        String serverUrlAdress = CLOUD_SERVER_URL;
        if (CLOUD_PORT != null && !CLOUD_PORT.isEmpty()) {
            serverUrlAdress += ":" + Integer.parseInt(CLOUD_PORT);
        }

        storageEdition.setServerUrlAddress(serverUrlAdress);
        storageEdition.setAccessKeyIdPasswd(CLOUD_ACCESS_KEY_ID_PASSWD);
        storageEdition.setCreateBucket(false);
        storageEdition.setComment("modif");
        storageEdition.setObjectLockMode("NONE");
        storageEdition.setBucketVersioning(true);
        storageEdition.setRepositoryProductId(CLOUD_AWS_S3_REPOSITORY_PRODUCT_ID);
        getIdForUpdateStatusOk(uri, storageEdition);
        return true;
    }

    public static boolean editStorageCloudLock(String storageId,
                                               String lock,
                                               String newBucketName) {
        assertNotNull(storageId);
        String uri = "storages/cloud/s3/" + storageId;
        CloudS3EditionInput storageEdition = new CloudS3EditionInput();
        storageEdition.setName(STORAGE_CLOUD_LINUX);
        storageEdition.setCloudType(Integer.parseInt(CLOUD_TYPE));
        storageEdition.setBucketName(newBucketName);
        storageEdition.setAccessKeyId(CLOUD_ACCESS_KEY_ID);

        String serverUrlAdress = CLOUD_SERVER_URL;
        if (CLOUD_PORT != null && !CLOUD_PORT.isEmpty()) {
            serverUrlAdress += ":" + Integer.parseInt(CLOUD_PORT);
        }

        storageEdition.setServerUrlAddress(serverUrlAdress);
        storageEdition.setAccessKeyIdPasswd(CLOUD_ACCESS_KEY_ID_PASSWD);
        storageEdition.setCreateBucket(false);
        storageEdition.setComment("modif");
        storageEdition.setBucketVersioning(true);
        storageEdition.setRepositoryProductId(CLOUD_AWS_S3_REPOSITORY_PRODUCT_ID);
        storageEdition.setObjectLockMode(lock);
        getIdForUpdateStatusOk(uri, storageEdition);
        return true;

    }

    public static List<String> tryEditStorageCloudLock(String storageId,
                                                       String lock,
                                                       String newBucketName) {
        assertNotNull(storageId);
        String uri = "storages/cloud/s3/" + storageId;
        CloudS3EditionInput storageEdition = new CloudS3EditionInput();
        storageEdition.setName(STORAGE_CLOUD_LINUX);
        storageEdition.setCloudType(Integer.parseInt(CLOUD_TYPE));
        storageEdition.setBucketName(newBucketName);
        storageEdition.setAccessKeyId(CLOUD_ACCESS_KEY_ID);

        String serverUrlAdress = CLOUD_SERVER_URL;
        if (CLOUD_PORT != null && !CLOUD_PORT.isEmpty()) {
            serverUrlAdress += ":" + Integer.parseInt(CLOUD_PORT);
        }

        storageEdition.setServerUrlAddress(serverUrlAdress);
        storageEdition.setAccessKeyIdPasswd(CLOUD_ACCESS_KEY_ID_PASSWD);
        storageEdition.setCreateBucket(false);
        storageEdition.setComment("modif");
        storageEdition.setBucketVersioning(true);
        storageEdition.setRepositoryProductId(CLOUD_AWS_S3_REPOSITORY_PRODUCT_ID);
        storageEdition.setObjectLockMode(lock);
        return postForEditionStatusOther(uri, storageEdition, 400);
    }

    public static boolean editStorageDedupHss(String storageId) {
        assertNotNull(storageId);
        String uri = "storages/deduplication/hss/" + storageId;
        HssEditionInput storageEdition = new HssEditionInput();
        storageEdition.setName(STORAGE_DEDUP_HSS_LINUX);
        storageEdition.setPort(Integer.parseInt(ADE_PORT));
        storageEdition.setSize(2048);
        storageEdition.setUserName(ADE_USER);
        storageEdition.setCompress("Lzf");
        storageEdition.setNbDrives(4);
        storageEdition.setServerName(ADE_SERVER_NAME);
        storageEdition.setComment("modif");
        getIdForUpdateStatusOk(uri, storageEdition);
        return true;
    }

    public static boolean editStorageDedupHvds(String storageId) {
        assertNotNull(storageId);

        String uri = "storages/deduplication/hvds/" + storageId;
        DedupEditionInput storageEdition = new DedupEditionInput();
        storageEdition.setName(STORAGE_DEDUP_HVDS_LINUX);
        storageEdition.setPort(Integer.parseInt(ADE_PORT));
        storageEdition.setUserName(ADE_USER);
        storageEdition.setServerName(ADE_SERVER_NAME);
        storageEdition.setComment("modif");
        getIdForUpdateStatusOk(uri, storageEdition);
        return true;
    }

    public static boolean editStorageEnhancedDisk(String storageId) {
        assertNotNull(storageId);
        String uri = "storages/enhanced-disk/" + storageId;
        EnhancedDiskEditionInput storageEdition = new EnhancedDiskEditionInput();
        storageEdition.setName(STORAGE_ENHANCED_DISK_LINUX);
        storageEdition.setPort(7777);
        storageEdition.setRepositoryProductId(XDCS_REPOSITORY_PRODUCT_ID);
        storageEdition.setServerName(SERVER_NAME);
        storageEdition.setComment("modif");
        getIdForUpdateStatusOk(uri, storageEdition);
        return true;
    }

    public static List<String> tryEditStorageEnhancedDisk(String storageId) {
        assertNotNull(storageId);
        String uri = "storages/enhanced-disk/" + storageId;
        EnhancedDiskEditionInput storageEdition = new EnhancedDiskEditionInput();
        storageEdition.setName(STORAGE_ENHANCED_DISK_LINUX);
        storageEdition.setPort(7777);
        storageEdition.setRepositoryProductId(XDCS_REPOSITORY_PRODUCT_ID);
        storageEdition.setServerName(SERVER_NAME);
        storageEdition.setComment("modif");
        return postForEditionStatusOther(uri, storageEdition, 400);
    }

    public static boolean verifyEditStorage(String storageId,
                                            String modif) {
        assertNotNull(storageId);
        Storage storage = getStorage(storageId);
        return storage != null && storage.getComment().equals(modif);
    }

    public static boolean verifyEditStorageIsWormable(String storageId,
                                                      boolean expectedValue) {
        assertNotNull(storageId);
        StorageDrive storage = getStorageDrive(storageId);
        return storage != null && storage.getIsWorm().equals(expectedValue);
    }

    public static boolean verifyEditStorageCheckUsername(String storageId,
                                                         String modif) {
        assertNotNull(storageId);
        StorageDrive storage = getStorageDrive(storageId);
        return storage != null && storage.getUsername().equals(modif);
    }

    public static boolean verifyEditStorageSerialNumber(String storageId,
                                                        String modif) {
        assertNotNull(storageId);
        Storage storage = getStorage(storageId);
        return storage != null && storage.getSerialNumber().equals(modif);
    }

    public static boolean verifyEditS3Lock(String storageId,
                                           String modif) {
        assertNotNull(storageId);
        StorageCloud storageCloud = getStorageCloud(storageId);
        return storageCloud != null && storageCloud.getObjectLockMode().equals(modif);
    }


    public static boolean runActionStorage(String storagePublicId,
                                           String actionName,
                                           HttpStatus status) {
        ActionInput input = new ActionInput();
        List<String> publicIds = new ArrayList<>();
        publicIds.add(storagePublicId);
        input.setPublicIds(publicIds);
        input.setActionName(actionName);
        WebTestClientFactory.putWithExpectedStatus("storages/actions", input, status);
        return true;
    }

    public static boolean deleteStorage(String storagePublicId) {
        cachedStorages.values().remove(storagePublicId);
        return runActionStorage(storagePublicId, STORAGE_ACTION_DELETE, HttpStatus.OK);
    }

    public static boolean verifyStorageDeletion(String storageId) {
        assertNotNull(storageId);
        return !verifyCreateStorageByList(storageId);
    }

    public static boolean tryToDeleteUsedStorage(String storagePublicId) {
        return runActionStorage(storagePublicId, STORAGE_ACTION_DELETE, HttpStatus.BAD_REQUEST);
    }

    public static String retrieveOrCreateStorageSwarmCloudLinux() {
        String storage = cachedStorages.get(STORAGE_SWARM_CLOUD_LINUX);
        if (storage == null) {
            storage = retrieveStorageFromName(STORAGE_SWARM_CLOUD_LINUX);
            if (storage == null) {
                storage = createRepositoryCloudS3(STORAGE_SWARM_CLOUD_LINUX, SWARM_URL, SWARM_PORT, SWARM_REPOSITORY_PRODUCT_ID,
                        SWARM_TOKEN_ID, SWARM_SECRET, SWARM_BUCKET_NAME);
            }
        }
        return storage;
    }

    public static String retrieveOrCreateStorageScalityCloudLinux() {
        String storage = cachedStorages.get(STORAGE_SCALITY_CLOUD_LINUX);
        if (storage == null) {
            storage = retrieveStorageFromName(STORAGE_SCALITY_CLOUD_LINUX);
            if (storage == null) {
                storage = createRepositoryCloudS3(STORAGE_SCALITY_CLOUD_LINUX, SCALITY_URL, SCALITY_PORT,
                        SCALITY_REPOSITORY_PRODUCT_ID, SCALITY_TOKEN_ID, SCALITY_SECRET,
                        SCALITY_BUCKET_NAME);
            }
        }
        return storage;
    }

    public static String retrieveOrCreateStorageActiveScaleCloudLinux() {
        String storage = cachedStorages.get(STORAGE_ACTIVE_SCALE_CLOUD_LINUX);
        if (storage == null) {
            storage = retrieveStorageFromName(STORAGE_ACTIVE_SCALE_CLOUD_LINUX);
            if (storage == null) {
                storage = createRepositoryCloudS3(STORAGE_ACTIVE_SCALE_CLOUD_LINUX, ACTIVE_SCALE_URL, ACTIVE_SCALE_PORT,
                        ACTIVE_SCALE_REPOSITORY_PRODUCT_ID, ACTIVE_SCALE_TOKEN_ID,
                        ACTIVE_SCALE_SECRET, ACTIVE_SCALE_BUCKET_NAME);
            }
        }
        return storage;
    }

    public static String retrieveOrCreateStorageVLSLinux() throws Exception {
        String storageVLSLinux = cachedStorages.get(STORAGE_VLS_LINUX);
        if (storageVLSLinux == null) {
            storageVLSLinux = StorageResourceTest.retrieveStorageFromName(STORAGE_VLS_LINUX);
            if (storageVLSLinux == null) {
                storageVLSLinux =
                        createStorageVls(UtilHostTest.retrieveOrCreateHostLinux(), PATH_STORAGE_LINUX, STORAGE_VLS_LINUX);
            }
        }
        return storageVLSLinux;
    }

    public static String retrieveOrCreateStorageVLSWindows() throws Exception {
        String storageVLSWindows = cachedStorages.get(STORAGE_VLS_WINDOWS);
        if (storageVLSWindows == null) {
            storageVLSWindows = StorageResourceTest.retrieveStorageFromName(STORAGE_VLS_WINDOWS);
            if (storageVLSWindows == null) {
                storageVLSWindows =
                        createStorageVls(UtilHostTest.retrieveOrCreateHostWindows2016(), PATH_STORAGE_WINDOWS, STORAGE_VLS_WINDOWS);
            }
        }
        return storageVLSWindows;
    }

    public static String retrieveOrCreateStorageDriveLinux(String storageName) throws Exception {
        String storageDriveLinux = cachedStorages.get(storageName);
        if (storageDriveLinux == null) {
            storageDriveLinux = StorageResourceTest.retrieveStorageFromName(storageName);
            if (storageDriveLinux == null) {
                storageDriveLinux = createStorageDrive(UtilHostTest.retrieveOrCreateHostLinux(), PATH_STORAGE_LINUX,
                        UtilHostTest.retrieveOrCreateHostLinux(), storageName,
                        DRIVE_SERIAL_NUMBER, DRIVE_TYPE_HPE_ULTRIUM_6, false);
            }
            cachedStorages.put(storageName, storageDriveLinux);
        }
        return storageDriveLinux;
    }

    public static String retrieveOrCreateStorageDriveSanLinux(String storageName) throws Exception {
        String storageDriveLinux = cachedStorages.get(storageName);
        if (storageDriveLinux == null) {
            storageDriveLinux = StorageResourceTest.retrieveStorageFromName(storageName);
            if (storageDriveLinux == null) {
                storageDriveLinux = createStorageDriveSan(STORAGE_DRIVE_SAN_NETWORK_NAME, PATH_STORAGE_LINUX,
                        UtilHostTest.retrieveOrCreateHostLinux(), storageName,
                        DRIVE_SERIAL_NUMBER);
            }
            cachedStorages.put(storageName, storageDriveLinux);
        }
        return storageDriveLinux;
    }

    public static String retrieveOrCreateStorageDiskDriveLinux(String storageName) throws Exception {
        String storageDiskDriveLinux = cachedStorages.get(storageName);
        if (storageDiskDriveLinux == null) {
            storageDiskDriveLinux = StorageResourceTest.retrieveStorageFromName(storageName);
            if (storageDiskDriveLinux == null) {
                storageDiskDriveLinux = createStorageDiskDrive(UtilHostTest.retrieveOrCreateHostLinux(), PATH_STORAGE_LINUX,
                        UtilHostTest.retrieveOrCreateHostLinux(), storageName);
            }
            cachedStorages.put(storageName, storageDiskDriveLinux);
        }
        return storageDiskDriveLinux;
    }

    public static String retrieveOrCreateStorageLibraryLinux() throws Exception {
        String storageLibraryLinux = cachedStorages.get(STORAGE_LIBRARY_LINUX);
        if (storageLibraryLinux == null) {
            storageLibraryLinux = StorageResourceTest.retrieveStorageFromName(STORAGE_LIBRARY_LINUX);
            if (storageLibraryLinux == null) {
                storageLibraryLinux =
                        createStorageLibrary(UtilHostTest.retrieveOrCreateHostLinux(), UtilHostTest.retrieveOrCreateHostLinux(),
                                STORAGE_LIBRARY_LINUX, STORAGE_INTERNAL_DEVICE_LIBRARY_LINUX, LIBRARY_SERIAL_NUMBER);
            }
        }
        return storageLibraryLinux;
    }

    public static String retrieveOrCreateStorageCloudLinux() {
        String storageAWSS3CloudLinux = cachedStorages.get(STORAGE_AWS_CLOUD_LINUX);

        if (storageAWSS3CloudLinux == null) {
            storageAWSS3CloudLinux = StorageResourceTest.retrieveStorageFromName(STORAGE_AWS_CLOUD_LINUX);
            if (storageAWSS3CloudLinux == null) {
                storageAWSS3CloudLinux = createRepositoryCloudS3(STORAGE_AWS_CLOUD_LINUX, CLOUD_SERVER_URL, CLOUD_PORT,
                        CLOUD_AWS_S3_REPOSITORY_PRODUCT_ID, CLOUD_ACCESS_KEY_ID,
                        CLOUD_ACCESS_KEY_ID_PASSWD, CLOUD_BUCKET_NAME);
            }
        }
        return storageAWSS3CloudLinux;
    }

    public static String retrieveOrCreateStorageGenericCloudLinux() {
        String storageGenericCloudLinux = cachedStorages.get(STORAGE_CLOUD_LINUX);
        if (storageGenericCloudLinux == null) {
            storageGenericCloudLinux = StorageResourceTest.retrieveStorageFromName(STORAGE_CLOUD_LINUX);
            if (storageGenericCloudLinux == null) {
                storageGenericCloudLinux = createRepositoryCloudS3(STORAGE_CLOUD_LINUX, CLOUD_SERVER_URL, CLOUD_PORT,
                        CLOUD_AWS_S3_REPOSITORY_PRODUCT_ID, CLOUD_ACCESS_KEY_ID,
                        CLOUD_ACCESS_KEY_ID_PASSWD, CLOUD_BUCKET_NAME);
            }
        }
        return storageGenericCloudLinux;
    }

    public static String retrieveOrCreateStorageDedupHssLinux() {
        String storageDedupHssLinux = cachedStorages.get(STORAGE_DEDUP_HSS_LINUX);
        if (storageDedupHssLinux == null) {
            storageDedupHssLinux = StorageResourceTest.retrieveStorageFromName(STORAGE_DEDUP_HSS_LINUX);
            if (storageDedupHssLinux == null) {
                storageDedupHssLinux = createStorageDedupHss(STORAGE_DEDUP_HSS_LINUX);
            }
        }
        return storageDedupHssLinux;
    }

    public static String retrieveOrCreateStorageDedupHvdsLinux() {
        String storageDedupHvdsLinux = cachedStorages.get(STORAGE_DEDUP_HVDS_LINUX);
        if (storageDedupHvdsLinux == null) {
            storageDedupHvdsLinux = StorageResourceTest.retrieveStorageFromName(STORAGE_DEDUP_HVDS_LINUX);
            if (storageDedupHvdsLinux == null) {
                storageDedupHvdsLinux = createStorageDedupHvds(STORAGE_DEDUP_HVDS_LINUX);
            }
        }
        return storageDedupHvdsLinux;
    }

    public static String retrieveOrCreateStorageEnhancedDiskLinux() {
        String storageEnhancedDiskLinux = cachedStorages.get(STORAGE_ENHANCED_DISK_LINUX);
        if (storageEnhancedDiskLinux == null) {
            storageEnhancedDiskLinux = StorageResourceTest.retrieveStorageFromName(STORAGE_ENHANCED_DISK_LINUX);
            if (storageEnhancedDiskLinux == null) {
                storageEnhancedDiskLinux = createStorageEnhancedDisk(STORAGE_ENHANCED_DISK_LINUX);
            }
        }
        return storageEnhancedDiskLinux;
    }

    public static boolean editLibrary(String storageId,
                                      String modif,
                                      String serialNumber) {
        String url = "storages/library/" + storageId;
        String url2 = "storages/" + storageId;
        LibraryEditionInput storageEdition = WebTestClientFactory.getOneByURI(url2, LibraryEditionInput.class);
        LibraryProperties libraryProperties = new LibraryProperties();
        libraryProperties.setROB_PROP_BARCODE_SUPPORTED(false);
        libraryProperties.setROB_PROP_CAN_FLIP(false);
        libraryProperties.setROB_PROP_CHECK_DRIVE_ACCESS_STATUS(false);
        libraryProperties.setROB_PROP_CHECK_SLOT_ACCESS_STATUS(false);
        libraryProperties.setROB_PROP_DONT_TRUST_STATUS(false);
        libraryProperties.setROB_PROP_DRIVE_FULL(false);
        libraryProperties.setROB_PROP_DRIVE_STATUS_AFTER_CLEANING(false);
        libraryProperties.setROB_PROP_DRIVE_STATUS_BEFORE_CLEANING(false);
        libraryProperties.setROB_PROP_EXPLICIT_DRIVE_OFFLINE(false);
        libraryProperties.setROB_PROP_FAST_LOAD(false);
        libraryProperties.setROB_PROP_FIXED_PICKER_ADDR(false);
        libraryProperties.setROB_PROP_FORCE_IES_BARCODE_YES(false);
        libraryProperties.setROB_PROP_FORCE_NO_MAILBOX(false);
        libraryProperties.setROB_PROP_FULL_SIZE_DRIVE(false);
        libraryProperties.setROB_PROP_IESR_SUPPORT(false);
        libraryProperties.setROB_PROP_LSM_LOG(false);
        libraryProperties.setROB_PROP_NO_CLEANING(false);
        libraryProperties.setROB_PROP_NO_IES_IF_DRIVE_FULL(false);
        libraryProperties.setROB_PROP_NO_MOVE_AFTER_CLEANING(false);
        libraryProperties.setROB_PROP_PACK_MANAGEMENT(false);
        libraryProperties.setROB_PROP_POS_TO_ELEM(false);
        libraryProperties.setROB_PROP_READY_INPORT(false);
        libraryProperties.setROB_PROP_READ_DRIVE_BARCODE(false);
        libraryProperties.setROB_PROP_RELOAD_DRIVE(false);
        libraryProperties.setROB_PROP_RESET_WRONG_STATUS(false);
        libraryProperties.setROB_PROP_SCAN_ELEM(false);
        libraryProperties.setROB_PROP_SCAN_MAILBOX_ON_EXCEPT(false);
        libraryProperties.setROB_PROP_SHARED_LIBRARY(false);
        libraryProperties.setROB_PROP_TRUST_MAILBOX_STATUS(false);
        libraryProperties.setROB_PROP_UMOUNT_AFTER_USE(false);
        libraryProperties.setROB_PROP_UNLOAD_BEFORE_IES(false);
        libraryProperties.setROB_PROP_USE_IESR_INSTEAD_IES(false);
        libraryProperties.setROB_PROP_USE_INFOFILE(false);
        libraryProperties.setROB_PROP_USE_LAST_ELEM_ADDR(false);
        libraryProperties.setROB_PROP_USE_STIE_TO_DETECT_MAILBOX(false);
        libraryProperties.setROB_PROP_WAIT_MAILBOX_READY(false);
        storageEdition.setLibraryProperties(libraryProperties);
        storageEdition.setComment(modif);
        storageEdition.setDeviceDescriptor("ThisIsADeviceDescriptor");
        storageEdition.setSerialNumber(serialNumber);
        storageEdition.setCleanCartUsed(10);
        storageEdition.setSlotCleanCart(1);
        getIdForUpdateStatusOk(url, storageEdition);
        return true;
    }
}
