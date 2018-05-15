package main.util;

import com.google.common.collect.ImmutableMap;
import main.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Map<Role, String> ROLE_TO_VIEW_MAP = ImmutableMap.of(
            Role.ADMIN, "/admin"
//           ,Role.USER, "/user"
    );

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response){
        System.out.println("[AuthSuccessHandler] determine target for " + SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
        HttpSession session = request.getSession(false);
        if (session != null) {
          //  session.setAttribute("user", user);
        }
        return ROLE_TO_VIEW_MAP.get(Role.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().replaceAll("[\\[\\]]", "")));
    }
}
