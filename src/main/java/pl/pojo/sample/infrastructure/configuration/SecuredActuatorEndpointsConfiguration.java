package pl.pojo.sample.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecuredActuatorEndpointsConfiguration extends WebSecurityConfigurerAdapter {

    private static final String METRICS_ADMIN_ROLE = "METRICS_ADMIN";

    @Value("${security.basic.metrics.username}")
    private String metricsUserName;

    @Value("${security.basic.metrics.password}")
    private String metricsPassword;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.requestMatchers()
            .antMatchers("/actuator/**") //request matchers needed to not collide with OAuth (https://stackoverflow.com/a/32154950/9462835)
            .and()
            .authorizeRequests()
            .antMatchers("/actuator/metrics")
            .hasRole(SecuredActuatorEndpointsConfiguration.METRICS_ADMIN_ROLE)
            .antMatchers("/actuator/health")
            .permitAll()
            .antMatchers("/actuator/info")
            .permitAll()
            .and()
            .httpBasic();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser(this.metricsUserName)
            .password("{noop}" + this.metricsPassword)
            .roles(SecuredActuatorEndpointsConfiguration.METRICS_ADMIN_ROLE);
    }
}