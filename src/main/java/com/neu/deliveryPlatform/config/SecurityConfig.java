package com.neu.deliveryPlatform.config;

import com.neu.deliveryPlatform.config.permission.handler.AuthenticationEntryPointImpl;
import com.neu.deliveryPlatform.filter.JwtAuthenticationTokenFilter;
import com.neu.deliveryPlatform.properties.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author asiawu
 * @Date 2023-04-08 0:53
 * @Description: springSecurity配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    AccessDeniedHandler accessDeniedHandler;

    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    ConfigProperties configProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //允许跨域
                .cors().and()
                //关闭csrf  前后端分离项目不必担心csrf攻击
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 允许匿名访问的接口（只允许未登录时访问）
                .antMatchers(configProperties.getNonFilterPath()).anonymous()
                //静态资源（swagger）放行
                .antMatchers(configProperties.getStaticSource()).permitAll()
                //基于配置为某个接口设置权限
                .antMatchers(configProperties.getJwtFilterPath()).access("@rbacExpressionRoot.hasRole('user')")
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();


        //添加过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置自定义异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

    }

    //暴露AuthenticationManagerBean
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //自定义密码encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
