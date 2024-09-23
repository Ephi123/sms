package fileManagment.file.domain;

import fileManagment.file.apiException.ApiException;
import fileManagment.file.securityDto.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;


public class ApiAuthenticationToken extends AbstractAuthenticationToken {
    private static final String PASSWORD_PROTECTED = "[PASSWORD IS PROTECTED]";
    private static final String EMAIL_PROTECTED = "[EMAIL IS PROTECTED]";
    private User user;
    private String password;
    private String email;
    private boolean authenticated;

  private ApiAuthenticationToken(User user ,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user =  user;
        this.password = PASSWORD_PROTECTED;
        this.email = EMAIL_PROTECTED;
        this.authenticated = true;
    }



    private ApiAuthenticationToken(String password, String email) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.password = password;
        this.email = email;
        this.authenticated = false;
    }

    public static ApiAuthenticationToken authenticated(User user , Collection<? extends GrantedAuthority> authorities){
      return new ApiAuthenticationToken(user,authorities);
    }
    public static ApiAuthenticationToken Unauthenticated(String password, String email){
        return new ApiAuthenticationToken(password,email);
    }

    public String getPassword(){
      return password;
    }

    public String getEmail(){
      return email;
    }


    @Override
    public Object getCredentials() {
        return PASSWORD_PROTECTED;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new ApiException("you can't set authentication");
    }
}
