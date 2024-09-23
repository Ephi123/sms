package fileManagment.file.security;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.domain.ApiAuthenticationToken;
import fileManagment.file.domain.UserPrincipal;
import fileManagment.file.service.UserService;
import fileManagment.file.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;

import static fileManagment.file.constant.Constant.EXPIRE_DAYS;

@Component
@RequiredArgsConstructor
public class ApiAuthenticationProvider implements AuthenticationProvider {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        var user = (UsernamePasswordAuthenticationToken) authentication;
//          var  userDb = userDetailsService.loadUserByUsername((String)user.getPrincipal());
//          var password = (String)user.getCredentials();
//          if(password.equals(userDb.getPassword())){
//              return UsernamePasswordAuthenticationToken.authenticated(userDb.getUsername(),"[CREDENTIAL IS PROTECTED]", userDb.getAuthorities());
//          }
//        throw new BadCredentialsException("login is failed");//previous implementation

        var apiAuthenticationToken = authenticationFunction.apply(authentication);
        var user = userService.getUserByUserEmail(apiAuthenticationToken.getEmail());
        if (user != null) {
            var userCredential = userService.getUserCredentialById(user.getId());
            var userPrincipal = new UserPrincipal(user, userCredential);
             if(validAccount.apply(userPrincipal) != null){
                 RequestUtil.handleErrorResponse(request, response, validAccount.apply(userPrincipal),HttpStatus.UNAUTHORIZED);
             }

            if (encoder.matches(apiAuthenticationToken.getPassword(), userCredential.getPassword())) {
                return ApiAuthenticationToken.authenticated(user, userPrincipal.getAuthorities());
            } else {
                RequestUtil.handleErrorResponse(request, response, new BadCredentialsException("Email and/or password incorrect. please try again"),HttpStatus.UNAUTHORIZED);
                throw new BadCredentialsException("Email and/or password incorrect. please try again");
            }
        } else
            RequestUtil.handleErrorResponse(request, response, new ApiException("unable to authenticate"), HttpStatus.UNAUTHORIZED);
        throw new ApiException("unable to authenticate");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiAuthenticationToken.class.isAssignableFrom(authentication);
    }


    private final Function<Authentication, ApiAuthenticationToken> authenticationFunction = authentication1 -> (ApiAuthenticationToken) authentication1;

    private final Function<UserPrincipal,Exception>  validAccount = userPrincipal -> {
      if(!userPrincipal.isAccountNonExpired()){
             return new AccountExpiredException("your account is expired. please contact administer");

      }

      if(!userPrincipal.isAccountNonLocked()){
          return  new LockedException("your account is currently locked");
      }
      if(!userPrincipal.isCredentialsNonExpired()){
          return  new CredentialsExpiredException("your password has expire. please reset your password");
      }
      if(!userPrincipal.isEnabled()) return  new DisabledException("your account is currently disable");

      return null;
    };

}
