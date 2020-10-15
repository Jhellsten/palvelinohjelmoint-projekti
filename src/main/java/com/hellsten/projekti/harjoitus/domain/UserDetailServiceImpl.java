package com.hellsten.projekti.harjoitus.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepo repository;
    @Autowired
    public UserDetailServiceImpl(UserRepo userRepo) {
        this.repository = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws
    UsernameNotFoundException {
        User curruser = repository.findByUsername(username);
        UserDetails user = new org.springframework.security.core.userdetails.User(username,
        curruser.getPasswordHash(),
        AuthorityUtils.createAuthorityList(curruser.getRole()));
        return user;
    }
}
