package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface OrderDao {
    Order create(Order order);

    Optional<Order> get(Long orderId);

    Order update(Order order);

    boolean delete(Order order);

    List<Order> getAll();

    List<Order> getUserOrders(User user);
}
