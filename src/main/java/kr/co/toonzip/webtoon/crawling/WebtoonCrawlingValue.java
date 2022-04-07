package kr.co.toonzip.webtoon.crawling;

public class WebtoonCrawlingValue {

    /**
     * NABER WEBTOON CRAWLING URL AND TAG
     **/
    public static final String NAVER_WEEKEND_URL = "https://comic.naver.com/webtoon/weekday";
    public static final String NAVER_WEEKEND_ELEMENTS = "div.list_area.daily_all div.col";
    public static final String NAVER_ALL_URLS = "ul li div.thumb a";
    public static final String NAVER_WEBTOON_DOMIN = "https://comic.naver.com";
    public static final String NAVER_WEBTOON_URL_ATTRIBUTE_KEY = "href";
    public static final String NAVER_WEBTOON_DAT_ATTRIBUTE_KEY = "h4";
    public static final String[] NAVER_WEBTTON_SCORE = {"div.rating_type", "strong"};
    public static final String NAVER_INNSER_ELEMENTS = "div.comicinfo";
    public static final String NAVER_WEBTOON_TITLE = "span.title";
    public static final String NAVER_WEBTOON_CONTENT = "p";
    public static final String NAVER_WEBTOON_WRITER = "span.wrt_nm";
    public static final String[] NAVER_WEBTOON_THUMBAIL = {"div.thumb img", "src"};
    public static final String NAVER_WEBTOON_GENRE = "span.genre";
}