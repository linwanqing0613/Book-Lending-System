package com.example.booklendsystem.service;

import com.example.booklendsystem.dto.MemberRequest;
import com.example.booklendsystem.dto.TokenResponse;
import com.example.booklendsystem.model.Member;

public interface MemberService {
    public TokenResponse login(String phone_number) throws Exception ;
    public Integer register(MemberRequest memberRequest);
    public void updateMember(String jwt, MemberRequest memberRequest);
    public Member getMemberByPhone(String phone_number);

    public Member getMemberById(Integer member_id);

    public Member getMemberByJWT(String jwt) throws Exception;


}
