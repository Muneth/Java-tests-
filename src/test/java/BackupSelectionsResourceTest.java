package com.atempo.tina.testAuto.restAPI.resources;

import static com.atempo.tina.testAuto.restAPI.Setup.BACKUP_SELECTION_EXCLUDE_FILTER;
import static com.atempo.tina.testAuto.restAPI.Setup.BACKUP_SELECTION_EXCLUDE_FILTER2;
import static com.atempo.tina.testAuto.restAPI.Setup.STRATEGY_A;
import static com.atempo.tina.testAuto.restAPI.Setup.STRATEGY_B;
import static com.atempo.tina.testAuto.restAPI.Setup.STRATEGY_C;
import static com.atempo.tina.testAuto.restAPI.Setup.STRATEGY_D;
import static com.atempo.tina.testAuto.restAPI.resources.WebTestClientFactory.getIdForCreateStatusOk;
import static com.atempo.tina.testAuto.restAPI.resources.WebTestClientFactory.getIdForUpdateStatusOk;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.atempo.tina.restapi.model.ActionInput;
import com.atempo.tina.restapi.model.actionparameters.BackupSelectionActionParameters;
import com.atempo.tina.restapi.model.backupselection.BackupSelection;
import com.atempo.tina.restapi.model.backupselection.BackupSelectionCreationInput;
import com.atempo.tina.restapi.model.backupselection.BackupSelectionDetail;
import com.atempo.tina.restapi.model.backupselection.BackupSelectionEditionInput;
import com.atempo.tina.restapi.model.swagger.BackupSelectionsActionInputBulkEdition;
import com.atempo.tina.restapi.model.swagger.BackupSelectionsActionInputDelete;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;

public class BackupSelectionsResourceTest {
    public static String createBackupSelection(String id,
                                               String pathBackupSelection) {
        String url = "backup-selections";
        BackupSelectionCreationInput bckSelectionCreationInput = new BackupSelectionCreationInput();
        bckSelectionCreationInput.setAgentPublicId(id);
        bckSelectionCreationInput.setCryptedFormat(BackupSelectionDetail.CRYPTED_FORMAT_NONE);
        bckSelectionCreationInput.setFormatCompressed(false);
        bckSelectionCreationInput.setParallelMode(true);
        ArrayList<String> strategyList = new ArrayList<>();
        strategyList.add(STRATEGY_A);
        strategyList.add(STRATEGY_B);
        strategyList.add(STRATEGY_C);
        strategyList.add(STRATEGY_D);
        ArrayList<String> pathList = new ArrayList<>();
        pathList.add(pathBackupSelection);
        bckSelectionCreationInput.setStrategyList(strategyList);
        bckSelectionCreationInput.setPathList(pathList);
        return getIdForCreateStatusOk(url, bckSelectionCreationInput);
    }

    public static boolean verifCreateBackupSelectionById(String backupSelectionId) {
        assertNotNull(backupSelectionId);
        String url = "backup-selections/" + backupSelectionId;

        BackupSelection backupSelection = WebTestClientFactory.getOneByURI(url, BackupSelection.class);
        return backupSelection.getPublicId().equals(backupSelectionId);
    }

    public static boolean verifCreateBackupSelectionByList(String backupSelectionId) {
        assertNotNull(backupSelectionId);
        String url = "backup-selections";

        List<BackupSelection> backupSelections = WebTestClientFactory.getListByURI(url, BackupSelection.class);
        for (BackupSelection backupSelection : backupSelections) {
            if (backupSelection.getPublicId().equals(backupSelectionId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean editBackupSelection(String backupSelectionId) {
        assertNotNull(backupSelectionId);
        String url = "backup-selections/" + backupSelectionId;

        BackupSelectionEditionInput bckSelectionEditionInput =
                WebTestClientFactory.getOneByURI(url, BackupSelectionEditionInput.class);
        bckSelectionEditionInput.setCryptedFormat(BackupSelectionDetail.CRYPTED_FORMAT_STD);
        bckSelectionEditionInput.setCryptedRuleId("");
        bckSelectionEditionInput.setFormatCompressed(false);
        bckSelectionEditionInput.setParallelMode(true);
        bckSelectionEditionInput.setMaxAgeFilter(0);
        bckSelectionEditionInput.setMaxSizeFilter(0L);
        ArrayList<String> nameIncludeFilters = new ArrayList<>();
        nameIncludeFilters.add("");
        bckSelectionEditionInput.setNameExcludeFilters(nameIncludeFilters);
        bckSelectionEditionInput.setParallelMode(true);
        bckSelectionEditionInput.setRuleMode("");
        ArrayList<String> strategyList = new ArrayList<>();
        strategyList.add(STRATEGY_A);
        strategyList.add(STRATEGY_B);
        strategyList.add(STRATEGY_C);
        strategyList.add(STRATEGY_D);
        bckSelectionEditionInput.setStrategyList(strategyList);
        assertEquals(backupSelectionId, getIdForUpdateStatusOk(url, bckSelectionEditionInput));
        return true;
    }

    public static boolean verifEditBackupSelection(String backupSelectionId) {
        assertNotNull(backupSelectionId);
        String url = "backup-selections/" + backupSelectionId;
        BackupSelectionEditionInput backupSelectionEditionInput =
                WebTestClientFactory.getOneByURI(url, BackupSelectionEditionInput.class);
        return backupSelectionEditionInput.getCryptedFormat().contains(BackupSelectionDetail.CRYPTED_FORMAT_STD);
    }

    public static boolean runBackupSelectionAction(String backupSelectionId,
                                                   String actionName) {
        String url = "backup-selections/actions";

        BackupSelectionsActionInputDelete backupSelectionsActionInputDelete = new BackupSelectionsActionInputDelete();
        ArrayList<String> publicIds = new ArrayList<>();
        publicIds.add(backupSelectionId);
        backupSelectionsActionInputDelete.setPublicIds(publicIds);
        backupSelectionsActionInputDelete.setActionName(actionName);
        WebTestClientFactory.putThatExpectedStatusOk(url, backupSelectionsActionInputDelete);
        return true;
    }

    public static boolean deleteBackupSelection(String backupSelectionId) {
        return runBackupSelectionAction(backupSelectionId, ActionInput.BACKUP_SELECTION_ACTION_DELETE);
    }

    public static boolean verifDeletionBackupSelection(String backupSelectionId) {
        return !verifCreateBackupSelectionByList(backupSelectionId);
    }

    public static boolean bulkEditionBackupSelectionReplace(String backupSelectionId,
                                                            String otherBackupSelectionId) {
        return bulkEditionBackupSelection(backupSelectionId, otherBackupSelectionId, true);
    }

    public static boolean bulkEditionBackupSelectionMerge(String backupSelectionId,
                                                          String otherBackupSelectionId) {
        return bulkEditionBackupSelection(backupSelectionId, otherBackupSelectionId, false);
    }

    private static boolean bulkEditionBackupSelection(String backupSelectionId,
                                                      String otherBackupSelectionId,
                                                      boolean oneStrategyAndReplaceFilter) {
        assertNotNull(backupSelectionId);
        String url = "backup-selections/actions";
        BackupSelectionsActionInputBulkEdition backupSelectionsActionInputBulkEdition =
                new BackupSelectionsActionInputBulkEdition();
        ArrayList<String> publicIds = new ArrayList<>();
        publicIds.add(backupSelectionId);
        publicIds.add(otherBackupSelectionId);
        backupSelectionsActionInputBulkEdition.setPublicIds(publicIds);
        backupSelectionsActionInputBulkEdition.setActionName(ActionInput.BACKUP_SELECTION_ACTION_BULK_EDITING);

        ArrayList<String> nameExcludeFilters = new ArrayList<>();
        if (oneStrategyAndReplaceFilter) {
            nameExcludeFilters.add(BACKUP_SELECTION_EXCLUDE_FILTER);
        } else {
            nameExcludeFilters.add(BACKUP_SELECTION_EXCLUDE_FILTER2);
        }

        ArrayList<String> strategyList = new ArrayList<>();
        if (oneStrategyAndReplaceFilter) {
            // NB : for strategy, it's always replace
            strategyList.add(STRATEGY_D);
        } else {
            // NB : for strategy, it's always replace
            strategyList.add(STRATEGY_A);
            strategyList.add(STRATEGY_B);
            strategyList.add(STRATEGY_C);
            strategyList.add(STRATEGY_D);
        }


        BackupSelectionActionParameters backupSelectionActionParameters = new BackupSelectionActionParameters();
        if (oneStrategyAndReplaceFilter) {
            backupSelectionActionParameters.setNameExcludeFilterEditionMode(BackupSelectionActionParameters.REPLACE);
        } else {
            backupSelectionActionParameters.setNameExcludeFilterEditionMode(BackupSelectionActionParameters.MERGE);
        }
        backupSelectionActionParameters.setNameExcludeFilters(nameExcludeFilters);
        backupSelectionActionParameters.setStrategyList(strategyList);

        backupSelectionsActionInputBulkEdition.setPayload(backupSelectionActionParameters);
        WebTestClientFactory.putOrPostWithXSItype(true, url, BackupSelectionsActionInputBulkEdition.class,
                backupSelectionsActionInputBulkEdition, HttpStatus.OK.value());
        return true;
    }

    public static boolean verifBulkEditionBackupSelectionReplace(String backupSelectionId,
                                                                 String otherBackupSelectionId) {
        return verifBulkEditionBackupSelection(backupSelectionId, otherBackupSelectionId, true);
    }

    public static boolean verifBulkEditionBackupSelectionMerge(String backupSelectionId,
                                                               String otherBackupSelectionId) {
        return verifBulkEditionBackupSelection(backupSelectionId, otherBackupSelectionId, false);
    }

    private static boolean verifBulkEditionBackupSelection(String backupSelectionId,
                                                           String otherBackupSelectionId,
                                                           boolean oneStrategyAndReplaceFilter) {
        assertNotNull(backupSelectionId);
        assertNotNull(otherBackupSelectionId);

        String url = "backup-selections/" + backupSelectionId;
        String otherUrl = "backup-selections/" + otherBackupSelectionId;

        BackupSelectionEditionInput backupSelectionEditionInput =
                WebTestClientFactory.getOneByURI(url, BackupSelectionEditionInput.class);
        assertTrue(backupSelectionEditionInput.getNameExcludeFilters().contains(BACKUP_SELECTION_EXCLUDE_FILTER));
        assertEquals(!oneStrategyAndReplaceFilter,
                backupSelectionEditionInput.getNameExcludeFilters().contains(BACKUP_SELECTION_EXCLUDE_FILTER2));
        assertEquals(!oneStrategyAndReplaceFilter, backupSelectionEditionInput.getStrategyList().contains(STRATEGY_A));
        assertEquals(!oneStrategyAndReplaceFilter, backupSelectionEditionInput.getStrategyList().contains(STRATEGY_B));
        assertEquals(!oneStrategyAndReplaceFilter, backupSelectionEditionInput.getStrategyList().contains(STRATEGY_C));
        assertTrue(backupSelectionEditionInput.getStrategyList().contains(STRATEGY_D));
        BackupSelectionEditionInput backupSelectionEditionInput2 =
                WebTestClientFactory.getOneByURI(otherUrl, BackupSelectionEditionInput.class);
        assertEquals(!oneStrategyAndReplaceFilter, backupSelectionEditionInput2.getStrategyList().contains(STRATEGY_A));
        assertEquals(!oneStrategyAndReplaceFilter, backupSelectionEditionInput2.getStrategyList().contains(STRATEGY_B));
        assertEquals(!oneStrategyAndReplaceFilter, backupSelectionEditionInput2.getStrategyList().contains(STRATEGY_C));
        assertTrue(backupSelectionEditionInput2.getStrategyList().contains(STRATEGY_D));
        return true;

    }
}
