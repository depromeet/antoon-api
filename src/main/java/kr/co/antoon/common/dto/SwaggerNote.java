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

    public static final String WEBTOON_READ_GENRE = """
            장르별 활성화된 웹툰 조회
            GET /api/v1/webtoons/genres?genre={genre}
            
            Response Body
            {
              "webtoons": [
                {
                  "id": 864,
                  "title": "아빠같은 남자",
                  "content": "“마음에 드실거예요. 아빠같은 남자거든요.” 사이코패스 성격을 숨기고 살아가는 로펌대표 묘인준은 자신의 본성을 잘 감춘 채 재력과 권력, 그리고 겉보기에 화목한 가정까지 모든 걸 갖추고 살아가고 있다. 어느 날 끔찍히 사랑하는 외동딸 수진이 아버지 묘인준에게 결혼할 남자(정이현)를 소개하게 되는데, 묘인준은 그 순간 딸의 남자가 자신과 같은 부류의 인간임을 알아본다. 자신이 이룬 모든 것의 상징과도 같은 딸을 향해 접근하는 것은 물론, 여러 의심스러운 상황에서 계속 마주치게 되는 정이현... 묘인준은 그에게 딸을 뺏길 수 없다는 생각을 하게 되지만, 자신의 두 얼굴을 들키지 않으면서 딸을 지키기란 쉽지 않다. 거기에 더해 자신과 자신의 회사 법무법인 노블의 주변에서는 끊임없이 잡음이 들려오기 시작하고, 묘인준의 내재된 본성이 서서히 모습을 드러내며 상황은 점점 복잡해진다.",
                  "webtoonUrl": "https://comic.naver.com/webtoon/list?titleId=774451&weekday=thu",
                  "thumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/774451/thumbnail/thumbnail_IMAG04_4f6a1d0e-7e60-49d3-b94d-5c5da04d7ea4.jpg",
                  "platform": "NAVER"
                },
                {
                  "id": 918,
                  "title": "그 기사가 레이디로 사는 법",
                  "content": "가장 가까운 친우이자 믿었던 부관의 검이 내 몸을 관통했다. 다시 눈을 떴을 땐 적국의 귀족 레이디가 되어있었다. 낯설고 연약한 타인의 몸과 잃어버린 3년의 세월, 그 사이에 망해버린 나의 조국. 배신의 아픔과 정체성의 혼란으로 정신을 차릴 수가 없었다. 그러나 나는 결국 살아가기로 했다. 루시펠라 아이딘으로써, 검을 든 귀족 레이디로서.",
                  "webtoonUrl": "https://comic.naver.com/webtoon/list?titleId=758675&weekday=fri",
                  "thumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/758675/thumbnail/thumbnail_IMAG04_184211aa-efb6-4b40-9f2d-986e05a08247.jpg",
                  "platform": "NAVER"
                } 
            """;
}