package kr.co.antoon.common.dto;

public class SwaggerNote {
    public final static String DISCUSSION_CREATE_NOTE = """
                    종목토론방에 댓글 달기
                    POST /api/v1/webtoons/{webtoonId}/discussions            
                        
                    Request Body
                    {
                        "content" : "이 웹툰 꿀잼" // 댓글 내용
                    }
                        
                    Response Body
                    {
                        "discussionId" : 1, // 종목토론 댓글 Id
                        "content" :  "이 웹툰 꿀잼", // 댓글 내용
                        "memberId" : 1 // 사용자 Id
                    }            
            """;

    public final static String DISCUSSION_READ_ONE_NOTE = """
                    종목토론방에 댓글 단건 조회
                    POST /api/v1/webtoons/discussions/discussionId            
                        
                    Response Body
                    {
                        "discussionId" : 1, // 종목토론 댓글 Id
                        "content" :  "이 웹툰 꿀잼", // 댓글 내용
                        "memberId" : 1 // 사용자 Id
                    }
            """;

    public final static String DISCUSSION_READL_ALL_NOTE = """
                    종목토론방에 댓글 페이지 조회
                    GET /api/v1/webtoons/discussions
                        
                    Response Body
                    {
                        "data": [
                            {
                                "discussionId": 1,
                                "content": "string",
                                "memberId": 1
                            }...
                        ],
                        "page": 0,
                        "size": 20,
                        "totalPages": 1,
                        "totalElements": 1,
                        "firstPage": true,
                        "lastPage": true
                    }
            """;

    public final static String DISCUSSION_UPDATE_NOTE = """
                    종목토론방에 댓글 업데이트 조회
                    PATCH /api/v1/webtoons/discussions/{discussionId}
                        
                    Response Body
                    {
                        "discussionId" : 1, // 종목토론 댓글 Id
                        "content" :  "이 웹툰 꿀잼", // 댓글 내용
                        "memberId" : 1 // 사용자 Id
                    }          
            """;

    public final static String DISCUSSION_DELETE_NOTE = """
                    종목토론방에 댓글 삭제
                    DELETE /api/v1/webtoons/discussions/{discussionId}
                        
                    Response Body
                    None
            """;

    public final static String RECOMMENDATION_JOIN = """
                    웹툰 상세페이지 > 탑승해요 API
                    PATCH /api/v1/recommendations/join/{webtonId}   
            """;

    public final static String RECOMMENDATION_LEAVE = """
                    웹툰 상세페이지 > 하차해요 API
                    PATCH /api/v1/recommendations/leave/{webtoonId}
            """;

    public final static String WEBTOON_READ_DETAIL = """
                    웹툰 상세페이지 조회
                    GET /api/v1/webtoons/{webtoonId}    
                        
                    Response Body
                    {
                        "title" : "재밌는 만화", // 웹툰 제목
                        "content" :  "이 웹툰 꿀잼", // 댓글 내용
                        "writer": {     // 웹툰 작가
                            "김작가", "이작가", "염작가"
                        },
                        "url" : "https://webtoon-url.com", // 웹툰 url
                        "thumbnail" : "https://picture.jpg", // 웹툰 썸네일
                        "genre" : { // 웹툰 장르
                            "스릴러", "로맨스", "코미디"
                        },
                        "status" : PUBLISH, // 연재 여부(PUBLISH - 연재, PAUSE - 휴재)
                        "platform" : NAVER // 웹툰 플랫폼(NAVER, KAKAO)
                    }
            """;

    public final static String DISCUSSION_LIKE_CREATE_NOTE = """
                    종목토론방에 댓글 삭제
                    PUT /api/v1/webtoons/discussions/{discussionId}/likes
                        
                    Response Body
                    None
            """;

    public final static String USER_READ_DETAIL = """
                    사용자 마이페이지 조회
                    GET /api/v1/users/mypage            
                        
                    Response Body
                    {
                        "name" : "김테스트", //사용자 이름
                        "email" : "test@naver.com", // 사용자 이메일
                        "imageUrl" : "https://image.jpg", // 프로필 이미지 url
                        "age" : 0   // 사용자 연령대 (default=0)
                    }
            """;

    public final static String WEBTOON_SEARCH = """
                    웹툰 검색 조회
                    GET /api/v1/webtoons
                        
                    Response Body
                    {
                        "data": [
                            {
                                "id": 1,
                                "title": "제목"
                            }...
                        ],    
                    }
            """;

    public static final String WEBTOON_READ_GENRE = """
                    장르별 활성화된 웹툰 조회
                    GET /api/v1/webtoons/genres?genre={genre}
                        
                    Response Body
                        
                    "data": [
                      {
                        "thumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/749639/thumbnail/thumbnail_IMAG04_da2eeb2c-c0ce-4433-bc82-e489df37993f.jpg",
                        "title": "남주의 첫날밤을 가져버렸다",
                        "graphScore": 649,
                        "scoreGap": 100,
                        "writers": [
                          "티바",
                          "MSG",
                          "황도톨"
                        ],
                        "platform": "NAVER",
                        "genre": "ACTION"
                      },
                      {
                        "thumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/784248/thumbnail/thumbnail_IMAG04_ed49d316-2291-4ac2-be6c-1b407acbb0e8.jpg",
                        "title": "내가 키운 S급들",
                        "graphScore": 648,
                        "scoreGap": 100,
                        "writers": [
                          "seri",
                          "비완",
                          "근서"
                        ],
                        "platform": "NAVER",
                        "genre": "ACTION"
                      },
                    ],
                    "page": 0,
                    "size": 12,
                    "totalPages": 9,
                    "totalElements": 108,
                    "firstPage": true,
                    "lastPage": false
                  }
            """;

    public final static String WEBTOON_DAY_READ_NOTE = """
                    웹툰 요일별 조회
                    GET /api/v1/webtoons/days?day={day}
                        
                    Response Body
                    {
                        "data": [
                            {
                                "webtoonId" : 1,
                                "thumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/783053/thumbnail/thumbnail_IMAG04_77f75c21-cdcc-4d23-bc00-1ff829d0a209.jpg",
                                "title": "김부장",
                                "writers": [
                                      "박태준 만화회사",
                                      "정종택"
                                ],
                                "day": "화"
                            },
                            {
                                "thumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/703846/thumbnail/thumbnail_IMAG04_ba579f29-bb71-4003-8e07-0a0418c7b32b.jpg",
                                "title": "여신강림",
                                "writers": [
                                      "야옹이"
                                ],
                                "day": "화"
                            }, ...
                        ],
                        "page": 0,
                        "size": 12,
                        "totalPages": 1,
                        "totalElements": 1,
                        "firstPage": true,
                        "lastPage": true
                    }
            """;

    public final static String AUTH_RFRESH = """
                    토큰 재발급
                    POST /api/v1/auth/refresh
                    (헤더에 리프레시 토큰을 "Refresh"로 추가해서 api 요청해주세요!)
                        
                    Response Body
                    {
                        "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlVTRVIiLCJleHAiOjE2NTczMTI2MjZ9.oapnD4a2jvO4WqLprv4-1FeVpx3dWgNCDUtnrZT7-JA",
                        "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjU3MzEyNjI2fQ.iDsaEI9JN3YYEdsaUhP-EQJBhaz4llO-wNo8kpDhgGk"
                    }
            """;

    public final static String AUTH_LOGOUT = """
                    로그아웃
                    POST /api/v1/auth/logout
                    (헤더에 리프레시 토큰과 엑세스 토큰을 추가해서 api 요청해주세요!)
                    (access Token 요청 시엔 "Bearer " + accessToken 으로 추가해서 요청해주세요!
                        
                    Request Header
                    {
                        "Authorization": "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlVTRVIiLCJleHAiOjE2NTczMTI2MjZ9.oapnD4a2jvO4WqLprv4-1FeVpx3dWgNCDUtnrZT7-JA",
                        "Refresh": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjU3MzEyNjI2fQ.iDsaEI9JN3YYEdsaUhP-EQJBhaz4llO-wNo8kpDhgGk"
                    }     
            """;

    public final static String WEBTOON_READ_GENRES = """
                    메인페이지 장르별 웹툰 조회 
                    GET /api/v1/webtoons/genres
                       
                    Response Body
                    {
                        "webtoons": [
                            {
                                "genre": "EVERYDAY",
                                "thumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/785250/thumbnail/thumbnail_IMAG04_b2a285d6-0172-44cc-bfb0-ea5eaab41f1a.jpg"
                            },
                            {
                                "genre": "EVERYDAY",
                                "thumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/725586/thumbnail/thumbnail_IMAG04_147bdc44-14ef-4a49-81d0-aeec09b898be.jpg"
                            },
                            {
                                "genre": "EVERYDAY",
                                "thumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/784140/thumbnail/thumbnail_IMAG04_fdda97b7-d253-4918-8f18-3c1f207c5ab3.jpg"
                            }, ...       
            """;

    public final static String USER_UPDATE_DETAIL = """
                    사용자 마이페이지 수정
                    PATCH /api/v1/users/mypage            
                        
                    Request Body
                    {
                        "name" : "김테스트", //사용자 이름
                        "imageUrl" : "https://image.jpg" // 프로필 이미지 url
                    }
            """;
    public static final String WEBTOON_READ_RANKING_NOTE = """
            현재 시간 기준으로 상승 중인 웹툰 조회
            GET /api/v1/webtoons/top-up
                        
            {
               "webtoons": [
                 {
                   "id": 602,
                   "url": "https://page.kakao.com/home?seriesId=53190884",
                   "thumbnail": "https://dn-img-page.kakao.com/download/resource?kid=cbOcrh/hzhOeyLVuz/o3mzWEkKKAuJOLWv6bYJxK&filename=th2",
                   "title": "도굴왕",
                   "score": 650,
                   "scoreGapPercent": 0,
                   "snapshotTime": "2022-05-13T03:59:01",
                   "activeStatus": "연재",
                   "platform": "KAKAO"
                 },
                 {
                   "id": 605,
                   "url": "https://page.kakao.com/home?seriesId=58777646",
                   "thumbnail": "https://dn-img-page.kakao.com/download/resource?kid=KWNVT/hzp2f362bW/NWT2ViqCrkIlWJhAlPwQp1&filename=th2",
                   "title": "백작가의 사생아가 결혼하면",
                   "score": 650,
                   "scoreGapPercent": 0,
                   "snapshotTime": "2022-05-13T03:59:01",
                   "activeStatus": "연재",
                   "platform": "KAKAO"
                 },
                 {
                   "id": 617,
                   "url": "https://page.kakao.com/home?seriesId=58663937",
                   "thumbnail": "https://dn-img-page.kakao.com/download/resource?kid=c9BidG/hzhOhJV5W3/ApkqR0bIKHmF8faTtNCm60&filename=th2",
                   "title": "남편님, 다시 결혼해 주세요!",
                   "score": 650,
                   "scoreGapPercent": 0,
                   "snapshotTime": "2022-05-13T03:59:02",
                   "activeStatus": "연재",
                   "platform": "KAKAO"
                 },
                 {
                   "id": 539,
                   "url": "https://page.kakao.com/home?seriesId=56657309",
                   "thumbnail": "https://dn-img-page.kakao.com/download/resource?kid=ceQX6q/hzmU2m9nxu/dJoslDiIylEt2nzmoSCCeK&filename=th2",
                   "title": "당신의 이해를 돕기 위하여",
                   "score": 650,
                   "scoreGapPercent": 0,
                   "snapshotTime": "2022-05-11T22:55:13",
                   "activeStatus": "연재",
                   "platform": "KAKAO"
                 }
               ]
             }
            """;
}