package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InjectDataController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(InjectDataController.class);

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setName("Bob");
        user.setSurname("Martin");
        user.addRole(Role.of("USER"));
        user.setLogin("bob");
        user.setPassword("1");

        User admin = new User();
        admin.setName("Super");
        admin.setSurname("Mario");
        admin.addRole(Role.of("ADMIN"));
        admin.setLogin("admin");
        admin.setPassword("1");

        try {
            userService.create(user);
            userService.create(admin);
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("dpa_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/servlet/index");
    }
}
