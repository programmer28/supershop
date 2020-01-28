package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public interface OrderDao {
    Order create(Order order) throws DataProcessingException;

    Optional<Order> get(Long orderId) throws DataProcessingException;

    Order update(Order order) throws DataProcessingException;

    boolean delete(Order order) throws DataProcessingException;

    List<Order> getAll() throws DataProcessingException;

    List<Order> getUserOrders(User user) throws DataProcessingException;
}
