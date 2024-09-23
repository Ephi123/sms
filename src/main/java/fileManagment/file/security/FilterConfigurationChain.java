package fileManagment.file.security;

import fileManagment.file.service.UserService;
import fileManagment.file.service.impl.JwtImpl;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@RequiredArgsConstructor
public class FilterConfigurationChain {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,AuthenticationManager authenticationManager, UserService userService, JwtImpl jwt,JwtAuthorizationFilter jwtFilter) throws Exception{

        return httpSecurity.addFilterAt(authenticationFilter(authenticationManager,userService,jwt),UsernamePasswordAuthenticationFilter.class).addFilterBefore(jwtFilter,AuthenticationFilter.class).csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/user/login","/user/register","/verify/account").permitAll()
                        .anyRequest().authenticated()
                ).build();
    }


    @Bean
    public AuthenticationManager authenticationManager(ApiAuthenticationProvider authenticationProvider){
        return new ProviderManager(authenticationProvider);

    }

     @Bean
   public  AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager, UserService userService, JwtImpl jwt) throws Exception{
        return new AuthenticationFilter(authenticationManager,userService,jwt);

     }


//    @Bean
//    public UserDetailsService userDetailsService(){
//        var user1 = User.withUsername("ephrem")
//                .password("123")
//                .roles("USER").build();
//        var user2 = User.withUsername("sofanit")
//                .password("123")
//                .roles("USER").build();
//
//        return new InMemoryUserDetailsManager(List.of(user2,user1));
// }

}
