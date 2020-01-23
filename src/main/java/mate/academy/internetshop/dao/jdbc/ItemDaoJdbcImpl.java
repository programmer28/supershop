package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = LogManager.getLogger(ItemDaoJdbcImpl.class);
    private static final String DB_NAME = "test";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String query = String.format(Locale.ROOT,
                "INSERT INTO %s.items (name, price) VALUES"
                + "('%s', %.2f);", DB_NAME, item.getName(), item.getPrice());
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can`t insert item into DB", e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long itemId) {
        String query = String.format(
                "SELECT * FROM %s.items WHERE item_id=%d;", DB_NAME, itemId);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Long id = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item(name, price);
                item.setId(id);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            logger.warn("Can`t get item by id " + itemId, e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        String query = String.format(Locale.ROOT,
                "UPDATE %s.items SET name='%s'"
                + ", price=%.2f WHERE item_id=%d;",
                DB_NAME, item.getName(), item.getPrice(), item.getId());
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can`t update item", e);
        }
        return item;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = String.format(
                "DELETE FROM %s.items WHERE item_id=%d;",
                DB_NAME, id);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            logger.warn("Can`t delete item from DB with id " + id, e);
        }
        return false;
    }

    @Override
    public boolean delete(Item item) {
        String query = String.format(
                "DELETE FROM %s.items WHERE item_id=%d;",
                DB_NAME, item.getId());
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            logger.warn("Can`t delete item from DB", e);
        }
        return false;
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        String query = String.format(
                "SELECT * FROM %s.items;", DB_NAME);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            logger.warn("Can`t get all items from DB", e);
        }
        return items;
    }
}
