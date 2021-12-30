package com.ahirajustice.retail.services.user;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.ahirajustice.retail.config.AppConfig;
import com.ahirajustice.retail.constants.SecurityConstants;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.repositories.IUserRepository;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrentUserService implements ICurrentUserService {

    private final AppConfig appConfig;
    private final HttpServletRequest request;
    private final IUserRepository userRepository;

    @Override
    public Optional<User> getCurrentUser() {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        return userRepository.findByEmail(getUsernameFromToken(header));
    }

    private String getUsernameFromToken(String header) {
        if (header == null) {
            return null;
        }

        String token = header.split(" ")[1];

        return Jwts.parser().setSigningKey(appConfig.SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

}
