package pt.isel.ps.gis.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HeadersUtils {

    private final static String SIREN_MEDIA_TYPE = "application/vnd.siren+json";
    private final static String JSON_HOME_MEDIA_TYPE = "application/home+json";
    private final static String COLLECTION_JSON_MEDIA_TYPE = "application/vnd.collection+json";

    public static HttpHeaders setSirenContentType(HttpHeaders headers) {
        headers.setContentType(org.springframework.http.MediaType.valueOf(SIREN_MEDIA_TYPE));
        return headers;
    }

    public static HttpHeaders setJsonHomeContentType(HttpHeaders headers) {
        headers.setContentType(org.springframework.http.MediaType.valueOf(JSON_HOME_MEDIA_TYPE));
        return headers;
    }

    public static HttpHeaders setCollectionContentType(HttpHeaders headers) {
        headers.setContentType(org.springframework.http.MediaType.valueOf(COLLECTION_JSON_MEDIA_TYPE));
        return headers;
    }

    public static HttpHeaders setProblemDetailContentType(HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        return headers;
    }
}
