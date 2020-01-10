package mate.academy.internetshop.service;

import java.util.List;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface OrderService {
    Order get(Long orderId);

    Order update(Order order);

    boolean delete(Order order);

    Order completeOrder(List<Item> items, User user);

    List<Order> getUserOrders(User user);
}
