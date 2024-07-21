package egor.OK.api.group;

import egor.OK.api.APIConfig;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;


/**
 * API тесты методы <a href="https://apiok.ru/dev/methods/rest/group/group.getCounters">...</a>
 */

@DisplayName("group.getCounters")
public class GetCountersAPITest {
    private final static String group_id = "70000007112205";
    private final static String method = "group.getCounters";
    private final static String format = "json";
    APIConfig config = new APIConfig();

    public static final String url = "https://api.ok.ru/api/group/getCounters";

/*
    @Test
    public void checkSig() throws NoSuchAlgorithmException {
        String secretKeyForSig = config.getSecretKeyForSig();
        System.out.println(secretKeyForSig);
    }*/


    @Test
    @DisplayName("API доступен")
    public void apiChecked() {
        RestAssured.given().log().all().get(url)
                .then().log().all().assertThat().statusCode(200);
    }

    @ParameterizedTest
    @DisplayName("anyValidType")
    @CsvSource(textBlock = """
            ADS_TOPICS,counters.ads_topics
            BLACK_LIST,counters.black_list
            CATALOGS,counters.catalogs
            DELAYED_TOPICS,counters.delayed_topics
            FRIENDS,counters
            JOIN_REQUESTS,counters.join_requests
            LINKS,counters.links
            MAYBE,counters.maybe
            MEMBERS,counters.members
            MODERATORS,counters.moderators
            MUSIC_TRACKS,counters.music_tracks
            NEW_PAID_TOPICS,counters
            OWN_PRODUCTS,counters.own_products
            PAID_MEMBERS,counters
            PAID_TOPICS,counters
            PHOTOS,counters.photos
            PHOTO_ALBUMS,counters.photo_albums
            PINNED_TOPICS,counters.pinned_topics
            PRESENTS,counters.presents
            PRODUCTS,counters.products
            PROMO_TOPICS_ON_MODERATION,counters.promo_on_moderation
            SUGGESTED_PRODUCTS,counters.suggested_products
            SUGGESTED_TOPICS,counters.suggested_topics
            THEMES,counters.themes
            UNPUBLISHED_TOPICS,counters.unpublished_topics
            VIDEOS,counters.videos
           """
    )
    public void anyValidType(String counterTypes,String expectedKey) throws NoSuchAlgorithmException {
        TreeMap<String, String> queryParams = new TreeMap<>();

        queryParams.put("application_key", config.getApplication_key());
        queryParams.put("counterTypes", counterTypes);
        queryParams.put("format", format);
        queryParams.put("group_id", group_id);
        queryParams.put("method", method);
        queryParams.put("access_token", config.getAccessToken());
        queryParams.put("sig", config.getSig(queryParams));
        RestAssured.given().queryParams(queryParams).log().all().get(url)
                .then().log().all().assertThat().statusCode(200)
                .assertThat().body(expectedKey, Matchers.notNullValue());
    }


}
