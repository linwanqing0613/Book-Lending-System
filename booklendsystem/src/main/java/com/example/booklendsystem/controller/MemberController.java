package com.example.booklendsystem.controller;

import com.example.booklendsystem.dto.LoginRequest;
import com.example.booklendsystem.dto.MemberRequest;
import com.example.booklendsystem.dto.TokenResponse;
import com.example.booklendsystem.model.Member;
import com.example.booklendsystem.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @GetMapping("/info")
    public ResponseEntity<Member> getUserInfo(@RequestHeader("Authorization") String jwt) throws Exception {
        Member member = memberService.getMemberByJWT(jwt);
        return ResponseEntity.ok(member);
    }
    @PostMapping("/register")
    public ResponseEntity<Member> register(@RequestBody MemberRequest memberRequest) {
        Integer member_id = memberService.register(memberRequest);
        Member member = memberService.getMemberById(member_id);
        return member != null? ResponseEntity.ok(member): ResponseEntity.notFound().build();
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
        TokenResponse token = memberService.login(loginRequest.getPhone_number());
        return ResponseEntity.ok(token);
    }
    @PostMapping("/update")
    public ResponseEntity<Member> update(@RequestBody MemberRequest memberRequest) throws Exception {
        memberService.updateMember(memberRequest);
        Member member = memberService.getMemberByPhone(memberRequest.getPhone_number());
        return ResponseEntity.ok(member);
    }
}