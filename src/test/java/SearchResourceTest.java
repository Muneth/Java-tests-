package com.atempo.tina.testAuto.restAPI.resources;

import static com.atempo.tina.testAuto.restAPI.Setup.DEPTH_SEARCH_FILTER_IN_DAY;
import static com.atempo.tina.testAuto.restAPI.Setup.GENERIC_SEARCH_TEXT_FILTER;
import static com.atempo.tina.testAuto.restAPI.Setup.MAXIMUM_SEARCH_RESULT;
import static com.atempo.tina.testAuto.restAPI.Setup.STRATEGY_SEARCH_FILTER;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.atempo.tina.restapi.model.preparesearch.SearchSettings;
import com.atempo.tina.restapi.model.search.GenericSearchInput;
import com.atempo.tina.restapi.model.search.SearchContextInput;
import com.atempo.tina.restapi.model.search.SearchParameterInput;
import java.util.ArrayList;
import java.util.Date;

public class SearchResourceTest {

    public static SearchSettings getSearchSettings(String appArchiveOrHostPublicId) {
        assertNotNull(appArchiveOrHostPublicId);
        String url = "search/" + appArchiveOrHostPublicId + "/prepare";
        return WebTestClientFactory.getOneByURI(url, SearchSettings.class);
    }

    public static String startSearch(String appArchiveOrHostPublicId) {
        assertNotNull(appArchiveOrHostPublicId);
        String url = "search/" + appArchiveOrHostPublicId + "/start";

        SearchParameterInput searchParameterInput = new SearchParameterInput();
        SearchContextInput searchContextInput = new SearchContextInput();
        searchContextInput.setSearchDate(new Date());

        if (DEPTH_SEARCH_FILTER_IN_DAY != null && DEPTH_SEARCH_FILTER_IN_DAY.matches("\\d+")) {
            searchContextInput.setDepthInDay(Integer.parseInt(DEPTH_SEARCH_FILTER_IN_DAY));
        } else {
            searchContextInput.setDepthInDay(30);
        }
        if (STRATEGY_SEARCH_FILTER != null && STRATEGY_SEARCH_FILTER.matches("^STRATEGY_(A|B|C|D)$")) {
            searchContextInput.setStrategies(new ArrayList<String>() {{
                add(STRATEGY_SEARCH_FILTER);
            }});
        } else {
            searchContextInput.setStrategies(new ArrayList<String>() {{
                add("STRATEGY_A");
                add("STRATEGY_B");
                add("STRATEGY_C");
                add("STRATEGY_D");
            }});
        }
        if (MAXIMUM_SEARCH_RESULT != null && MAXIMUM_SEARCH_RESULT.matches("\\d+")) {
            searchParameterInput.setMaxHit(Integer.parseInt(MAXIMUM_SEARCH_RESULT));
        } else {
            searchParameterInput.setMaxHit(50);
        }
        searchParameterInput.setGenericSearchInput(new GenericSearchInput(GENERIC_SEARCH_TEXT_FILTER, true));
        searchParameterInput.setSearchContextInput(searchContextInput);
        assertNotNull(searchContextInput);
        return WebTestClientFactory.getIdForCreateStatusOk(url, searchParameterInput);
    }
}
