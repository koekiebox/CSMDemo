package com.backbase.csmdemo.config;

import com.backbase.csmdemo.security.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * The app configuration for view.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 */
@EnableWebMvc
@Configuration
@ComponentScan({ "com.backbase.csmdemo.web.*" })
@Import({ SecurityConfig.class })
public class AppConfig {

    /**
     * Setup the view resolver.
     * 
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        
        InternalResourceViewResolver viewResolver =
                new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        
        //viewResolver.setPrefix("/");
        viewResolver.setSuffix("*");
        
        return viewResolver;
    }
}
