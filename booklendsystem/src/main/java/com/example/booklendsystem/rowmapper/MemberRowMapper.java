package com.example.booklendsystem.rowmapper;

import com.example.booklendsystem.model.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();
        member.setMember_id(rs.getInt("member_id"));
        member.setPhone_number(rs.getString("phone_number"));
        member.setMember_name(rs.getString("member_name"));
        member.setPassword(rs.getString("password"));
        member.setRegistration_time(rs.getTime("registration_time").toString());
        if(rs.getTime("last_login_time") != null)
            member.setLast_login_time(rs.getTime("last_login_time").toString());
        else
            member.setLast_login_time("");
        return member;
    }
}
