package mate.academy.internetshop.dao.impl;

import java.util.List;
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
        for (Bucket bucket : Storage.buckets) {
            if (bucket.getId().equals(bucketId)) {
                return Optional.of(bucket);
            }
        }
        return Optional.empty();
    }

    @Override
    public Bucket update(Bucket bucket) {
        for (int i = 0; i < Storage.buckets.size(); i++) {
            if (Storage.buckets.get(i).getId().equals(bucket.getId())) {
                Storage.buckets.set(i, bucket);
                return bucket;
            }
        }
        return create(bucket);
    }

    @Override
    public boolean deleteById(Long bucketId) {
        for (int i = 0; i < Storage.buckets.size(); i++) {
            if (Storage.buckets.get(i).getId().equals(bucketId)) {
                return Storage.buckets.remove(i) != null ? true : false;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Bucket bucket) {
        for (int i = 0; i < Storage.buckets.size(); i++) {
            if (Storage.buckets.get(i).getId().equals(bucket.getId())) {
                return Storage.buckets.remove(i) != null ? true : false;
            }
        }
        return false;
    }

    @Override
    public List<Bucket> getAll() {
        return Storage.buckets;
    }
}
