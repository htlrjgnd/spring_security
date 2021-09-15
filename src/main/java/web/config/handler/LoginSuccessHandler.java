package web.config.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web.model.User;
import web.service.UserService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        httpServletResponse.sendRedirect(determineTargetUrl(authentication));
    }

    private String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            roles.add(grantedAuthority.getAuthority());
        }
        if(roles.contains("ROLE_ADMIN")) {
            return "admin";
        } else if (roles.contains("ROLE_USER")) {
            return "user";
        }
        return "err";
    }
}