package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddItemToBucketController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(AddItemToBucketController.class);

    @Inject
    private static ItemService itemService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        Bucket bucket = null;
        try {
            bucket = bucketService.getByUserId(userId);
            String itemId = req.getParameter("item_id");
            Item item = itemService.get(Long.valueOf(itemId));
            bucketService.addItem(bucket, item);
        } catch (DataProcessingException e) {
            logger.error(e);
            req.setAttribute("dpa_msg", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/dbError.jsp").forward(req, resp);
        }
        req.setAttribute("bucket", bucket);
        req.getRequestDispatcher("/WEB-INF/views/bucket.jsp")
                .forward(req, resp);
    }
}
