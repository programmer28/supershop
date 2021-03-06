package mate.academy.internetshop.web.filters;

import static mate.academy.internetshop.model.Role.RoleName.ADMIN;
import static mate.academy.internetshop.model.Role.RoleName.USER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthorizationFilter implements Filter {
    public static final String EMPTY_STRING = "";
    private static Logger logger = LogManager.getLogger(AuthorizationFilter.class);

    @Inject
    private static UserService userService;

    public Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/servlet/getAllUsers", ADMIN);
        protectedUrls.put("/servlet/deleteUser", ADMIN);
        protectedUrls.put("/servlet/getAllItems", USER);
        protectedUrls.put("/servlet/addItem", USER);
        protectedUrls.put("/servlet/showBucket", USER);
        protectedUrls.put("/servlet/addItemToBucket", USER);
        protectedUrls.put("/servlet/deleteItemFromBucket", USER);
        protectedUrls.put("/servlet/showOrders", ADMIN);
        protectedUrls.put("/servlet/completeOrder", USER);
        protectedUrls.put("/servlet/deleteOrder", USER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            processUnAuthenticated(req, resp);
            return;
        }
        String requestedUrl = req.getRequestURI().replace(req.getContextPath(), EMPTY_STRING);
        Role.RoleName roleName = protectedUrls.get(requestedUrl);
        if (roleName == null) {
            processAuthenticated(chain, req, resp);
            return;
        }
        String token = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("MATE")) {
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) {
            processUnAuthenticated(req, resp);
            return;
        } else {
            Optional<User> user = null;
            try {
                user = userService.getByToken(token);
            } catch (DataProcessingException e) {
                logger.error(e);
                req.setAttribute("dpa_msg", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
            }
            if (user.isPresent()) {
                //check the user role
                if (verifyRole(user.get(), roleName)) {
                    processAuthenticated(chain, req, resp);
                    return;
                } else {
                    processDenied(req, resp);
                    return;
                }
            } else {
                processUnAuthenticated(req, resp);
                return;
            }
        }
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getRoleName().equals(roleName));
    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp")
                .forward(req, resp);
    }

    private void processAuthenticated(
            FilterChain chain, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        chain.doFilter(req, resp);

    }

    private void processUnAuthenticated(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void destroy() {

    }
}
