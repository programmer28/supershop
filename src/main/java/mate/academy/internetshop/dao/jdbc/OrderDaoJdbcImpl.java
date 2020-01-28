package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {

    @Inject
    private static ItemDao itemDao;

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) throws DataProcessingException {
        String query = "INSERT INTO test.orders (user_id) VALUES(?);";
        Long orderId = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getUserId());
            orderId = statement.executeUpdate() * 1L;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t insert order into test.orders" + e);
        }
        String insertOrderItemQuery = "INSERT INTO test.orders_items"
                + "(order_id, item_id) "
                + "VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertOrderItemQuery)) {
            statement.setLong(1, orderId);
            for (Item item : order.getItems()) {
                statement.setLong(2, item.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t insert items into test.orders_items" + e);
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long orderId) throws DataProcessingException {
        Order order = new Order();
        String query = "SELECT * FROM test.orders WHERE order_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long idOrder = rs.getLong("order_id");
                Long idUser = rs.getLong("user_id");
                order.setId(idOrder);
                order.setId(idUser);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t get order by id " + orderId + e);
        }
        List<Item> itemList = new ArrayList<>();
        String getAllItemsQuery =
                "SELECT test.orders_items.item_id FROM test.orders_items "
                + "WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(getAllItemsQuery)) {
            statement.setLong(1, order.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long idItem = rs.getLong("item_id");
                Item item = itemDao.get(idItem).get();
                itemList.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t get id of items by id of test.orders" + order.getId());
        }
        order.setItems(itemList);
        return Optional.ofNullable(order);
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        String removeOrdersItems =
                "DELETE FROM test.orders_items WHERE order_id = ?;";
        try (PreparedStatement statement
                     = connection.prepareStatement(removeOrdersItems)) {
            statement.setLong(1, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the orders " + e);
        }
        String insertIntoOrdersItems =
                "INSERT INTO test.orders_items (order_id, item_id) VALUES(?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertIntoOrdersItems)) {
            statement.setLong(1, order.getId());
            for (Item item : order.getItems()) {
                statement.setLong(2, order.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the orders " + e);
        }
        return order;
    }

    @Override
    public boolean delete(Order order) throws DataProcessingException {
        String query =
                "DELETE FROM test.orders_items WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order from test.orders_items " + e);
        }
    }

    @Override
    public List<Order> getAll() throws DataProcessingException {
        List<Order> list = new ArrayList<>();
        String query =
                "SELECT test.orders.order_id FROM test.orders;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long idOrder = rs.getLong("order_id");
                Order order = get(idOrder).get();
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders from test.orders " + e);
        }
    }

    @Override
    public List<Order> getUserOrders(User user) throws DataProcessingException {
        List<Order> list = new ArrayList<>();
        String getOrdersQuery = "SELECT test.orders.order_id, "
                + "test.orders.user_id, test.items.item_id, "
                + "test.items.name, test.items.price"
                + "FROM test.orders"
                + "INNER JOIN test.orders_items "
                + "ON test.orders.order_id = test.orders_items.order_id"
                + "INNER JOIN test.items "
                + "ON test.orders_items.item_id = test.items.item_id"
                + "WHERE test.orders.user_id = ?"
                + "ORDER BY test.orders.order_id;";

        try (PreparedStatement statement = connection.prepareStatement(getOrdersQuery)) {
            statement.setLong(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long orderId = rs.getLong("order_id");
                Order order = get(orderId).get();
                list.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get orders from test.orders by " + user.getId() + e);
        }
        return list;
    }
}
