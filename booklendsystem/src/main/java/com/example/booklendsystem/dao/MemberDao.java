package com.example.booklendsystem.dao;

import com.example.booklendsystem.dto.MemberRequest;
import com.example.booklendsystem.model.Member;

public interface MemberDao {
    public Integer register(MemberRequest memberRequest);
    public Member getMemberByPhone(String phone_number);
    public Member getMemberById(Integer member_id);
    public void updateMember(MemberRequest memberRequest);
    public void login(String phone_number);
}
