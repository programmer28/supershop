package mate.academy.internetshop.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.dao.impl.BucketDaoImpl;
import mate.academy.internetshop.dao.impl.OrderDaoImpl;
import mate.academy.internetshop.dao.impl.UserDaoImpl;
import mate.academy.internetshop.dao.jdbc.ItemDaoJdbcImpl;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.service.impl.BucketServiceImpl;
import mate.academy.internetshop.service.impl.ItemServiceImpl;
import mate.academy.internetshop.service.impl.OrderServiceImpl;
import mate.academy.internetshop.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Factory {
    private static ItemDao itemDao;
    private static BucketDao bucketDao;
    private static UserDao userDao;
    private static OrderDao orderDao;
    private static ItemService itemService;
    private static BucketService bucketService;
    private static UserService userService;
    private static OrderService orderService;

    private static Logger logger = LogManager.getLogger(Factory.class);
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/test?"
                            + "user=root&password=abcd1234&serverTimezone=UTC");
            logger.debug("Connection to DB is established");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Can`t establish connection to DB");
        }
    }

    public static ItemDao getItemDao() {
        if (itemDao == null) {
            return new ItemDaoJdbcImpl(connection);
        }
        return itemDao;
    }

    public static BucketDao getBucketDao() {
        if (bucketDao == null) {
            return new BucketDaoImpl();
        }
        return bucketDao;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            return new UserDaoImpl();
        }
        return userDao;
    }

    public static OrderDao getOrderDao() {
        if (orderDao == null) {
            return new OrderDaoImpl();
        }
        return orderDao;
    }

    public static ItemService getItemService() {
        if (itemService == null) {
            return new ItemServiceImpl();
        }
        return itemService;
    }

    public static BucketService getBucketService() {
        if (bucketService == null) {
            return new BucketServiceImpl();
        }
        return bucketService;
    }

    public static UserService getUserService() {
        if (userService == null) {
            return new UserServiceImpl();
        }
        return userService;
    }

    public static OrderService getOrderService() {
        if (orderService == null) {
            return new OrderServiceImpl();
        }
        return orderService;
    }
}
