package com.codevibe.web.dripworld.service;

import com.codevibe.web.dripworld.dao.UserDetails;
import com.codevibe.web.dripworld.entities.UsersEntity;
import com.codevibe.web.dripworld.handler.types.AuthorizationFailedException;
import com.codevibe.web.dripworld.handler.types.UserNotFoundException;
import com.codevibe.web.dripworld.repositories.UserRepository;
import com.codevibe.web.dripworld.util.Encryption;
import com.codevibe.web.dripworld.util.JwtTokenUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private Encryption encryption;

    @Inject
    private JwtTokenUtil jwtTokenUtil;

    public UsersEntity getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public UsersEntity getById(Long id) {return userRepository.findUserById(id).orElse(null);}

    public void save(UsersEntity user){userRepository.save(user);}

    public UsersEntity extractUserFromRequest(HttpServletRequest request){
        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null)
            throw new AuthorizationFailedException();
        if(!authHeader.startsWith("Bearer "))
            throw new AuthorizationFailedException("Invalid token");

        final String token = authHeader.split(" ")[1];
        System.out.println("Token TEST-------------"+token);
        return userRepository
                .findByEmail(jwtTokenUtil.getUsernameFromToken(token))
                .orElseThrow(UserNotFoundException::new);
    }

}
