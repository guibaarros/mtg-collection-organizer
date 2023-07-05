package com.mtgcollectionorganizer.api.security;

import com.mtgcollectionorganizer.api.user.entity.UserPasswordEntity;
import com.mtgcollectionorganizer.api.user.exception.UserNotAuthorizedException;
import com.mtgcollectionorganizer.api.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public String login(final String userName, final String userPassword) {
        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userName, userPassword);

        final UserPasswordEntity userPasswordEntity = userService.getUserPasswordByUserName(userName);

        if (passwordEncoder.matches(userPassword, userPasswordEntity.getPassword())) {
            //TODO verificar se já tem sessáo logada, se sim, deslogar e abrir a nova
            final Authentication authenticate = authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);
            final UserPasswordEntity authenticatedUser = (UserPasswordEntity) authenticate.getPrincipal();

            log.info("user={}, succesfully logged in", userName);
            return tokenService.generateToken(authenticatedUser);
        }
        throw new UserNotAuthorizedException(userName);
    }

    public String getUsernameByToken(final String token) {
        return tokenService.getSubject(token.replace("Bearer ", ""));
    }
}
