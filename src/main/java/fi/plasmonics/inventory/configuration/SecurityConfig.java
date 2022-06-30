package fi.plasmonics.inventory.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;
    //JwtTokenUtil jwtTokenUtil;

    //private final JwtTokenFilter jwtTokenFilter;

    //for Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

    }


    //for Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").permitAll();
//                .antMatchers("/api/login").hasAnyRole("ADMIN","USER")
//                .antMatchers("/api/login-admin").hasRole("ADMIN")
//                .antMatchers("/api/login-user").hasRole("USER")
//                .antMatchers("/api/register").permitAll()
//                .antMatchers("/api/items").permitAll()
//                .antMatchers("/h2-console/**").permitAll()
//                .and().formLogin();
//        http
//                .headers().frameOptions().sameOrigin();
//        http
//                .csrf().disable();
//        http
//                .headers().frameOptions().disable();

    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
