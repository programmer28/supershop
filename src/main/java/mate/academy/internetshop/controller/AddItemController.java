package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddItemController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(AddItemController.class);

    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/addItem.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Item newItem = new Item();
        newItem.setName(req.getParameter("item_name"));
        newItem.setPrice(Double.valueOf(req.getParameter("item_price")));
        try {
            itemService.create(newItem);
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("dpa_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");
    }
}
