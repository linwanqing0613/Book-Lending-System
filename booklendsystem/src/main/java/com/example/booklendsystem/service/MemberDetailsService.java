package com.example.booklendsystem.service;

import com.example.booklendsystem.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.logging.Logger;

@Component
public class MemberDetailsService implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(MemberDetailsService.class.getName());

    @Autowired
    private MemberService memberService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phone_number) throws UsernameNotFoundException {
        Member member = memberService.getMemberByPhone(phone_number);

        if(member == null){
            throw new UsernameNotFoundException("Username not Found!");
        }
        logger.info("使用者 ID: "+member.getMember_id());
        logger.info("手機: "+member.getPhone_number());
        logger.info("使用者名稱: "+member.getMember_name());
        logger.info("註冊日期時間: "+member.getRegistration_time());
        return new User(member.getPhone_number(), member.getPassword(), new ArrayList<>());
    }
}
