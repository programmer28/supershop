package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.model.Bucket;

public interface BucketDao {
    Bucket create(Bucket bucket) throws DataProcessingException;

    Optional<Bucket> get(Long bucketId) throws DataProcessingException;

    Bucket update(Bucket bucket) throws DataProcessingException;

    boolean delete(Bucket bucket) throws DataProcessingException;

    List<Bucket> getAll() throws DataProcessingException;

    Bucket getByUserId(Long userId) throws DataProcessingException;
}
