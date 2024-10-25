package request;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomParserTest {

    @Test
    public void testParse() {
        String requestString = "POST /api/users?username=john&email=john@example.com HTTP/1.1\n" +
                "Host: example.com\n" +
                "Content-Type: application/json\n";

        ParsedRequest parsedRequest = CustomParser.parse(requestString);

        Assert.assertEquals(parsedRequest.getMethod(), "POST");
        Assert.assertEquals(parsedRequest.getPath(), "/api/users");
        Assert.assertEquals(parsedRequest.getQueryParam("username"), "john");
        Assert.assertEquals(parsedRequest.getQueryParam("email"), "john@example.com");
    }

    @Test
    public void testParseWithoutQueryParams() {
        String requestString = "GET /api/users HTTP/1.1\n" +
                "Host: example.com\n";

        ParsedRequest parsedRequest = CustomParser.parse(requestString);

        Assert.assertEquals(parsedRequest.getMethod(), "GET");
        Assert.assertEquals(parsedRequest.getPath(), "/api/users");
        Assert.assertNull(parsedRequest.getQueryParam("username"));
    }
}
