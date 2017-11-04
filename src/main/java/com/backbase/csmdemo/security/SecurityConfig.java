package com.backbase.csmdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The security config for the ATM application.
 *
 * @author jasonbruwer on 11/4/17.
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static String ROLE_ADMIN = "ADMIN";
    private static String ROLE_ATM = "ATM";

    /**
     * The hard-coded users of the system.
     * Only the users below will be able to access the ATM application.
     * 
     */
    public static enum UserMapping{

        Admin("admin","12345", new String[]{ROLE_ADMIN,ROLE_ATM}),
        Johnny("johnny","123456", new String[]{ROLE_ATM}),
        Sally("sally","123456", new String[]{ROLE_ATM}),
        ;

        private String username;
        private String password;
        private String[] roles;

        /**
         * 
         * @param usernameParam
         * @param passwordParam
         * @param rolesParam
         */
        UserMapping(
                String usernameParam,
                String passwordParam,
                String[] rolesParam)
        {
            this.username = usernameParam;
            this.password = passwordParam;
            this.roles = rolesParam;
        }

        /**
         *
         * @return
         */
        public String getUsername() {
            return this.username;
        }

        /**
         *
         * @return
         */
        public String getPassword() {
            return this.password;
        }

        /**
         * 
         * @return
         */
        public String[] getRoles() {
            return this.roles;
        }
    }

    /**
     * Configure the users for the ATM UI.
     *
     * @param authParam
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authParam) throws Exception {

        for(UserMapping user : UserMapping.values())
        {
            authParam.inMemoryAuthentication().withUser(
                    user.getUsername()).password(
                    user.getPassword()).roles(user.getRoles());
        }

        //auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
    }

    /**
     * Admin has full access, while ATM role only has access to {@code index.xhtml}.
     *
     * //.antMatchers("/pages/main_page_tab_menu.xhtml").access("hasRole('ATM')")
     //.antMatchers("/index.xhtml").access("hasRole('ATM')")
     //.antMatchers("/javax.faces.resource/**").access("hasRole('ATM')")
     //.antMatchers("/javax**").access("hasRole('ADMIN')")
     //.antMatchers("jquery.js*").access("hasRole('ADMIN')")
     //.antMatchers("/pages/main_page_tab_menu.xhtml").access("hasRole('ROLE_ATM') or hasRole('ATM')")
     //.antMatchers("/index.xhtml").access("hasRole('ROLE_ATM') or hasRole('ATM')")
     * 
     * @param httpParam
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpParam) throws Exception {

        //Allow all resources for anyone authenticated...
        httpParam.authorizeRequests()
                .antMatchers("**").access("hasRole('ADMIN') or hasRole('ATM')")
                .and().formLogin();

    }
    
}
