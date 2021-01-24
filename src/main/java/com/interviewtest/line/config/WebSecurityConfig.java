package com.interviewtest.line.config;


import com.interviewtest.line.security.JwtAuthEntryPoint;
import com.interviewtest.line.security.JwtAuthTokenFilter;
import com.interviewtest.line.security.SystemUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private SystemUserDetailsService systemUserDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }
        
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(systemUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers("/api/auth/**",
                        "/v2/api-docs", 
                        "/configuration/**", 
//                        "/swagger*/**",
                        "/webjars/**"
                ).permitAll()
//                .antMatchers("/api/utility/**").hasAnyAuthority(new String[]{"ADMIN_PRIVILEGE", "SUB_ADMIN_PRIVILEGE"})
//                .antMatchers("/api/outlets/**").hasAnyAuthority(new String[]{"ADMIN_PRIVILEGE", "SUB_ADMIN_PRIVILEGE"})
//                .antMatchers("/api/payment/remita/**").hasAnyAuthority(new String[]{"ADMIN_PRIVILEGE", "SUB_ADMIN_PRIVILEGE"})

                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().logoutUrl("/api/auth/logout").invalidateHttpSession(true);

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", 
                "/static/**",
                "/css/**", 
                "/js/**", 
                "/images/**",
                "/console/**",
                "/v2/api-docs",
                "/configuration/ui",
//                "/swagger-resources/**",
                "/configuration/security",
//                "/swagger-ui.html",
                "/webjars/**",
                "/api/utility/**",
                "/api/outlets/**",
                "/api/wallet-master/**"

        );
    }

}