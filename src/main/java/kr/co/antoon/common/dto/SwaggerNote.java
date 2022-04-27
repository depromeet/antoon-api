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

    public static final String RECOMMENDATION_JOIN = """
            웹툰 상세페이지 > 탑승해요 API
            PATCH /api/v1/recommendations/join/{webtonId}
            
            """;
    public static final String RECOMMENDATION_LEAVE = """
            웹툰 상세페이지 > 하차해요 API
            PATCH /api/v1/recommendations/leave/{webtoonId}
            """;
}