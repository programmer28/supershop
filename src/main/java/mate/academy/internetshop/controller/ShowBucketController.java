package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;

public class ShowBucketController extends HttpServlet {
    private static final Long USER_ID = 1L;

    @Inject
    private static ItemService itemService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Bucket newBucket = new Bucket(USER_ID);
        req.getRequestDispatcher("/WEB-INF/views/bucket.jsp")
                .forward(req, resp);
    }
}
