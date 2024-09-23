package fileManagment.file.service;

import fileManagment.file.domain.Token;
import fileManagment.file.domain.TokenData;
import fileManagment.file.enumeration.TokenType;
import fileManagment.file.securityDto.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.function.Function;

public interface JwtService {
    String createToken(User user, Function<Token, String> tokenStringFunction);
    Optional<String> extractToken(HttpServletRequest request,String tokenType);
    void addCookie(HttpServletResponse response,User user, TokenType type);
    void removeCookie(HttpServletResponse response,HttpServletRequest request, String cookieName);
    <T> T getTokenData(String token, Function<TokenData,T> tokenDataTFunction);
    Optional<Cookie> extractCookies(HttpServletRequest request, String cookieName);

}
