package com.codevibe.web.dripworld.service;

import com.codevibe.web.dripworld.constants.AppConsts;
import com.codevibe.web.dripworld.constants.Role;
import com.codevibe.web.dripworld.constants.UserRegValidationsOuts;
import com.codevibe.web.dripworld.constants.UserStatus;
import com.codevibe.web.dripworld.dao.TokenDAO;
import com.codevibe.web.dripworld.dao.UserDAO;
import com.codevibe.web.dripworld.entities.UsersEntity;
import com.codevibe.web.dripworld.handler.types.AuthorizationFailedException;
import com.codevibe.web.dripworld.handler.types.ProcessFailedException;
import com.codevibe.web.dripworld.handler.types.UserNotFoundException;
import com.codevibe.web.dripworld.handler.types.ValidationFailedException;
import com.codevibe.web.dripworld.mail.types.VerificationMail;
import com.codevibe.web.dripworld.provider.MailServiceProvider;
import com.codevibe.web.dripworld.repositories.UserRepository;
import com.codevibe.web.dripworld.util.Encryption;
import com.codevibe.web.dripworld.util.JwtTokenUtil;
import com.codevibe.web.dripworld.util.ResponseUtil;
import io.fusionauth.jwt.JWTExpiredException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.*;

public class AuthService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private Encryption encryption;

    @Inject
    private JwtTokenUtil jwtTokenUtil;

    public Response signIn(UserDAO userDAO, HttpServletRequest request) {
        if (userDAO == null || userDAO.getEmail() == null || userDAO.getEmail().isBlank() ||
                userDAO.getPassword() == null || userDAO.getPassword().isEmpty()) {

//            throw new ValidationFailedException();

            return ResponseUtil.generate(Response.Status.BAD_REQUEST,null, "Invalid Credentials");

        }else if (!userDAO.getEmail().matches(AppConsts.EMAIL_VALIDATION_REGEX)){

            return ResponseUtil.generate(Response.Status.OK,null, "Invalid Email");

        }else{
            //            throw new ValidationFailedException("Invalid Email Address");

            final UsersEntity user = userRepository.findByEmailAndPassword(userDAO.getEmail(), encryption.encrypt(userDAO.getPassword()))
                    .orElseThrow(() -> new ValidationFailedException("Invalid Credentials"));
            if (user.getStatus().equals(UserStatus.active)){
                //            throw new ProcessFailedException("user inactive");
                if (user.getEmailVerifiedAt() != null){
                    //            throw new ProcessFailedException("Account is not verified yet");
                    final UserDAO userDetails = new UserDAO();
                    userDetails.setId(user.getId());
                    userDetails.setFirstName(user.getFirstName());
                    userDetails.setLastName(user.getLastName());
                    userDetails.setEmail(user.getEmail());
                    userDetails.setContact(user.getContact());
                    userDetails.setStatus(UserStatus.active);

                    final HttpSession session = request.getSession();
                    session.setAttribute("user", user.getId());

                    final Map<String, Object> map = new HashMap<>();
                    map.put("user", userDetails);
                    map.put("tokens", generateTokens(user, true));
                    return ResponseUtil.generate(Response.Status.OK, map, "success");
                }else{
                    return ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "User Not Verified");
                }
            }else{
                return ResponseUtil.generate(Response.Status.BAD_REQUEST,null, "User Inactive");
            }
        }
    }

    private TokenDAO generateTokens(UsersEntity user, boolean issueRefreshToken) {
        if (user == null)
            throw new UserNotFoundException();

        return new TokenDAO(
                jwtTokenUtil.generateAccessToken(user),
                issueRefreshToken ? jwtTokenUtil.generateRefreshToken(user) : null
        );
    }

    public Response signUp(UserDAO userDAO) {

        UserRegValidationsOuts outs = signUpValidator(userDAO);

        if (outs.equals(UserRegValidationsOuts.PASS)) {
            if (userRepository.findByEmailOrContact(userDAO.getEmail(), userDAO.getContact()).isPresent())
                throw new ValidationFailedException("Email OR Contact Already in use");

            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setFirstName(userDAO.getFirstName());
            usersEntity.setLastName(userDAO.getLastName());
            usersEntity.setEmail(userDAO.getEmail());
            usersEntity.setContact(userDAO.getContact());
            usersEntity.setPassword(encryption.encrypt(userDAO.getPassword()));
            usersEntity.setStatus(UserStatus.inactive);
            usersEntity.setRole(Role.customer);

            final String vCode = UUID.randomUUID().toString();
            usersEntity.setVerificationCode(vCode);

            userRepository.save(usersEntity);

            final VerificationMail mail = new VerificationMail(usersEntity.getEmail(), usersEntity.getFirstName() + " " + usersEntity.getLastName(), vCode);
            MailServiceProvider.getInstance().sendMail(mail);

            return ResponseUtil.generate(Response.Status.OK, null, "Registration Success");

        }else{

            if(outs.equals(UserRegValidationsOuts.INVALID_DATA)){

                return ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "Invalid data");

            }else if(outs.equals(UserRegValidationsOuts.INVALID_EMAIL)){

                return ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "Invalid Email");

            }else if(outs.equals(UserRegValidationsOuts.INVALID_CONTACT)){

                return ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "Invalid Contacts");

            }else if(outs.equals(UserRegValidationsOuts.INVALID_PW)){

                return ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "Invalid Password Length");

            }else{
                return ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "Invalid data");
            }



        }
    }

    public Response refreshToken(TokenDAO tokenDAO) {
        if (tokenDAO == null || tokenDAO.getRefreshToken() == null || tokenDAO.getRefreshToken().isBlank()){
            return ResponseUtil.generate(Response.Status.BAD_REQUEST,null,"Invalid token data");
        }else if (jwtTokenUtil.isTokenExpired(tokenDAO.getRefreshToken()))
            throw new JWTExpiredException();
        if (userRepository.findByEmail(jwtTokenUtil.getUsernameFromToken(tokenDAO.getRefreshToken())).isPresent()) {
            final UsersEntity user = userRepository
                    .findByEmail(jwtTokenUtil.getUsernameFromToken(tokenDAO.getRefreshToken())).get();
            return ResponseUtil.generate(
                    Response.Status.OK,
                    generateTokens(user, false),
                    "success"
            );
        } else {
            return ResponseUtil.generate(Response.Status.UNAUTHORIZED, null, "Invalid token");
        }


    }

    public Response verify(String code) {
        final Optional<UsersEntity> optional = userRepository.findByVerificationCode(code);
        if (optional.isPresent()) {
            System.out.println("ToKenFound");
            final UsersEntity user = optional.get();
            user.setEmailVerifiedAt(new Date());
            user.setStatus(UserStatus.active);
            userRepository.update(user);
            return Response.status(Response.Status.FOUND).location(URI.create("login")).build();
        } else
            System.out.println("Token not found");
        return Response.seeOther(URI.create("")).build();
    }

    private UserRegValidationsOuts signUpValidator(UserDAO userDAO) {
        if (userDAO == null || userDAO.getFirstName() == null || userDAO.getFirstName().isBlank() || userDAO.getLastName() == null || userDAO.getLastName().isBlank() ||
                userDAO.getEmail() == null || userDAO.getEmail().isBlank() || userDAO.getPassword() == null || userDAO.getPassword().isEmpty() ||
                userDAO.getContact() == null || userDAO.getContact().isEmpty()) {
            System.out.println("Invalid Data");
//            ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "Invalid Data");
            return UserRegValidationsOuts.INVALID_DATA;
        } else if (!userDAO.getEmail().matches(AppConsts.EMAIL_VALIDATION_REGEX)) {
//            ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "Invalid Email");
            return UserRegValidationsOuts.INVALID_EMAIL;
        }
        if (!userDAO.getContact().matches(AppConsts.MOBILE_VALIDATION_REGEX)) {
//            ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "Invalid Mobile");
            return UserRegValidationsOuts.INVALID_CONTACT;
        }
        if (userDAO.getPassword().length() < 8) {
//            ResponseUtil.generate(Response.Status.BAD_REQUEST, null, "Password should contain 8 characters");
            return UserRegValidationsOuts.INVALID_PW;
        }

        return UserRegValidationsOuts.PASS;

    }

}
