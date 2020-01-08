package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoImpl implements ItemDao {
    @Override
    public Item create(Item item) {
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long itemId) {
        for (Item item :Storage.items) {
            if (item.getId().equals(itemId)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        for (int i = 0; i < Storage.items.size(); i++) {
            if (Storage.items.get(i).getId().equals(item.getId())) {
                Storage.items.set(i, item);
                return item;
            }
        }
        return create(item);
    }

    @Override
    public boolean deleteById(Long itemId) {
        for (int i = 0; i < Storage.items.size(); i++) {
            if (Storage.items.get(i).getId().equals(itemId)) {
                return Storage.items.remove(i) != null ? true : false;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Item item) {
        for (int i = 0; i < Storage.items.size(); i++) {
            if (Storage.items.get(i).getId().equals(item.getId())) {
                return Storage.items.remove(i) != null ? true : false;
            }
        }
        return false;
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }
}
