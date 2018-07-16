package pt.isel.ps.gis.utils;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertEquals;
import static pt.isel.ps.gis.utils.HeadersUtils.*;

public class HeadersUtilsTest {

    private final static String SIREN_MEDIA_TYPE = "application/vnd.siren+json";
    private final static String JSON_HOME_MEDIA_TYPE = "application/home+json";

    @Test
    public void test_set_siren_content_type() {
        HttpHeaders headers = new HttpHeaders();
        assertEquals(MediaType.valueOf(SIREN_MEDIA_TYPE), setSirenContentType(headers).getContentType());
    }

    @Test
    public void test_set_json_home_content_type() {
        HttpHeaders headers = new HttpHeaders();
        assertEquals(MediaType.valueOf(JSON_HOME_MEDIA_TYPE), setJsonHomeContentType(headers).getContentType());
    }

    @Test
    public void test_set_problem_detail_content_type() {
        HttpHeaders headers = new HttpHeaders();
        assertEquals(MediaType.APPLICATION_PROBLEM_JSON, setProblemDetailContentType(headers).getContentType());
    }
}
