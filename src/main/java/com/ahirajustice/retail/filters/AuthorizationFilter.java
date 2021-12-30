package com.ahirajustice.retail.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ahirajustice.retail.config.SpringApplicationContext;
import com.ahirajustice.retail.constants.SecurityConstants;
import com.ahirajustice.retail.dtos.auth.AuthToken;
import com.ahirajustice.retail.exceptions.UnauthorizedException;
import com.ahirajustice.retail.repositories.IUserRepository;
import com.ahirajustice.retail.services.auth.IAuthService;
import com.ahirajustice.retail.viewmodels.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class AuthorizationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        IAuthService authService = (IAuthService) SpringApplicationContext.getBean("authService");

        if (!excludeFromAuth(request.getRequestURI(), request.getMethod())) {
            String header = request.getHeader(SecurityConstants.HEADER_STRING);

            if (StringUtils.isBlank(header)) {
                writeErrorToResponse("Missing authorization header", response);
                return;
            }

            String scheme = header.split(" ")[0];
            String token = header.split(" ")[1];

            if (StringUtils.isBlank(scheme) || StringUtils.isBlank(token)) {
                writeErrorToResponse("Malformed authorization header", response);
                return;
            }

            if (!StringUtils.lowerCase(scheme).equals("bearer")) {
                writeErrorToResponse("Invalid authentication scheme", response);
                return;
            }

            AuthToken authToken = authService.decodeJwt(token);

            if (!userExists(authToken) || isExpired(authToken)) {
                writeErrorToResponse("Invalid or expired token", response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean excludeFromAuth(String requestURI, String requestMethod) {
        for (String url : SecurityConstants.EXCLUDE_FROM_AUTH_URLS) {
            String excludeURI = url.split(", ")[0];
            String excludeMethod = url.split(", ")[1];

            if (excludeURI.equals(requestURI) && excludeMethod.equals(requestMethod)){
                return true;
            }

            if (excludeURI.endsWith("/**") && requestURI.startsWith(excludeURI.replace("/**", "")) && excludeMethod.equals(requestMethod)){
                return true;
            }
        }

        return false;
    }

    private boolean userExists(AuthToken token) {
        IUserRepository userRepository = (IUserRepository) SpringApplicationContext.getBean("IUserRepository");
        return userRepository.findByEmail(token.getUsername()).isPresent();
    }

    private boolean isExpired(AuthToken token) {
        return Instant.now().isAfter(token.getExpiry().toInstant());
    }

    private void writeErrorToResponse(String message, HttpServletResponse response) throws IOException {
        UnauthorizedException ex = new UnauthorizedException(message);
        ErrorResponse errorResponse = ex.toErrorResponse();

        ObjectMapper mapper = new ObjectMapper();
        String errorResponseBody = mapper.writeValueAsString(errorResponse);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ex.getStatusCode());
        writer.print(errorResponseBody);
        writer.flush();
    }

}
