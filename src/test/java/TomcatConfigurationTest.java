package com.atempo.tina.restapi.model.serverconfiguration;

import static com.atempo.tina.restapi.model.serverconfiguration.TomcatConfiguration.PORT_AS_STRING_REGEX;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.atempo.tina.restapi.model.DTOTemplateTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class TomcatConfigurationTest extends DTOTemplateTest<TomcatConfiguration> {

    public TomcatConfigurationTest() {
        super();
        isValidEmptyObject = true;
    }

    @Override
    public TomcatConfiguration createInput() {
        return new TomcatConfiguration();
    }

    @Override
    public void populateInputWithValidData() {
        input.setAccessLogEnabled(true);
        input.setHttpPort("8443");
        input.setSecure(true);
        input.setFullProtocolName("TLS");
        input.setHttpProtocol("HTTP");
        input.setServerName("tomcat.atempo.dev");
        input.setServerVersion("9");

    }

    @Override
    public void populateInputWithInvalidData() {
        input.setHttpPort("abcd");
    }

    @Override
    public String getJSONString() {
        return "{\n" + "   \"accessLogEnabled\" : true,\n" + "   \"fullProtocolName\" : \"TLS\",\n" +
                "   \"httpPort\" : \"8443\",\n" + "   \"httpProtocol\" : \"HTTP\",\n" + "   \"secure\" : true,\n" +
                "   \"serverName\" : \"tomcat.atempo.dev\",\n" + "   \"serverVersion\" : \"9\"\n" + "}";
    }


    public static Object[][] portNumberFalse() {
        return new Object[][] { { "" }, { "abc" }, { "-1" }, { "65536" }, { "66000" }, { "65600" }, { "65640" },
                { "999999" }, { "-99" }, { "00" }, { "000" }, { "0000" }, { "00000" } };
    }

    @ParameterizedTest
    @MethodSource("portNumberFalse")
    void testRegex(String portToTest) {
        assertTrue(portToTest.matches(PORT_AS_STRING_REGEX));
    }

    @Test
    void testRegex2() {
        for (int i = 0 ; i < 65536 ; i++) {
            String portAsString = String.valueOf(i);
            assertTrue(portAsString.matches(PORT_AS_STRING_REGEX));
        }
    }
}