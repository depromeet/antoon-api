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
import org.springframework.web.bind.annotation.*;

@Api(tags = "사용자 API")
@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "사용자 마이페이지 조회 API", notes = SwaggerNote.USER_READ_DETAIL)
    @GetMapping
    public ResponseEntity<UserDetailResponse> getUser(@AuthUser Long memberId) {
        return ResponseEntity.ok(userService.findById(memberId));
    }

    @ApiOperation(value = "사용자 마이페이지 수정 API", notes = SwaggerNote.USER_UPDATE_DETAIL)
    @PatchMapping
    public ResponseEntity<String> updateUser(@AuthUser Long memberId, @RequestBody UserDetailRequest userDetailRequest) {
        userService.updateById(memberId, userDetailRequest);
        return ResponseEntity.ok("success");
    }
}