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
            GET /api/v1/users/{userId}            
                        
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

    public final static String WEBTOON_DAY_READ_NOTE = """
            웹툰 요일별 조회
            GET /api/v1/webtoons/days?day={day}
            
            Response Body
            {
                "data": [
                            {
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
}