package com.atempo.tina.restapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public final class ClientIpAddress {
    static final String IPV4_RANGE_127_0_0_1 =
            "^(127\\.(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5]))$";
    static final String IPV4_RANGE_10_0_0_0 =
            "^(10\\.(?:\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(?:\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(?:\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]))$";
    static final String IPV4_RANGE_172_16_0_0 =
            "^(172\\.(?:1[6-9]|2\\d|3[0-1])\\.(?:\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(?:\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]))$";
    static final String IPV4_RANGE_192_168_0_0 =
            "^(192\\.168\\.(?:\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(?:\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]))$";
    static final String IPV6_VERIFY_REGEX_PATTERN =
            "^([fF][cCdD][0-9a-fA-F]{2}(:[0-9a-fA-F]{1,4}){6})$|^([fF][cCdD][0-9a-fA-F]{2}(:[0-9a-fA-F]{1,4}){0,5}::([0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){0,4})?)$";
    static final String IPV4_VERIFY_REGEX_PATTERN =
            IPV4_RANGE_127_0_0_1 + "|" + IPV4_RANGE_10_0_0_0 + "|" + IPV4_RANGE_172_16_0_0 + "|" + IPV4_RANGE_192_168_0_0;
    static final String IP_VERIFY_REGEX_PATTERN = IPV4_VERIFY_REGEX_PATTERN + "|" + IPV6_VERIFY_REGEX_PATTERN;
    private static final Pattern PRIVATE_ADDRESS_PATTERN = Pattern.compile(IP_VERIFY_REGEX_PATTERN, Pattern.CANON_EQ);
    // CHECKSTYLE:ON

    private ClientIpAddress() {
    }

    /**
     * Extracts the "real" client IP address from the request. It analyzes request
     * headers {@code REMOTE_ADDR}, {@code X-Forwarded-For} as well as
     * {@code Client-IP}. Optionally private/local addresses can be filtered in
     * which case an empty string is returned.
     *
     * @param request                HTTP request
     * @param filterPrivateAddresses true if private/local addresses (see
     *                               <a href="https://en.wikipedia.org/wiki/Private_network#Private_IPv4_address_spaces">...</a>
     *                               and <a href="https://en.wikipedia.org/wiki/Unique_local_address">...</a>) should be
     *                               filtered i.e. omitted
     * @return IP address or empty string
     */
    public static String getFrom(HttpServletRequest request,


                                 boolean filterPrivateAddresses) {
        if (request == null) {
            return "0.0.0.0";
        }
        String ip = request.getRemoteAddr();

        String headerClientIp = request.getHeader("Client-IP");
        String headerXForwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) && StringUtils.isNotEmpty(headerClientIp)) {
            ip = headerClientIp;
        } else if (StringUtils.isNotEmpty(headerXForwardedFor)) {
            ip = headerXForwardedFor;
        }
        if (filterPrivateAddresses && isPrivateOrLocalAddress(ip)) {
            return StringUtils.EMPTY;
        } else {
            return ip;
        }
    }

    static boolean isPrivateOrLocalAddress(String address) {
        Matcher regexMatcher = PRIVATE_ADDRESS_PATTERN.matcher(address);
        return regexMatcher.matches();
    }
}
