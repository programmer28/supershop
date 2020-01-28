package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {

    @Inject
    private static ItemDao itemDao;

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        String query = "INSERT INTO test.buckets (user_id) VALUES(?);";
        Long bucketId = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucket.getUserId());
            bucketId = statement.executeUpdate() * 1L;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t insert bucket into test.buckets" + e);
        }
        String insertBucketItemQuery = "INSERT INTO test.buckets_items"
                + "(bucket_id, item_id) "
                + "VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertBucketItemQuery)) {
            statement.setLong(1, bucketId);
            for (Item item : bucket.getItems()) {
                statement.setLong(2, item.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t insert items into test.buckets_items" + e);
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long bucketId) throws DataProcessingException {
        Bucket bucket = new Bucket();
        String query = "SELECT * FROM test.buckets WHERE bucket_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucketId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long idBucket = rs.getLong("bucket_id");
                Long idUser = rs.getLong("user_id");
                bucket.setId(idBucket);
                bucket.setId(idUser);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t get bucket by id " + bucketId + e);
        }
        List<Item> itemList = new ArrayList<>();
        String getAllItemsQuery =
                "SELECT test.buckets_items.item_id FROM test.buckets_items "
                        + "WHERE bucket_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(getAllItemsQuery)) {
            statement.setLong(1, bucket.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long idItem = rs.getLong("item_id");
                Item item = itemDao.get(idItem).get();
                itemList.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can`t get id of items by id of test.buckets" + bucket.getId());
        }
        bucket.setItems(itemList);
        return Optional.ofNullable(bucket);
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        String removeBucketsItems =
                "DELETE FROM test.buckets_items WHERE bucket_id = ?;";
        try (PreparedStatement statement
                     = connection.prepareStatement(removeBucketsItems)) {
            statement.setLong(1, bucket.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the buckets " + e);
        }
        String insertIntoBucketsItems =
                "INSERT INTO test.buckets_items (bucket_id, item_id) VALUES(?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertIntoBucketsItems)) {
            statement.setLong(1, bucket.getId());
            for (Item item : bucket.getItems()) {
                statement.setLong(2, bucket.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update the buckets " + e);
        }
        return bucket;
    }

    @Override
    public boolean delete(Bucket bucket) throws DataProcessingException {
        String query =
                "DELETE FROM test.buckets_items WHERE bucket_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, bucket.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete bucket from test.buckets_items " + e);
        }
    }

    @Override
    public List<Bucket> getAll() throws DataProcessingException {
        List<Bucket> list = new ArrayList<>();
        String query =
                "SELECT test.buckets.bucket_id FROM test.buckets;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Long idBucket = rs.getLong("bucket_id");
                Bucket bucket = get(idBucket).get();
                list.add(bucket);
            }
            return list;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all buckets from test.buckets " + e);
        }
    }

    @Override
    public Bucket getByUserId(Long userId) throws DataProcessingException {
        Long idBucket = null;
        String query = "SELECT bucket_id FROM test.buckets WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                idBucket = rs.getLong("bucket_id");
            }
            Bucket bucket = get(idBucket).get();
            return bucket;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't get bucket from test.buckets by user id " + userId + e);
        }
    }
}
