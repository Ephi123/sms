package fileManagment.file.service.impl;

import fileManagment.file.Function.TriConsumer;
import fileManagment.file.domain.Token;
import fileManagment.file.domain.TokenData;
import fileManagment.file.enumeration.TokenType;
import fileManagment.file.security.JwtConfiguration;
import fileManagment.file.securityDto.User;
import fileManagment.file.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static fileManagment.file.constant.Constant.*;
import static fileManagment.file.enumeration.TokenType.ACCESS;
import static fileManagment.file.enumeration.TokenType.REFRESH;
import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static org.apache.tomcat.util.http.SameSiteCookies.NONE;
import static org.springframework.security.core.authority.AuthorityUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtImpl extends JwtConfiguration implements JwtService {
    private final UserServiceImpl userService;
    private final Supplier<SecretKey> key = () -> Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecret()));

  private final BiFunction<HttpServletRequest, String, Optional<String>> extractToken = (request, cookieName) ->
    Optional.of(stream(request.getCookies() == null ? new Cookie[]{new Cookie(EMPTY_VALUE,EMPTY_VALUE)}: request.getCookies()).
            filter(cookie -> Objects.equals(cookieName,cookie.getName())).map(Cookie::getValue).findAny()).orElse(empty());

    private final BiFunction<HttpServletRequest, String, Optional<Cookie>> extractCookies = (request, cookieName) ->
            Optional.of(stream(request.getCookies() == null ? new Cookie[]{new Cookie(EMPTY_VALUE,EMPTY_VALUE)}: request.getCookies()).
                    filter(cookie -> Objects.equals(cookieName,cookie.getName())).findAny()).orElse(empty());

    private final Supplier<JwtBuilder> builder = () ->
                                  Jwts.builder()
                                          .header().add(Map.of(TYPE,JWT_TYPE))
                                          .and()
                                          .audience().add(EPHI)
                                          .and()
                                          .id(UUID.randomUUID().toString())
                                          .issuedAt(Date.from(Instant.now()))
                                          .notBefore(new Date())
                                          .signWith(key.get(),Jwts.SIG.HS512);
    private final BiFunction<User,TokenType,String> buildToken = (user,type) ->
            Objects.equals(type, ACCESS) ? builder.get()
                    .subject(user.getUserId())
                    .claim(AUTHORITIES, user.getAuthorities())
                    .claim(ROLE,user.getRoles())
                    .expiration(Date.from(Instant.now().plusSeconds(getExpiration())))
                    .compact()
                        :
                    builder.get()
                    .subject(user.getUserId())
                    .expiration(Date.from(Instant.now().plusSeconds(getExpiration())))
                    .compact();

   private final TriConsumer<HttpServletResponse,User,TokenType> addCookie = (response, user, type) -> {
                      switch (type){
                          case ACCESS -> {
                              var accessToken = createToken(user, Token::getAccess);
                              var cookies = new Cookie(type.getValue(), accessToken);
                                cookies.setHttpOnly(true);
                                //cookies.setSecure(true);
                               cookies.setMaxAge(2*60);
                               cookies.setPath("/");
                               cookies.setAttribute("sameSite",NONE.name());
                               response.addCookie(cookies);
                               log.info("cookies add");
                          }

                          case REFRESH-> {
                              var refresh = createToken(user,Token::getRefresh);
                              var cookies = new Cookie(type.getValue(), refresh);
                              cookies.setHttpOnly(true);
                              //cookies.setSecure(true);
                              cookies.setMaxAge(2 * 60 * 60*60);//refresh cookies age 120 day
                              cookies.setPath("/");
                              cookies.setAttribute("sameSite",NONE.name());
                              response.addCookie(cookies);
                          }



                      }
   };

    private final Function<String,String> subject =  token -> getClaimsValue(token,Claims::getSubject);

  private <T> T getClaimsValue(String token, Function<Claims, T> claims){
      return claimsFunction.andThen(claims).apply(token);
  }


    private final Function<String, Claims>  claimsFunction = token ->
            Jwts.parser().verifyWith(key.get())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();



    public Function<String, List<GrantedAuthority>> authorities = token -> commaSeparatedStringToAuthorityList(new StringJoiner(AUTHORITY_DELIMITER)
            .add(claimsFunction.apply(token).get(AUTHORITIES,String.class)).add(ROLE_PREFIX + claimsFunction.apply(token).get(ROLE,String.class)).toString());
    @Override
    public String createToken(User user, Function<Token, String> tokenStringFunction) {
       var token = Token.builder().access(buildToken.apply(user,ACCESS)).refresh(buildToken.apply(user,REFRESH)).build();
        return tokenStringFunction.apply(token);
    }

    @Override
    public Optional<String> extractToken(HttpServletRequest request, String cookieName) {
        return extractToken.apply(request,cookieName);
    }
    @Override
    public Optional<Cookie> extractCookies(HttpServletRequest request, String cookieName) {
        return  extractCookies.apply(request,cookieName);
    }


    @Override
    public void addCookie(HttpServletResponse response, User user, TokenType type) {
        addCookie.accept(response,user,type);

    }

    @Override
    public void removeCookie(HttpServletResponse response, HttpServletRequest request, String cookieName) {
            var optionalCookie = extractCookies.apply(request,cookieName);

           if( optionalCookie.isPresent()){
              var cookie = optionalCookie.get();
                cookie.setMaxAge(0);
                response.addCookie(cookie);
           }

    }


    @Override
    public <T> T getTokenData(String token, Function<TokenData, T> tokenDataTFunction) {
        return tokenDataTFunction.apply(TokenData.builder()
                        .valid(Objects.equals(userService.getUserByUserId(subject.apply(token)).getUserId(),claimsFunction.apply(token).getSubject()))
                        .authorities(authorities.apply(token))
                        .claims(claimsFunction.apply(token))
                        .user(userService.getUserByUserId(subject.apply(token)))
                .build());
    }
}
