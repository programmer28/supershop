package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;

@Dao
public class BucketDaoImpl implements BucketDao {
    @Override
    public Bucket create(Bucket bucket) {
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long bucketId) {
        return Storage.buckets.stream()
                .filter(b -> b.getId().equals(bucketId))
                .findFirst();
    }

    @Override
    public Bucket update(Bucket bucket) {
        Bucket bucketToUpdate = get(bucket.getId())
                .orElseThrow(() -> new NoSuchElementException("Can`t find bucket to update"));
        bucketToUpdate.setItems(bucket.getItems());
        bucketToUpdate.setUserId(bucket.getUserId());
        return bucketToUpdate;
    }

    @Override
    public boolean delete(Bucket bucket) {
        return Storage.buckets.remove(bucket);
    }

    @Override
    public List<Bucket> getAll() {
        return Storage.buckets;
    }

    @Override
    public Bucket getByUserId(Long userId) {
        return Storage.buckets.stream()
                .filter(b -> b.getUserId().equals(userId))
                .findFirst().orElse(create(new Bucket(userId)));
    }
}
