package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private static OrderDao orderDao;

    @Override
    public Order get(Long orderId) throws DataProcessingException {
        return orderDao.get(orderId).orElseThrow(()
                -> new NoSuchElementException("There is no order with id " + orderId));
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        return orderDao.update(order);
    }

    @Override
    public boolean delete(Order order) throws DataProcessingException {
        return orderDao.delete(order);
    }

    @Override
    public Order completeOrder(List<Item> items, User user) throws DataProcessingException {
        Order order = new Order();
        order.setItems(items);
        order.setUserId(user.getId());
        orderDao.create(order);
        return order;
    }

    @Override
    public List<Order> getUserOrders(User user) throws DataProcessingException {
        return orderDao.getUserOrders(user);
    }
}
