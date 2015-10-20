package com.practice.authentication;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.api.user.UserService;
import com.juric.carbon.schema.user.Auth;
import com.juric.carbon.schema.user.User;
import com.mysql.jdbc.NotUpdatable;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Eric on 10/18/2015.
 */
public class UserAuthenticationProvider implements AuthenticationProvider, InitializingBean {
    private final static Log LOG = LogFactory.getLog(UserAuthenticationProvider.class);

    private UserPasswordService userPasswordService;

    public void setUserPasswordService(UserPasswordService userPasswordService) {
        this.userPasswordService = userPasswordService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        Auth auth = new Auth();
        auth.setUsername(name);
        auth.setPassword(password);
        User user = null;
        try {
            user = userPasswordService.authticate(auth);
        } catch (Exception e) {
            LOG.error(e);
        }

        if (user != null) {
            Collection<GrantedAuthority> authorityList = new ArrayList<>();
            return new UsernamePasswordAuthenticationToken(user, null, authorityList);
        } else {
            throw new BadCredentialsException("Unable to auth user " + name);
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(userPasswordService);
    }
}
