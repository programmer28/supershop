package mate.academy.internetshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private static BucketDao bucketDao;

    @Inject
    private static OrderDao orderDao;

    @Inject
    private static UserDao userDao;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Optional<Order> get(Long orderId) {
        return orderDao.get(orderId);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public boolean deleteById(Long orderId) {
        return orderDao.deleteById(orderId);
    }

    @Override
    public boolean delete(Order order) {
        return orderDao.delete(order);
    }

    @Override
    public Order completeOrder(List<Item> items, User user) {
        Order order = new Order(items, user);
        Bucket bucket = new Bucket();
        bucket.setItems(items);
        orderDao.update(order);
        userDao.update(user);
        bucketDao.update(bucket);
        return order;
    }

    @Override
    public Optional<List<Order>> getUserOrders(User user) {
        List<Order> listOrders = new ArrayList<>();
        for (Order order : Storage.orders) {
            if (order.getUser().getId().equals(user.getId())) {
                listOrders.add(order);
            }
        }
        return Optional.ofNullable(listOrders);
    }
}
