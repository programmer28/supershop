package mate.academy.internetshop.service;

import java.util.List;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface OrderService {
    Order get(Long orderId) throws DataProcessingException;

    Order update(Order order) throws DataProcessingException;

    boolean delete(Order order) throws DataProcessingException;

    Order completeOrder(List<Item> items, User user) throws DataProcessingException;

    List<Order> getUserOrders(User user) throws DataProcessingException;
}
