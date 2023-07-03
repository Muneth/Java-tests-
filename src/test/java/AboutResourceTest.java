
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag(CATALOG)
class AboutResourceTest {

    private static final CharSequence HTML = "html";

    @Test
    void getOPTIONS() {
        WebTestClient.ResponseSpec response = WebTestClientFactory.getWebTestClient().options().uri("/about").exchange();
        response.expectHeader().valueEquals("allow", "GET, HEAD, OPTIONS");
    }

    @Test
    void sayPlainTextHello() {
        String about = WebTestClientFactory.getOneByURITypePlainText("/about");
        assertNotNull(about);
        assertFalse(about.isEmpty());
        assertFalse(about.contains(HTML));
    }

    @Test
    void sayJSONHello() {
        About about = WebTestClientFactory.getOneByURI("/about", About.class);
        assertNotNull(about);
        assertFalse(about.getInformation().isEmpty());
        assertFalse(about.getApiVersion().isEmpty());
        assertFalse(about.getProductVersion().isEmpty());
    }

    @Test
    void sayHtmlHello() {
        String about = WebTestClientFactory.getOneByURITypeHtml("/about");
        System.out.println(about);
        assertNotNull(about);
        assertFalse(about.isEmpty());
        assertTrue(about.contains(HTML));
    }
}