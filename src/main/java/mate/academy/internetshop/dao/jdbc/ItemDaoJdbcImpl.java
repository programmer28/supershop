package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) throws DataProcessingException {
        String query = "INSERT INTO test.items (name, price) VALUES"
                + "(?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t insert item into DB" + e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long itemId) throws DataProcessingException {
        String query = "SELECT * FROM test.items WHERE item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, itemId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("item_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                Item item = new Item(name, price);
                item.setId(id);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get item by id " + itemId + e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        String query = "UPDATE test.items SET name = ?"
                + ", price = ? WHERE item_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setLong(3, item.getId());
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update item " + e);
        }
        return item;
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        String query =
                "DELETE FROM test.items WHERE item_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t delete item from DB with id " + id + e);
        }
    }

    @Override
    public boolean delete(Item item) throws DataProcessingException {
        return deleteById(item.getId());
    }

    @Override
    public List<Item> getAll() throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM test.items;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all items from DB" + e);
        }
        return items;
    }
}
