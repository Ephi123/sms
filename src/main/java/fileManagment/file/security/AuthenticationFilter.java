package fileManagment.file.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import fileManagment.file.enumeration.TokenType;
import fileManagment.file.requestDto.LoginDto;
import fileManagment.file.securityDto.User;
import fileManagment.file.service.JwtService;
import fileManagment.file.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import java.io.IOException;
import java.util.Map;
import static com.fasterxml.jackson.core.JsonParser.Feature.*;
import static fileManagment.file.constant.Constant.LOGIN_PATH;
import static fileManagment.file.domain.ApiAuthenticationToken.*;
import static fileManagment.file.enumeration.LoginType.*;
import static fileManagment.file.util.RequestUtil.getResponse;
import static fileManagment.file.util.RequestUtil.handleErrorResponse;
import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final JwtService jwtService;
    private final UserService userService;



        protected AuthenticationFilter(AuthenticationManager manager, UserService userService, JwtService jwtService){
        super(new AntPathRequestMatcher(LOGIN_PATH,POST.name()),manager);
        this.jwtService = jwtService;
        this.userService = userService;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
         try {
             var user = new ObjectMapper().configure(AUTO_CLOSE_SOURCE,true).readValue(request.getInputStream(), LoginDto.class);
             userService.updateLoginAttempt(user.getEmail(), LOGIN_ATTEMPT);
             var authentication = Unauthenticated(user.getPassword(), user.getEmail());
             return getAuthenticationManager().authenticate(authentication);
         } catch (Exception e) {
             log.error(e.getMessage());
             handleErrorResponse(request, response, e, UNAUTHORIZED);
          return null;
         }


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
            var user = (User)authResult.getPrincipal();
              userService.updateLoginAttempt(user.getEmail(),LOGIN_SUCCESS);
              var httpResponse = user.isMfa() ? sendQrCode(request,user) : sendResponse(request,response,user);
              response.setContentType(APPLICATION_JSON_VALUE);
              response.setStatus(OK.value());
              var out = response.getOutputStream();
              var mapper = new ObjectMapper();
              mapper.writeValue(out,httpResponse);
              out.flush();



    }

    private Object sendResponse(HttpServletRequest request, HttpServletResponse response, User user) {
        jwtService.addCookie(response,user, TokenType.ACCESS);
        jwtService.addCookie(response,user, TokenType.REFRESH);
        return getResponse(request, Map.of("user",user),"Login Success", OK);

    }

    private Object sendQrCode(HttpServletRequest request, User user) {
        return getResponse(request,Map.of("user",user),"please enter QR code", OK);
    }


}
