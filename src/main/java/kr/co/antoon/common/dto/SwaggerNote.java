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
                        "webtoonId" : 1,  // 웹툰 Id
                        "discussionId" : 1, // 종목토론 댓글 Id
                        "content" :  "이 웹툰 꿀잼", // 댓글 내용
                        "userId" : 1, // 사용자 Id
                        "nickname" : "개미는 툰툰",  // 사용자 닉네임
                        "imageUrl" : "https://picture.jpg",  // 댓글 작성자 프로필 URL
                        "likeCount" : 1,  // 좋아요 카운트
                        "isUserLike": true   // 사용자 좋아요 여부,
                        "time": "10분 전"
                    }            
            """;

    public final static String DISCUSSION_READ_ONE_NOTE = """
                    종목토론방에 댓글 단건 조회
                    POST /api/v1/webtoons/discussions/discussionId           
                        
                    Response Body
                    {
                        "webtoonId" : 1,  // 웹툰 Id
                        "discussionId" : 1, // 종목토론 댓글 Id
                        "content" :  "이 웹툰 꿀잼", // 댓글 내용
                        "userId" : 1, // 사용자 Id
                        "nickname" : "개미는 툰툰",  // 사용자 닉네임
                        "imageUrl" : "https://picture.jpg",  // 댓글 작성자 프로필 URL
                        "likeCount" : 1,  // 좋아요 카운트
                        "isUserLike": true,   // 사용자 좋아요 여부
                        "time": "1시간 전"
                    }
            """;

    public final static String DISCUSSION_READL_ALL_NOTE = """
                    종목토론방에 댓글 페이지 조회
                    GET /api/v1/webtoons/discussions
                        
                    Response Body
                    {
                        "data": [
                            {
                                "webtoonId" : 1,  // 웹툰 Id
                                "discussionId" : 1, // 종목토론 댓글 Id
                                "content" :  "이 웹툰 꿀잼", // 댓글 내용
                                "userId" : 1, // 사용자 Id
                                "nickname" : "개미는 툰툰",  // 사용자 닉네임
                                "imageUrl" : "https://picture.jpg",  // 댓글 작성자 프로필 URL
                                "likeCount" : 1,  // 좋아요 카운트
                                "isUserLike": true,   // 사용자 좋아요 여부
                                "time": "3개월 전"
                            },
                            {
                                "webtoonId" : 1,
                                "discussionId" : 1,
                                "content" :  "우앙 재밌다",
                                "userId" : 1,
                                "nickname" : "개미는 툰툰",
                                "imageUrl" : "https://picture.jpg",
                                "likeCount" : 0,
                                "isUserLike": false,
                                "time": "2019-06-12"  // 1년 이상된 댓글은 날짜로 보냄
                            }, ...
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
                        "webtoonId" : 1,  // 웹툰 Id
                        "discussionId" : 1, // 종목토론 댓글 Id
                        "content" :  "이 웹툰 꿀잼", // 댓글 내용
                        "userId" : 1, // 사용자 Id
                        "nickname" : "개미는 툰툰",  // 사용자 닉네임
                        "imageUrl" : "https://picture.jpg",  // 댓글 작성자 프로필 URL
                        "likeCount" : 1,  // 좋아요 카운트
                        "isUserLike": true,   // 사용자 좋아요 여부
                        "time": "40초 전"
                    }          
            """;

    public final static String DISCUSSION_DELETE_NOTE = """
                    종목토론방에 댓글 삭제
                    DELETE /api/v1/webtoons/discussions/{discussionId}
                        
                    Response Body
                    None
            """;

    public final static String WEBTOON_JOIN = """
                    웹툰 상세페이지 > 탑승/하차 API
                    status값 : JOIN, LEAVE
                    PATCH /api/v1/webtoons/{webtonId}/join?status={status}
                    
                    Response Body
                    {
                        "joinCount": 2,
                        "leaveCount": 1,
                        "isJoined": true,
                        "isLeaved": false
                    }
            """;

    public final static String WEBTOON_READ_DETAIL = """
                    웹툰 상세페이지 조회
                    GET /api/v1/webtoons/{webtoonId}
                        
                    Response Body
                    {
                          "@class": "kr.co.antoon.webtoon.dto.WebtoonDto",
                          "webtoonId": 1,
                          "title": "엽총소년",
                          "content": "약자에서 영웅이 될 수밖에 없었던 소년 '한규환', 그리고 그 소년을 영웅으로 만들어줄 엽총 한 자루가 눈앞에 있다. 글로벌 누적 조회수 12억 뷰를 기록한 <스위트홈>의 프리퀄 대공개!",
                          "webtoonUrl": "https://comic.naver.com/webtoon/list?titleId=759925&weekday=tue",
                          "thumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/759925/thumbnail/thumbnail_IMAG04_5763c122-5372-441d-ad61-261e079ff561.jpg",
                          "platform": "NAVER",
                          "platformDescription": "네이버 웹툰",
                          "status": "PUBLISH",
                          "statusDescription": "연재",
                          "genres": [
                              {
                                  "webtoonGenreId": 2,
                                  "genreCategory": "THRILLER",
                                  "genreCategoryDescription": "스릴러"
                              },
                              {
                                  "webtoonGenreId": 1,
                                  "genreCategory": "STORY",
                                  "genreCategoryDescription": "스토리"
                              }
                          ],
                          "publishDays": [
                              {
                                  "webtoonPublishDayId": 1,
                                  "day": "화"
                              }
                          ],
                          "writers": [
                              {
                                  "webtoonWriterId": 1,
                                  "name": "김칸비"
                              },
                              {
                                  "webtoonWriterId": 2,
                                  "name": "홍필"
                              }
                          ],
                          "webtoonStatusCountId": 6,
                          "joinCount": 1,
                          "leaveCount": 1,
                          "score": 648,
                          "scoreGap": 0,
                          "scoreGapPercent": 0.0,
                          "webtoonStatus": "LEAVED",
                          "ranking": 1,
                          "characters": [
                              {
                                  "characterId": 3,
                                  "name": "문서하",
                                  "imageUrl": "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/%EC%9D%B8%EB%AC%BC%EC%B0%A8%ED%8A%B82.png"
                              },
                              {
                                  "characterId": 1,
                                  "name": "최푸름",
                                  "imageUrl": "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/%EC%9D%B8%EB%AC%BC%EC%B0%A8%ED%8A%B8.png"
                              },
                              {
                                  "characterId": 4,
                                  "name": "공주영",
                                  "imageUrl": "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/%EC%9D%B8%EB%AC%BC%EC%B0%A8%ED%8A%B83.png"
                              }
                          ]
                      }
            """;

    public final static String DISCUSSION_LIKE_CREATE_NOTE = """
                    종목토론방 좋아요
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
                    "일상", "개그",  "판타지",  "액션", "드라마", "로맨스", "감성", "스릴러", "무협/사극", "스포츠", "옴니버스", "스토리","에피소드", "없음"
                    
                    
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
                    (헤더에 엑세스 토큰만 추가해서 api 요청해주세요!)
                    (access Token 요청 시엔 "Bearer " + accessToken 으로 추가해서 요청해주세요!
                        
                    Request Header
                    {
                        "Authorization": "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlVTRVIiLCJleHAiOjE2NTczMTI2MjZ9.oapnD4a2jvO4WqLprv4-1FeVpx3dWgNCDUtnrZT7-JA",
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

    public final static String USER_IMAGE_UPDATE_DETAIL = """
                    사용자 마이페이지 프로필 이미지 수정
                    PATCH /api/v1/users/mypage/image            
                        
                    Request Body
                    {
                        "file" : image.jpg // 업로드 할 이미지 파일
                    }
            """;

    public final static String USER_NAME_UPDATE_DETAIL = """
                    사용자 마이페이지 이름 수정
                    PATCH /api/v1/users/mypage/name            
                        
                    Request Body
                    {
                        "name" : "김테스트", //사용자 이름
                    }
            """;


    public static final String WEBTOON_READ_RANKING_NOTE = """
            현재 시간 기준으로 상승 중인 웹툰 조회
            GET /api/v1/webtoons/top-up
                        
            Response Body
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
                 }
               ]
             }
            """;
    public static final String GRAPH_SCORES_DAY_READ_NOTE = """
            1일 기준 그래프 조회
            GET /api/v1/webtoons/{webtoonId}/graph-scores/days
                        
            Response Body            
            {
              "count": 32,
              "graphScores": [
                {
                  "graphScore": 648,
                  "scoreGap": 0,
                  "scoreGapPercent": 0,
                  "webtoonId": 1,
                  "snapshotTime": "2022-05-28T14:00:45",
                  "status": "MAINTAIN",
                  "graphStatusDescription": "유지"
                }
              ]
            }
            """;
    public static final String GRAPH_SCORES_WEEKENDS_READ_NOTE = """
            1주일 기준 그래프 조회
            GET /api/v1/webtoons/{webtoonId}/graph-scores/weekends
                        
            Response Body
            {
              "count": 7,
              "graphScores": [
                {
                  "graphScore": 648,
                  "scoreGap": 0,
                  "scoreGapPercent": 0,
                  "webtoonId": 1,
                  "snapshotTime": "2022-05-29T13:00:35",
                  "status": "MAINTAIN",
                  "graphStatusDescription": "유지"
                }
              ]
            }
            """;
    public static final String GRAPH_SCORES_MONTHS_READ_NOTE = """
            1달 기준 그래프 조회
            GET /api/v1/webtoons/{webtoonId}/graph-scores/months
                        
            ResponseBody
            {
              "count": 0,
              "graphScores": [
                {
                  "graphScore": 0,
                  "graphStatusDescription": "string",
                  "scoreGap": 0,
                  "scoreGapPercent": 0,
                  "snapshotTime": "2022-05-29T04:27:36.088Z",
                  "status": "DOWN",
                  "webtoonId": 0
                }
              ]
            }
            """;
    public static final String GRAPH_SCORE_THREE_MONTH_READ_NOTE = """
            3개월 기준 그래프 조회
            GET /api/v1/webtoons/{webtoonId}/graph-scores/three-months
                        
            ResponseBody
            {
              "count": 0,
              "graphScores": [
                {
                  "graphScore": 0,
                  "graphStatusDescription": "string",
                  "scoreGap": 0,
                  "scoreGapPercent": 0,
                  "snapshotTime": "2022-05-29T04:28:40.611Z",
                  "status": "DOWN",
                  "webtoonId": 0
                }
              ]
            }
            """;

    public final static String GET_COIN_HISTORY = """
                    코인 지급 히스토리 조회
                    GET /api/v1/coins/history  
                    
                    RemittanceType : {"웹툰 탑승", "웹툰 하차", "회원가입", "인물 탑승", "커플 탑승", "투표 참여"}  
                        
                    Response Body
                    {
                        "date" : "2022-06-18", 
                        "status" : "PLUS" (or "MINUS"),
                        "amount" : 3,
                        "wallet" : 305,
                        "remittanceType" : "웹툰 탑승",
                        "reason" : "유미의 세포들"
                    }
            """;

    public final static String GET_CHARACTER_RANK = """
                    인물 순위(실시간 차트) 조회
                    GET /api/v1/top-ranks/characters?type=PERSONA
                    
                    Response Body
                    {
                         "couples": [
                             {
                                 "id": 1,
                                 "names": "최푸름",
                                 "title": "엽총소년",
                                 "thumbnails": [
                                     "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/%EC%9D%B8%EB%AC%BC%EC%B0%A8%ED%8A%B8.png"
                                 ],
                                 "coinAmount": 6,
                                 "isJoined": true
                             },
                             {
                                 "id": 3,
                                 "names": "문서하",
                                 "title": "엽총소년",
                                 "thumbnails": [
                                     "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/%EC%9D%B8%EB%AC%BC%EC%B0%A8%ED%8A%B82.png"
                                 ],
                                 "coinAmount": 3,
                                 "isJoined": true
                             },
                             ...
                         ]
                    }
                    
                    
                    커플 순위(실시간 차트) 조회
                    GET /api/v1/top-ranks/characters?type=COUPLE
                    
                    Response Body
                    {
                         "couples": [
                             {
                                 "id": 2,
                                 "names": "병현♥푸름",
                                 "title": "엽총소년",
                                 "thumbnails": [
                                     "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%8C%EC%B0%A8%ED%8A%B81-1.png",
                                     "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%8C%EC%B0%A8%ED%8A%B81-2.png"
                                 ],
                                 "coinAmount": 5,
                                 "isJoined": false
                             },
                             {
                                 "id": 6,
                                 "names": "혜린♥홍빈",
                                 "title": "엽총소년",
                                 "thumbnails": [
                                     "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%8C%EC%B0%A8%ED%8A%B82-1.png",
                                     "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%8C%EC%B0%A8%ED%8A%B82-2.png"
                                 ],
                                 "coinAmount": 3,
                                 "isJoined": true
                             },
                             ...
                     }
            """;

    public final static String GET_CHARACTER_DETAIL = """
                    인물 상세 조회 
                    GET /api/v1/characters/{characterId}?type=PERSONA
                    
                    Response Body
                    {
                        "name": "최푸름",
                        "characterThumbnail": "test1.png",
                        "rank": 1,
                        "content": "평범하면서 금사빠인 고등학생 순정남",
                        "backGroundColor": "4E7F86",
                        "coinAmount": 6,
                        "webtoonId": 1,
                        "webtoonThumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/792780/thumbnail/thumbnail_IMAG04_b0eeb63e-4f46-4747-b5e7-56f2299e0f7f.jpg",
                        "title": "연애혁명",
                        "score": 250,
                        "isJoined": true,
                        "joinedCount": 2
                    }
                    
                    
                    커플 상세조회 
                    GET /api/v1/characters/{characterId}?type=COUPLE
                    
                    Response Body
                    {
                        "name": "푸름♥병현",
                        "characterThumbnail": "test.png",
                        "rank": 1,
                        "content": "평범하면서 금사빠인 고등학생 순정남과 순정녀의 어마무시한 관계",
                        "backGroundColor": "4E7F86",
                        "coinAmount": 3,
                        "webtoonId": 1,
                        "webtoonThumbnail": "https://shared-comic.pstatic.net/thumb/webtoon/792780/thumbnail/thumbnail_IMAG04_b0eeb63e-4f46-4747-b5e7-56f2299e0f7f.jpg",
                        "title": "연애혁명",
                        "score": 250,
                        "isJoined": true,
                        "joinedCount": 1
                    }
            
            """;

    public final static String JOINED_CHARACTER = """
                    인물/커플 탑승하기
                    PATCH /api/v1/characters/{characterId}
                    
                    Response Body
                    None
            """;

    public final static String CHARACTER_DISCUSSION_CREATE_NOTE = """
                    댓글 달기
                    POST /api/v1/characters/{characterId}/discussions            
                        
                    Request Body
                    {
                        "content" : "이 웹툰 꿀잼" // 댓글 내용
                    }
                        
                    Response Body
                    {
                        "characterId" : 1,  // 웹툰 Id
                        "discussionId" : 1, // 종목토론 댓글 Id
                        "content" :  "이 웹툰 꿀잼", // 댓글 내용
                        "userId" : 1, // 사용자 Id
                        "nickname" : "개미는 툰툰",  // 사용자 닉네임
                        "imageUrl" : "https://picture.jpg",  // 댓글 작성자 프로필 URL
                        "likeCount" : 1,  // 좋아요 카운트
                        "isUserLike": true   // 사용자 좋아요 여부,
                        "time": "10분 전"
                    }            
            """;

    public final static String CHARACTER_DISCUSSION_READ_ONE_NOTE = """
                    댓글 단건 조회
                    POST /api/v1/characters/discussions/{characterId}           
                        
                    Response Body
                    {
                        "characterId" : 1,  // 웹툰 Id
                        "discussionId" : 1, // 종목토론 댓글 Id
                        "content" :  "이 웹툰 꿀잼", // 댓글 내용
                        "userId" : 1, // 사용자 Id
                        "nickname" : "개미는 툰툰",  // 사용자 닉네임
                        "imageUrl" : "https://picture.jpg",  // 댓글 작성자 프로필 URL
                        "likeCount" : 1,  // 좋아요 카운트
                        "isUserLike": true,   // 사용자 좋아요 여부
                        "time": "1시간 전"
                    }
            """;

    public final static String CHARACTER_DISCUSSION_READL_ALL_NOTE = """
                    댓글 페이지 조회
                    GET /api/v1/characters/discussions
                        
                    Response Body
                    {
                        "data": [
                            {
                                "characterId" : 1,  // 웹툰 Id
                                "discussionId" : 1, // 종목토론 댓글 Id
                                "content" :  "이 웹툰 꿀잼", // 댓글 내용
                                "userId" : 1, // 사용자 Id
                                "nickname" : "개미는 툰툰",  // 사용자 닉네임
                                "imageUrl" : "https://picture.jpg",  // 댓글 작성자 프로필 URL
                                "likeCount" : 1,  // 좋아요 카운트
                                "isUserLike": true,   // 사용자 좋아요 여부
                                "time": "3개월 전"
                            },
                            {
                                "characterId" : 1,
                                "discussionId" : 1,
                                "content" :  "우앙 재밌다",
                                "userId" : 1,
                                "nickname" : "개미는 툰툰",
                                "imageUrl" : "https://picture.jpg",
                                "likeCount" : 0,
                                "isUserLike": false,
                                "time": "2019-06-12"  // 1년 이상된 댓글은 날짜로 보냄
                            }, ...
                        ],
                        "page": 0,
                        "size": 20,
                        "totalPages": 1,
                        "totalElements": 1,
                        "firstPage": true,
                        "lastPage": true
                    }
            """;

    public final static String CHARACTER_DISCUSSION_UPDATE_NOTE = """
                    댓글 수정
                    PATCH /api/v1/characters/discussions/{discussionId}
                    
                    Request Body
                    {
                        "content" : "이 웹툰 꿀잼" // 수정 내용
                    }
                        
                    Response Body
                    {
                        "characterId" : 1,  // 웹툰 Id
                        "discussionId" : 1, // 종목토론 댓글 Id
                        "content" :  "이 웹툰 꿀잼", // 댓글 내용
                        "userId" : 1, // 사용자 Id
                        "nickname" : "개미는 툰툰",  // 사용자 닉네임
                        "imageUrl" : "https://picture.jpg",  // 댓글 작성자 프로필 URL
                        "likeCount" : 1,  // 좋아요 카운트
                        "isUserLike": true,   // 사용자 좋아요 여부
                        "time": "40초 전"
                    }          
            """;

    public final static String CHARACTER_DISCUSSION_DELETE_NOTE = """
                    댓글 삭제
                    DELETE /api/v1/characters/discussions/{discussionId}
                        
                    Response Body
                    None
            """;

    public final static String CHARACTER_DISCUSSION_LIKE_CREATE_NOTE = """
                    종목토론방 좋아요
                    PUT /api/v1/characters/discussions/{discussionId}/likes
                        
                    Response Body
                    None
            """;
    public static final String TOPIC_READ_ALL_NOTE = """
            모든 토픽 조회
            GET /api/v1/topics/{sortType}
            Response Body
            {
              "data": [
                {
                  "topicId": 1,
                  "topicCategory": "AB",
                  "tags": [
                    "연애혁명",
                    "소년심판"
                  ],
                  "title": "이경우, 강효민 둘이 싸우면 누가 이길까?",
                  "topicVoteEndTime": "2022-06-25T15:53:50",
                  "joinCount": 2,
                  "thumbnails": [
                    "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/ANT_DEFAULT/blue.png",
                    "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/ANT_DEFAULT/red.png"
                  ]
                },
                {
                  "topicId": 2,
                  "topicCategory": "AB",
                  "tags": [
                    "연애혁명",
                    "소년심판"
                  ],
                  "title": "주영, 자림 커플은 이대로 헤어질까?",
                  "topicVoteEndTime": "2022-06-20T15:53:50",
                  "joinCount": 2,
                  "thumbnails": [
                    "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/ANT_DEFAULT/blue.png",
                    "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/ANT_DEFAULT/red.png"
                  ]
                },
                {
                  "topicId": 3,
                  "topicCategory": "CHOICE",
                  "tags": [
                    "연애혁명"
                  ],
                  "title": "이경우는 고백한다?",
                  "topicVoteEndTime": "2022-06-28T15:53:50",
                  "joinCount": 1,
                  "thumbnails": [
                    "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/ANT_DEFAULT/blue.png",
                    "https://antoon-api-bucket.s3.ap-northeast-2.amazonaws.com/ANT_DEFAULT/red.png"
                  ]
                }
              ],
              "page": 0,
              "size": 5,
              "totalPages": 1,
              "totalElements": 3,
              "firstPage": true,
              "lastPage": true
            }
            """;
}
