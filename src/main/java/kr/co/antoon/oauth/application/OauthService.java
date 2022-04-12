package kr.co.antoon.oauth.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import kr.co.antoon.member.domain.User;
import kr.co.antoon.oauth.domain.KakaoProfile;
import kr.co.antoon.oauth.dto.KakaoOauthToken;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class OauthService {

//    connection 생성
//    POST로 보낼 Body 작성
//    받아온 결과 JSON 파싱 (Gson)
      public KakaoOauthToken getKakaoAccessToken (String code) {
        String access_Token = "";
        String refresh_Token = "";
        String token_type = "";
        int expires_in = 0;
        String scope = "";
        int refresh_token_expires_in = 0;
        String reqURL = "https://kauth.kakao.com/oauth/token";


        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=f6acfbb7d33eda1f20e85f1f84fb640a"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:8080/login/oauth2/code/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            token_type = element.getAsJsonObject().get("token_type").getAsString();
            expires_in = element.getAsJsonObject().get("expires_in").getAsInt();
            scope = element.getAsJsonObject().get("scope").getAsString();
            refresh_token_expires_in = element.getAsJsonObject().get("refresh_token_expires_in").getAsInt();



            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        KakaoOauthToken kakaoOauthToken = KakaoOauthToken.builder()
                .access_token(access_Token)
                .token_type(token_type)
                .refresh_token(refresh_Token)
                .expires_in(expires_in)
                .scope(scope)
                .refresh_token_expires_in(refresh_token_expires_in)
                .build();

        return kakaoOauthToken;
    }

    public User createKakaoUser(String token) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        User user = null;

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            int id = element.getAsJsonObject().get("id").getAsInt();
            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            String email = "";
            if(hasEmail){
                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            }
            String name = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
            String img = element.getAsJsonObject().get("properties").getAsJsonObject().get("profile_image").getAsString();

            System.out.println("id : " + id);
            System.out.println("email : " + email);

            user = User.builder()
                    .name(name)
                    .email(email)
                    .profileImg(img)
                    .build();

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public KakaoProfile getKakaoProfile(String accessToken){

        RestTemplate rt = new RestTemplate();
        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+accessToken);
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        //  Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음
        ResponseEntity<String> response2 = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(response2.getBody(),KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }
}
