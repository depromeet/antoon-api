package kr.co.antoon.user.presentation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.antoon.common.dto.SwaggerNote;
import kr.co.antoon.oauth.config.AuthUser;
import kr.co.antoon.user.application.UserService;
import kr.co.antoon.user.dto.request.UserDetailRequest;
import kr.co.antoon.user.dto.response.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kr.co.antoon.common.Utility.APPLICATION_JSON_UTF_8;

@Api(tags = "사용자 API")
@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_UTF_8)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "사용자 마이페이지 조회 API", notes = SwaggerNote.USER_READ_DETAIL)
    @GetMapping
    public ResponseEntity<UserDetailResponse> getUser(@AuthUser Long userId) {
        var response = userService.findById(userId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "사용자 마이페이지 수정 API", notes = SwaggerNote.USER_UPDATE_DETAIL)
    @PatchMapping
    public ResponseEntity<UserDetailResponse> updateUser(
            @AuthUser Long userId,
            @RequestBody UserDetailRequest request
    ) {
        var response = userService.updateById(userId, request);
        return ResponseEntity.ok(response);
    }
}