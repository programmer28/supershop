package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long orderId) {
        for (Order order : Storage.orders) {
            if (order.getId().equals(orderId)) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

    @Override
    public Order update(Order order) {
        for (int i = 0; i < Storage.orders.size(); i++) {
            if (Storage.orders.get(i).getId().equals(order.getId())) {
                Storage.orders.set(i, order);
                return order;
            }
        }
        return create(order);
    }

    @Override
    public boolean deleteById(Long orderId) {
        for (int i = 0; i < Storage.orders.size(); i++) {
            if (Storage.orders.get(i).getId().equals(orderId)) {
                return Storage.orders.remove(i) != null ? true : false;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Order order) {
        for (int i = 0; i < Storage.orders.size(); i++) {
            if (Storage.orders.get(i).getId().equals(order.getId())) {
                return Storage.orders.remove(i) != null ? true : false;
            }
        }
        return false;
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }
}
