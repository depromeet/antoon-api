package kr.co.antoon.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.oauth.dto.AuthInfo;
import kr.co.antoon.user.application.UserService;
import kr.co.antoon.user.dto.request.UserDetailRequest;
import kr.co.antoon.user.dto.response.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.util.CommonUtil.APPLICATION_JSON_UTF_8;

@Api(tags = "사용자 API")
@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_UTF_8)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "사용자 마이페이지 조회 API", notes = SwaggerNote.USER_READ_DETAIL)
    @GetMapping
    public ResponseEntity<UserDetailResponse> getUser(@AuthUser AuthInfo info) {
        var response = userService.findById(info.userId());
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "사용자 마이페이지 수정 API", notes = SwaggerNote.USER_UPDATE_DETAIL)
    @PatchMapping
    public ResponseEntity<UserDetailResponse> updateUser(
            @AuthUser AuthInfo info,
            @RequestBody UserDetailRequest request
    ) {
        var response = userService.updateById(info.userId(), request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "사용자 마이페이지 프로필이미지 수정 API", notes = SwaggerNote.USER_IMAGE_UPDATE_DETAIL)
    @PatchMapping("/image")
    public ResponseEntity<UserDetailResponse> updateProfileImgae(
            @AuthUser AuthInfo info,
            @RequestBody String image) {
        var response = userService.updateImgaeUrlById(info.userId(), image);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "사용자 마이페이지 이름 수정 API", notes = SwaggerNote.USER_NAME_UPDATE_DETAIL)
    @PatchMapping("/name")
    public ResponseEntity<UserDetailResponse> updateName(
        @AuthUser AuthInfo info,
        @RequestBody String name) {
        var response = userService.updateNameById(info.userId(), name);
        return ResponseEntity.ok(response);
    }
}