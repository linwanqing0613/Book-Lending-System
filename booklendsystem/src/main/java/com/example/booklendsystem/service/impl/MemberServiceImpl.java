package com.example.booklendsystem.service.impl;

import com.example.booklendsystem.config.JWTProvider;
import com.example.booklendsystem.config.TokenUtility;
import com.example.booklendsystem.dao.MemberDao;
import com.example.booklendsystem.dto.MemberRequest;
import com.example.booklendsystem.dto.TokenResponse;
import com.example.booklendsystem.model.Member;
import com.example.booklendsystem.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class MemberServiceImpl implements MemberService {
    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenUtility tokenUtility;
    @Override
    @Transactional
    public Integer register(MemberRequest memberRequest) {
        if(memberDao.getMemberByPhone(memberRequest.getPhone_number()) != null) {
            logger.error("Registering member's id or Email is Exist!");
            throw new IllegalArgumentException("Registering member's id or Email is Exist");
        }
        logger.info("Registering member: " + memberRequest.getPhone_number());
        memberRequest.setPassword(passwordEncoder.encode(memberRequest.getPassword()));
        return memberDao.register(memberRequest);
    }

    @Override
    public void updateMember(MemberRequest memberRequest) {
        memberDao.updateMember(memberRequest);
    }

    @Transactional
    public TokenResponse login(String phone_number) throws Exception {
        String token = tokenUtility.generateToken(phone_number);
        String member_name = getMemberByPhone(phone_number).getMember_name();
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        tokenResponse.setMember_name(member_name);

        return tokenResponse;
    }
    @Override
    public Member getMemberByPhone(String phone_number) {
        return memberDao.getMemberByPhone(phone_number);
    }

    @Override
    public Member getMemberById(Integer member_id) {
        return memberDao.getMemberById(member_id);
    }
    public Member getMemberByJWT(String jwt) throws Exception{
        jwt = jwt.substring("Bearer ".length());
        String phone_number = tokenUtility.extractPhoneNumber(jwt);
        Member member = memberDao.getMemberByPhone(phone_number);
        if(member == null){
            throw new Exception("MemberService Error: Invalid JWT");
        }
        return member;
    }
}
