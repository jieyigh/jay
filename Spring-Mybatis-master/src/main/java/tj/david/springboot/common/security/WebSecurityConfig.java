package tj.david.springboot.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import tj.david.springboot.common.mybatis.config.MyBatisConfig;
import tj.david.springboot.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * 解释说明：
 * @EnableWebSecurity: 禁用Boot的默认Security配置，配合@Configuration启用自定义配置（需要扩展WebSecurityConfigurerAdapter）
 * @EnableGlobalMethodSecurity(prePostEnabled = true): 启用Security注解，例如最常用的@PreAuthorize
 * configure(HttpSecurity): Request层面的配置，对应XML Configuration中的<http>元素
 * configure(WebSecurity): Web层面的配置，一般用来配置无需安全检查的路径
 * configure(AuthenticationManagerBuilder): 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
 * Created by David on 2016/7/25.
 */
@Configuration
@EnableWebSecurity
//@AutoConfigureAfter(CustomFilterInvocationSecurityMetadataSource.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许所有用户访问”/”和”/home”
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/home").permitAll()
                .anyRequest().authenticated()
                .and()

                .exceptionHandling().accessDeniedPage("/403")
                .and()
            //指定退出登录url，登出成功之后跳转到home,并且清除session
                .logout().logoutUrl("/logout").logoutSuccessUrl("/home").invalidateHttpSession(true).permitAll()
                .and()
                //指定登录页面，登录成功之后跳转到home
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/home").failureUrl("/login?error").permitAll()
        .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(filterSecurityInterceptorObjectPostProcessor());

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/js/**", "/static/css/**", "/static/images/**",
                "/static/dlshouwen.grid.v1.2.1/**", "/static/dlshouwen.validator.v1.1.9/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }


    @Bean
    public ObjectPostProcessor<FilterSecurityInterceptor> filterSecurityInterceptorObjectPostProcessor() {

        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {

                object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);

                List<AccessDecisionVoter<?>> voters = new ArrayList<>();
                voters.add(new WebExpressionVoter());
                voters.add(new RoleVoter()); //添加前缀ROLE_
                AccessDecisionManager accessDecisionManager = new AffirmativeBased(voters);
                object.setAccessDecisionManager(accessDecisionManager);
                object.setAuthenticationManager(authenticationManager);
                return object;

            }
        };
    }



}
