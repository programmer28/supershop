package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {

    @Inject
    private static BucketDao bucketDao;

    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long bucketId) {
        return bucketDao.get(bucketId).orElseThrow(()
                -> new NoSuchElementException("There is no bucket with id " + bucketId));
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean delete(Bucket bucket) {
        return bucketDao.delete(bucket);
    }

    @Override
    public Bucket addItem(Bucket bucket, Item item) {
        bucket.getItems().add(itemDao.create(item));
        return bucketDao.update(bucket);
    }

    @Override
    public boolean deleteItem(Bucket bucket, Item item) {
        return bucket.getItems().remove(item);
    }

    @Override
    public void clear(Bucket bucket) {
        bucket.getItems().clear();
        bucketDao.update(bucket);
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucket.getItems();
    }
}
