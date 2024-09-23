package fileManagment.file.security;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.ApiAuthenticationToken;
import fileManagment.file.domain.Token;
import fileManagment.file.domain.TokenData;
import fileManagment.file.enumeration.TokenType;
import fileManagment.file.service.JwtService;
import fileManagment.file.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extract the token from cookies or Authorization header
        Optional<Cookie> optionalCookie = jwtService.extractCookies(request,TokenType.ACCESS.getValue());
        if(optionalCookie.isPresent()){
        Optional<String> accessTokenOptional = jwtService.extractToken(request, TokenType.ACCESS.getValue());

        if (accessTokenOptional.isPresent()) {
            String accessToken = accessTokenOptional.get();
            TokenData tokenData = jwtService.getTokenData(accessToken,tokenData1 -> tokenData1);

            try {
                // Check if access token is valid
                if (tokenData.isValid()) {
                    setAuthentication(accessToken, request);
                }
            } catch (Exception e) {
                // Access token is invalid or expired
                throw new ApiException("token is not valid");

            }
        }
        }

        else  {
            Optional<Cookie> optionalCookieRefresh = jwtService.extractCookies(request,TokenType.REFRESH.getValue());
            if(optionalCookieRefresh.isPresent())
                handleTokenRefresh(request, response);


        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token, HttpServletRequest request) {
        TokenData tokenData = jwtService.getTokenData(token,tokenData1 -> tokenData1);
        if (tokenData.isValid()) {
                   ApiAuthenticationToken authenticationToken= ApiAuthenticationToken.authenticated(tokenData.getUser(),tokenData.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private void handleTokenRefresh(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> refreshTokenOptional = jwtService.extractToken(request, TokenType.REFRESH.getValue());

        if (refreshTokenOptional.isPresent()) {
            String refreshToken = refreshTokenOptional.get();
                var tokenData = jwtService.getTokenData(refreshToken, tokenDataObj -> tokenDataObj);

            // Validate the refresh token
            if (tokenData.isValid()) {
                jwtService.addCookie(response, tokenData.getUser(), TokenType.ACCESS);
                var newAccessToken = jwtService.createToken(tokenData.getUser(), Token::getAccess);
                setAuthentication(newAccessToken, request);
            } else {
                log.error("Refresh token is invalid");
            }
        } else {
            log.error("Refresh token is missing");
        }
    }


}