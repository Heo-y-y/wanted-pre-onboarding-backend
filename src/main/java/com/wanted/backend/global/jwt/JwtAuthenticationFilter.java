package com.wanted.backend.global.jwt;

import com.wanted.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = tokenProvider.resolveToken(request);
        try {
            if (accessToken != null) {
                AuthToken authToken = tokenProvider.convertAuthToken(accessToken);

                if (authToken.validateToken()) {
                    Authentication authentication = tokenProvider.getAuthentication(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (CustomException e) {
            SecurityContextHolder.clearContext();
            response.sendError(e.getCustomErrorCode().getStatus().value(), e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
}
