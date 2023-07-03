package com.atempo.tina.restapi.util;

import static com.atempo.TestTags.REAL_UNIT_TEST;
import static com.atempo.tina.restapi.util.ClientIpAddress.isPrivateOrLocalAddress;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Tag(REAL_UNIT_TEST)
class ClientIpAddressTest {

    public static Object[][] ipAddressTrueExamples() {
        return new Object[][] { { "127.0.0.1" }, { "127.12.0.1" }, { "127.0.33.1" }, { "10.0.0.1" }, { "10.55.0.1" },
                { "10.0.66.1" }, { "172.16.0.1" }, { "172.16.55.1" }, { "172.31.1.222" }, { "192.168.0.1" },
                { "192.168.55.1" }, { "192.168.0.255" }, { "FC01:1234:5678::" }, { "FD00:abcd::" },
                { "Fd3f:8a12:5b3c::" }, { "Fd3f:8a12:5b3c::" }, { "Fd3f::8a12:5b3c" } };
    }

    public static Object[][] ipAddressFalseExamples() {
        return new Object[][] { { "" }, { "9.0.0.0.0" }, { "10.666.0.0" }, { "10.0.0.0.55.0" }, { "128.15.1.1.1" },
                { "130.15.1.1" }, { "127.15.1.999" }, { "172.15.0.0" }, { "172.32.0.0" },
                { "172.32.999.0" }, { "192.167.0.0" }, { "191.168.0.0" }, { "192.168.0.1.1" },
                { "2001:db8::1" }, { "Fd3f:8a12:5b3c:::" }, { "fe80::1234:5678:abcd:efgh" },
                { "2400:abcd:1234:5678::" }, { "ff01::1:2:3:4" }, { "::ffff:192.0.2.1" }, { "abcd:ef01::" },
                { "1234:5678:90ab:cdef::" }, { "abcd::1234:5678" },
                { "Fd3f:8a12:5b3c:Fd3f:8a12:5b3c:Fd3f:8a12:5b3c::" },
                { "Fd3f:8a12:5b3c:Fd3f:8a12:5b3c:Fd3f:8a12::5b3c" },
                { "Fd3f:8a12:5b3c:Fd3f:8a12:5b3c:Fd3f::8a12:5b3c" },
                { "Fd3f:8a12:5b3c:Fd3f:8a12:5b3c::Fd3f:8a12:5b3c" },
                { "Fd3f:8a12:5b3c:Fd3f:8a12::5b3c:Fd3f:8a12:5b3c" },
                { "Fd3f:8a12:5b3c:Fd3f::8a12:5b3c:Fd3f:8a12:5b3c" },
                { "Fd3f:8a12:5b3c::Fd3f:8a12:5b3c:Fd3f:8a12:5b3c" },
                { "Fd3f:8a12::5b3c:Fd3f:8a12:5b3c:Fd3f:8a12:5b3c" },
                { "Fd3f::8a12:5b3c:Fd3f:8a12:5b3c:Fd3f:8a12:5b3c" } };
    }

    @ParameterizedTest
    @MethodSource("ipAddressTrueExamples")
    void testRegexForTrueIpAddress(String ipAddress) {
        assertTrue(isPrivateOrLocalAddress(ipAddress));
    }

    @ParameterizedTest
    @MethodSource("ipAddressFalseExamples")
    void testRegexForFalseIpAddress(String ipAddress) {
        assertFalse(isPrivateOrLocalAddress(ipAddress));
    }
}