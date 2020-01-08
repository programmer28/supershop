package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long orderId) {
        return Storage.orders.stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst();
    }

    @Override
    public Order update(Order order) {
        Order orderToUpdate = get(order.getId())
                .orElseThrow(() -> new NoSuchElementException("Can`t find order to update"));
        orderToUpdate.setItems(order.getItems());
        orderToUpdate.setUserId(order.getUserId());
        return orderToUpdate;
    }

    @Override
    public boolean delete(Order order) {
        return Storage.orders.remove(order);
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return Storage.orders.stream()
                .filter(o -> o.getUserId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}
