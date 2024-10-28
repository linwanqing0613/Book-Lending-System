package com.example.booklendsystem.dao.impl;

import com.example.booklendsystem.dao.MemberDao;
import com.example.booklendsystem.dto.MemberRequest;
import com.example.booklendsystem.model.Member;
import com.example.booklendsystem.rowmapper.MemberRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDaoImpl implements MemberDao {
    private static final Logger logger = LoggerFactory.getLogger(MemberDaoImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Integer register(MemberRequest memberRequest) {
        Map<String, Object> map = new HashMap<>();
        String sql = "call REGISTER( :phone_number, :password, :member_name, @member_id);";
        map.put("phone_number", memberRequest.getPhone_number());
        map.put("password", memberRequest.getPassword());
        map.put("member_name", memberRequest.getMember_name());
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map));

        sql = "SELECT @member_id";
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    @Override
    public Member getMemberByPhone(String phone_number) {
        Map<String, Object> map = new HashMap<>();
        String sql = "call GET_MEMBER_BY_PHONE( :phone_number);";
        map.put("phone_number",phone_number);
        List<Member> list = namedParameterJdbcTemplate.query(sql, map,new MemberRowMapper());
        return list.isEmpty() ? null: list.get(0);
    }

    @Override
    public Member getMemberById(Integer member_id) {
        Map<String, Object> map = new HashMap<>();
        String sql = "call GET_MEMBER_BY_ID( :member_id);";
        map.put("member_id",member_id);
        List<Member> list = namedParameterJdbcTemplate.query(sql, map,new MemberRowMapper());
        return list.isEmpty() ? null: list.get(0);
    }

    @Override
    public void updateMember(MemberRequest memberRequest) {
        Map<String, Object> map = new HashMap<>();
        String sql = "call UPDATE_MEMBER( :phone_number, :password , :member_name);";
        logger.info(memberRequest.getMember_name());
        logger.info(memberRequest.getPassword());
        map.put("phone_number",memberRequest.getPhone_number());
        map.put("password",memberRequest.getPassword());
        map.put("member_name", memberRequest.getMember_name());

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map));
    }

    @Override
    public void login(String phone_number) {
        Map<String, Object> map = new HashMap<>();
        String sql = "call UPDATE_LOGIN_TIME( :phone_number);";
        map.put("phone_number",phone_number);
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map));
    }
}
