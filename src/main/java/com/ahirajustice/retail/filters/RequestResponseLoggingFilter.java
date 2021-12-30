package com.ahirajustice.retail.filters;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ahirajustice.retail.constants.SecurityConstants;

import org.apache.commons.io.IOUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class RequestResponseLoggingFilter extends GenericFilterBean{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Instant startTime = Instant.now();

        logRequest(request);
        chain.doFilter(request, response);

        double processTime = Duration.between(startTime, Instant.now()).getNano() / 1000000;
        logResponse(request, response, processTime);
    }

    private void logRequest(HttpServletRequest request) throws IOException {
        String msg = "";

        if (!excludeFromRequestResponseLogger(request.getRequestURI(), request.getMethod())) {
            msg = String.format("Running request '%s > %s'", request.getMethod(), request.getRequestURI());
            log.info(msg);

            if (HttpMethod.POST.matches(request.getMethod()) || HttpMethod.PUT.matches(request.getMethod())){
                String requestBody = IOUtils.toString(request.getReader());
                
                msg = String.format("Request body '%s'", requestBody);
                log.info(msg);
            }
        }
    }

    private void logResponse(HttpServletRequest request, HttpServletResponse response, double processTime) {
        String msg = "";

        if (!excludeFromRequestResponseLogger(request.getRequestURI(), request.getMethod())) {
            msg = String.format("Finished running request '%s > %s' in %f ms", request.getMethod(), request.getRequestURI(), processTime);
            log.info(msg);

            msg = String.format("Response Status Code: %d", response.getStatus());
            log.info(msg);
        }
    }

    private boolean excludeFromRequestResponseLogger(String requestURI, String requestMethod) {
        for (String url : SecurityConstants.EXCLUDE_FROM_REQUEST_RESPONSE_LOGGER) {
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
    
}
