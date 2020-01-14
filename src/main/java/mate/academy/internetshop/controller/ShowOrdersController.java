package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class ShowOrdersController extends HttpServlet {
    private static final Long USER_ID = 1L;

    @Inject
    private static OrderService orderService;

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> orders = orderService
                .getUserOrders(userService.get(Long.valueOf(USER_ID)));
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/allOrders.jsp")
                .forward(req, resp);
    }
}
