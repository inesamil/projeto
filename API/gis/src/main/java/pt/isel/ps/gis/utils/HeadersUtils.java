package pt.isel.ps.gis.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HeadersUtils {

    private final static String SIREN_MEDIA_TYPE = "application/vnd.siren+json";
    private final static String JSON_HOME_MEDIA_TYPE = "application/home+json";
    private final static String COLLECTION_JSON_MEDIA_TYPE = "application/vnd.collection+json";

    /**
     * Set content type header of HTTP protocol to siren hypermedia
     *
     * @param headers instance of HttpHeaders to add the header content type
     * @return the headers instance
     */
    public static HttpHeaders setSirenContentType(HttpHeaders headers) {
        headers.setContentType(org.springframework.http.MediaType.valueOf(SIREN_MEDIA_TYPE));
        return headers;
    }

    /**
     * Set content type header of HTTP protocol to json home hypermedia
     *
     * @param headers instance of HttpHeaders to add the header content type
     * @return the headers instance
     */
    public static HttpHeaders setJsonHomeContentType(HttpHeaders headers) {
        headers.setContentType(org.springframework.http.MediaType.valueOf(JSON_HOME_MEDIA_TYPE));
        return headers;
    }

    /**
     * Set content type header of HTTP protocol to collection+json hypermedia
     *
     * @param headers instance of HttpHeaders to add the header content type
     * @return the headers instance
     */
    public static HttpHeaders setCollectionContentType(HttpHeaders headers) {
        headers.setContentType(org.springframework.http.MediaType.valueOf(COLLECTION_JSON_MEDIA_TYPE));
        return headers;
    }

    /**
     * Set content type header of HTTP protocol to problem+json hypermedia
     *
     * @param headers instance of HttpHeaders to add the header content type
     * @return the headers instance
     */
    public static HttpHeaders setProblemDetailContentType(HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
        return headers;
    }
}
