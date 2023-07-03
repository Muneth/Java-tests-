
import static org.junit.jupiter.api.Assertions.fail;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.resolver.DefaultAddressResolverGroup;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLException;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

public class WebTestClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(WebTestClientFactory.class);
    /**
     * Adapt this if your need to debug test to avoid Timeout on blocking read...
     */
    private final static Duration timeoutDuration = Duration.ofHours(1);

    private static final ReactorClientHttpConnector httpsInsecureConnector;

    static {
        SslContext sslContext;
        try {
            sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } catch (SSLException e) {
            throw new RuntimeException(e);
        }
        httpsInsecureConnector = new ReactorClientHttpConnector(
                HttpClient.create().secure(t -> t.sslContext(sslContext)).resolver(DefaultAddressResolverGroup.INSTANCE));
    }

    public static ReactorClientHttpConnector getHttpsInsecureConnector() {
        return httpsInsecureConnector;
    }

    private static final WebTestClient client =
            WebTestClient.bindToServer(httpsInsecureConnector).baseUrl(SERVER_URL).responseTimeout(timeoutDuration).build();

    public static WebTestClient getWebTestClient() {
        return client;
    }


    /**
     * Helper method to write webclient GET call. The call is authenticated by the cached token.
     *
     * @param uri       last part of the URL to call (exemple : "users/xxx")
     * @param classType class type to retrieve (exemple User.class)
     * @param <T>       Generic type that will be retrieve (in this example : User)
     * @return the retrieved list of T
     */
    public static <T> T getOneByURI(String uri,
                                    Class<T> classType) {
        return getOneByURI(uri, classType, getCachedToken());
    }

    /**
     * @param uri last part of the URL to call (exemple : "users/xxx")
     * @return TEXT_PLAIN String from URI
     */
    public static String getOneByURITypePlainText(String uri) {
        return getOneByURIWithMediaType(uri, MediaType.TEXT_PLAIN);
    }

    /**
     * @param uri last part of the URL to call (exemple : "users/xxx")
     * @return TEXT_HTML String from URI
     */
    public static String getOneByURITypeHtml(String uri) {
        return getOneByURIWithMediaType(uri, MediaType.TEXT_HTML);
    }

    private static String getOneByURIWithMediaType(String uri,
                                                   MediaType media) {
        List<String> list = getListByURI(uri, String.class, getCachedToken(), 200, media);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    /**
     * Helper method to write webclient GET call. The call is authenticated with the specified token
     *
     * @param uri       last part of the URL to call (exemple : "users/xxx")
     * @param classType class type to retrieve (exemple User.class)
     * @param <T>       Generic type that will be retrieve (in this example : User)
     * @param token     Specified Token
     * @return the retrieved list of T
     */
    public static <T> T getOneByURI(String uri,
                                    Class<T> classType,
                                    String token) {
        List<T> list = getListByURI(uri, classType, token);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * Helper method to write webclient GET call. The call is authenticated by the cached token.
     *
     * @param uri       last part of the URL to call (exemple : "users/xxx")
     * @param classType class type to retrieve (exemple User.class)
     * @param <T>       Generic type that will be retrieve (in this example : User)
     * @return the retrieved list of T
     */
    public static <T> List<T> getListByURI(String uri,
                                           Class<T> classType) {
        return getListByURI(uri, classType, getCachedToken());
    }

    /**
     * Helper method to write webclient GET call. The call is authenticated with the specified token
     *
     * @param uri       last part of the URL to call (exemple : "users/xxx")
     * @param classType class type to retrieve (exemple User.class)
     * @param <T>       Generic type that will be retrieve (in this example : User)
     * @param token     Authentication token
     * @return the retrieved list of T
     */
    public static <T> List<T> getListByURI(String uri,
                                           Class<T> classType,
                                           String token) {
        return getListByURI(uri, classType, token, 200);
    }

    public static <T> List<T> getListByURI(String uri,
                                           Class<T> classType,
                                           int expectedStatus) {
        return getListByURI(uri, classType, getCachedToken(), expectedStatus);
    }

    public static <T> List<T> getListByURI(String uri,
                                           Class<T> classType,
                                           String token,
                                           int expectedStatus) {
        return getListByURI(uri, classType, token, expectedStatus, MediaType.APPLICATION_JSON);
    }

    public static <T> List<T> getListByURI(String uri,
                                           Class<T> classType,
                                           String token,
                                           int expectedStatus,
                                           MediaType mediaType) {
        WebTestClient.RequestHeadersSpec<?> spec = WebTestClientFactory.getWebTestClient().get().uri(uri);
        if (token != null) {
            spec = spec.headers(http -> http.setBearerAuth(token));
        }
        WebTestClient.ResponseSpec responseSpec =
                spec.accept(mediaType).exchange().expectStatus().isEqualTo(expectedStatus);
        try {
            Flux<T> flux = responseSpec.returnResult(classType).getResponseBody();
            return flux.collectList().block();
        } catch (Exception e) {
            logger.debug("Force JSON parsing with Moxy as default Jackson via WebFlux fails");
            // Need parsing JSON with Moxy
            FluxExchangeResult<String> fluxExchangeResult = responseSpec.returnResult(String.class);
            byte[] contentByte = fluxExchangeResult.getResponseBodyContent();
            if (contentByte != null) {
                String content = new String(contentByte, StandardCharsets.UTF_8);
                try {
                    // TODO Here, we only retrieve one element for the moment
                    T tList = JSONUtil.jsonStringToObjectLikeMoxy(content, classType);
                    return new ArrayList<>(Collections.singletonList(tList));
                } catch (JAXBException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        fail("Can't parse JSON result");
        return null;
    }

    public static <T> List<T> postListByURI(String uri,
                                            Object input,
                                            Class<T> ResponseClassType,
                                            String token,
                                            int expectedStatus,
                                            MediaType mediaType) {

        JSONUtil jsonUtil = new JSONUtil();
        String jsonString = jsonUtil.convertToJSON(input, input.getClass());

        WebTestClient.ResponseSpec spec = WebTestClientFactory.getWebTestClient().post().uri(uri).contentType(
                        (mediaType == null) ? MediaType.APPLICATION_JSON : mediaType).headers(
                        http -> http.setBearerAuth((token != null) ? token : getCachedToken())).bodyValue(jsonString).exchange()
                .expectStatus().isEqualTo(expectedStatus);

        try {
            Flux<T> flux = spec.returnResult(ResponseClassType).getResponseBody();
            return flux.collectList().block();
        } catch (Exception e) {
            logger.debug("Force JSON parsing with Moxy as default Jackson via WebFlux fails");
            // Need parsing JSON with Moxy
            FluxExchangeResult<String> fluxExchangeResult = spec.returnResult(String.class);
            byte[] contentByte = fluxExchangeResult.getResponseBodyContent();
            if (contentByte != null) {
                String content = new String(contentByte, StandardCharsets.UTF_8);
                try {
                    // TODO Here, we only retrieve one element for the moment
                    T tList = JSONUtil.jsonStringToObjectLikeMoxy(content, ResponseClassType);
                    return new ArrayList<>(Collections.singletonList(tList));
                } catch (JAXBException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        fail("Can't parse JSON result");
        return null;
    }

    /**
     * Analyse the error response to find some code and expected message
     *
     * @param returnedStrings the string errors returned
     * @param code            the expected code
     * @param containsString  the expected part of message
     * @return true if contains both code and message
     */
    public static boolean expectedErrors(List<String> returnedStrings,
                                         int code,
                                         String containsString) {
        boolean containsCode = false;
        boolean containsMessage = false;
        for (String r : returnedStrings) {
            if (r.contains("" + code)) {
                containsCode = true;
            }
            if (r.contains(containsString)) {
                containsMessage = true;
            }
        }
        return containsCode & containsMessage;
    }

    /**
     * Analyse the string response to find expected message
     *
     * @param returnedStrings the string returned
     * @param containsString  the expected part of message
     * @return true if contains all the messages
     */
    public static boolean expectedStrings(List<String> returnedStrings,
                                          String containsString) {
        boolean containsMessage = false;
        for (String r : returnedStrings) {
            if (r.contains(containsString)) {
                containsMessage = true;
                break;
            }
        }
        return containsMessage;
    }


    /**
     * Helper method to write webclient PUT call. The call is authenticated by the cached token.
     *
     * @param uri   last part of the URL to call (exemple : "users/xxx")
     * @param input modified object
     */
    public static void putThatExpectedStatusOk(String uri,
                                               Object input) {
        putThatExpectedStatusOk(uri, input, getCachedToken());
    }

    /**
     * Helper method to write webclient PUT call. The call is authenticated by given token.
     *
     * @param uri   last part of the URL to call (exemple : "users/xxx")
     * @param input modified object
     * @param token the authentication token
     */
    public static void putThatExpectedStatusOk(String uri,
                                               Object input,
                                               String token) {
        WebTestClientFactory.getWebTestClient().put().uri(uri).headers(http -> http.setBearerAuth(token)).bodyValue(input)
                .exchange().expectStatus().isOk();
    }

    public static void putWithExpectedStatus(String uri,
                                             Object input,
                                             HttpStatus status) {
        putWithExpectedStatus(uri, input, getCachedToken(), status);
    }

    public static void putWithExpectedStatus(String uri,
                                             Object input,
                                             String token,
                                             HttpStatus status) {
        WebTestClientFactory.getWebTestClient().put().uri(uri).headers(http -> http.setBearerAuth(token)).bodyValue(input)
                .exchange().expectStatus().isEqualTo(status);
    }


    public static String getIdForCreateStatusOk(String uri,
                                                Object input) {

        Flux<CreationResponse> flux =
                WebTestClientFactory.getWebTestClient().post().uri(uri).headers(http -> http.setBearerAuth(getCachedToken()))
                        .bodyValue(input).exchange().expectStatus().isOk().returnResult(CreationResponse.class)
                        .getResponseBody();
        List<CreationResponse> creationResponses = flux.collectList().block();
        String id = null;
        if (creationResponses != null && !creationResponses.isEmpty()) {
            id = creationResponses.get(0).getPublicId();
        }
        return id;
    }

    public static String getIdForUpdateStatusOk(String uri,
                                                Object input) {

        Flux<CreationResponse> flux =
                WebTestClientFactory.getWebTestClient().put().uri(uri).headers(http -> http.setBearerAuth(getCachedToken()))
                        .bodyValue(input).exchange().expectStatus().isOk().returnResult(CreationResponse.class)
                        .getResponseBody();
        List<CreationResponse> creationResponses = flux.collectList().block();
        String id = null;
        if (creationResponses != null && !creationResponses.isEmpty()) {
            id = creationResponses.get(0).getPublicId();
        }
        return id;
    }

    public static List<String> postForCreationStatusOther(String uri,
                                                          Object input,
                                                          int status) {
        Flux<String> flux =
                WebTestClientFactory.getWebTestClient().post().uri(uri).headers(http -> http.setBearerAuth(getCachedToken()))
                        .bodyValue(input).exchange().expectStatus().isEqualTo(status).returnResult(String.class)
                        .getResponseBody();
        return flux.collectList().block();
    }

    public static List<String> postForEditionStatusOther(String uri,
                                                         Object input,
                                                         int status) {
        Flux<String> flux =
                WebTestClientFactory.getWebTestClient().put().uri(uri).headers(http -> http.setBearerAuth(getCachedToken()))
                        .bodyValue(input).exchange().expectStatus().isEqualTo(status).returnResult(String.class)
                        .getResponseBody();
        return flux.collectList().block();
    }

    /**
     * Helper method to write webclient DELETE call. The call is authenticated by the cached token.
     *
     * @param uri   last part of the URL to call (exemple : "users/xxx")
     * @param input modified object
     */
    public static void deleteThatExpectedStatusOk(String uri,
                                                  Object input) {
        WebTestClientFactory.getWebTestClient().method(HttpMethod.DELETE).uri(uri)
                .headers(http -> http.setBearerAuth(getCachedToken())).bodyValue(input).exchange()
                .expectStatus().isOk();
    }

    /**
     * Use this method when DTO need a xsi:type
     */
    public static <T> WebTestClient.ResponseSpec putOrPostWithXSItype(boolean put,
                                                                      String uri,
                                                                      Class<T> classType,
                                                                      Object input,
                                                                      int status) {
        JSONUtil jsonUtil = new JSONUtil();
        String jsonString = jsonUtil.convertToJSON(input, classType);
        WebTestClient.ResponseSpec flux;
        if (put) {
            flux = WebTestClientFactory.getWebTestClient().put().uri(uri).contentType(MediaType.APPLICATION_JSON)
                    .headers(http -> http.setBearerAuth(getCachedToken())).bodyValue(jsonString).exchange()
                    .expectStatus().isEqualTo(status);
        } else { // a post request
            flux = WebTestClientFactory.getWebTestClient().post().uri(uri).contentType(MediaType.APPLICATION_JSON)
                    .headers(http -> http.setBearerAuth(getCachedToken())).bodyValue(jsonString).exchange()
                    .expectStatus().isEqualTo(status);
        }
        return flux;
    }

    public static List<String> getPlainTextResultFromUri(String uri) {
        return getPlainTextResultFromUri(uri, getCachedToken());
    }

    public static List<String> getPlainTextResultFromUri(String uri,
                                                         String token) {
        WebTestClient.RequestHeadersSpec<?> spec = WebTestClientFactory.getWebTestClient().get().uri(uri);
        if (token != null) {
            spec = spec.headers(http -> http.setBearerAuth(token));
        }
        WebTestClient.ResponseSpec responseSpec = spec.accept(MediaType.TEXT_PLAIN).exchange().expectStatus().isOk();

        Flux<String> flux = responseSpec.returnResult(String.class).getResponseBody();
        return flux.collectList().block();
    }

    public static List<String> getPlainTextResultFromUriWithoutToken(String uri) {
        return getPlainTextResultFromUri(uri, null);
    }

}

