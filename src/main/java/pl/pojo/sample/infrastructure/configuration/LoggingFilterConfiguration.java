package pl.pojo.sample.infrastructure.configuration;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingFilterConfiguration {

    @Bean
    public FilterRegistrationBean loggingFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(new MDCInsertingServletFilter());
        registration.setOrder(1);

        return registration;
    }

}