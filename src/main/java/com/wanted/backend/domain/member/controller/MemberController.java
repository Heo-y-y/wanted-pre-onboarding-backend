package com.wanted.backend.domain.member.controller;

import com.wanted.backend.domain.member.dto.AccessTokenRequest;
import com.wanted.backend.domain.member.dto.AccessTokenResponse;
import com.wanted.backend.domain.member.dto.LoginDto;
import com.wanted.backend.domain.member.dto.SignUpDto;
import com.wanted.backend.domain.member.entity.Member;
import com.wanted.backend.global.exception.ApiResponse;
import com.wanted.backend.domain.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Api(tags = "UserController")
@RequestMapping("/api")
@RestController
public class MemberController {
    private final MemberService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<Member>> signUp(@Validated @RequestBody SignUpDto signUp) {
        Member user = memberService.registerUser(signUp.getEmail(), signUp.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createId(user));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> login(@Validated @RequestBody LoginDto loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.login(memberService.signIn(loginDto).getBody())) ;
    }

    @ApiOperation(value = "access token 재발급")
    @PostMapping("/re-issue")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> reissueAccessToken(@RequestBody AccessTokenRequest accessTokenRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.reissueToken(memberService.getAccessToken(accessTokenRequest).getBody()));
    }

    @ApiOperation(value = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(@AuthenticationPrincipal UserDetails userDetails) {
        memberService.removeToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.logout());
    }
}
