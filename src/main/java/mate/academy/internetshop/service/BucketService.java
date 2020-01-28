package mate.academy.internetshop.service;

import java.util.List;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketService {
    Bucket create(Bucket bucket) throws DataProcessingException;

    Bucket get(Long bucketId) throws DataProcessingException;

    Bucket update(Bucket bucket) throws DataProcessingException;

    boolean delete(Bucket bucket) throws DataProcessingException;

    Bucket addItem(Bucket bucket, Item item) throws DataProcessingException;

    boolean deleteItem(Bucket bucket, Item item);

    void clear(Bucket bucket) throws DataProcessingException;

    List<Item> getAllItems(Bucket bucket);

    Bucket getByUserId(Long userId) throws DataProcessingException;
}
