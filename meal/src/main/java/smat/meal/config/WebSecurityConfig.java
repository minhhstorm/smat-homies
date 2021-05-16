package smat.meal.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import smat.meal.security.JwtFilter;
import smat.meal.service.UserDetailsServiceImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public JwtFilter authenticationJwtTokenFilter() {
        return new JwtFilter();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedHeader("Authorization,Authorization");
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("Access-Control-Expose-Headers,Authorization");
        config.addAllowedHeader("Access-Control-Expose-Headers,Content-Length");
        config.addAllowedHeader("Access-Control-Expose-Headers,X-Kuma-Revision");
        config.addAllowedHeader("Access-Control-Allow-Headers,X-Custom-Header");
        config.addAllowedHeader("Access-Control-Allow-Headers,Authorization");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity httpS) throws Exception {
        httpS.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/auth/**")
                .permitAll()
                .antMatchers("/smat/**")
                .permitAll()
                .antMatchers("/admin/**")
                .permitAll()
                .anyRequest().authenticated();
        httpS.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
