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
     * Ideally we want these users stored in a database.
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
         * @return Username
         */
        public String getUsername() {
            return this.username;
        }

        /**
         *
         * @return Password
         */
        public String getPassword() {
            return this.password;
        }

        /**
         * 
         * @return List of Roles
         */
        public String[] getRoles() {
            return this.roles;
        }
    }

    /**
     * Configure the users for the ATM UI.
     *
     * @param authParam The framework auth to configure.
     * @throws Exception If anything goes wrong.
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
     * Both ADMIN and ATM role has access to all web-app resources.
     *
     * We allow anyone to access the API.
     *
     * We will most likely change this in the future.
     *
     * @param httpParam The http to apply the security on.
     * @throws Exception If there are any configuration problems.
     */
    @Override
    protected void configure(HttpSecurity httpParam) throws Exception {

        //Web app access is restricted, while access tp /api/** is allowed for all.
        httpParam.authorizeRequests()
                //The JSF front-end...
                .antMatchers("/index.xhtml").access("hasRole('ADMIN') or hasRole('ATM')")
                .antMatchers("/javax*").access("hasRole('ADMIN') or hasRole('ATM')")
                //The API...
                .antMatchers("/api/**").permitAll()
                .and().formLogin();

    }
    
}
