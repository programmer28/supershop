package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
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
        return Storage.items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst();
    }

    @Override
    public Item update(Item item) {
        Item itemToUpdate = get(item.getId())
                .orElseThrow(() -> new NoSuchElementException("Can`t find item to update"));
        itemToUpdate.setName(item.getName());
        itemToUpdate.setPrice(item.getPrice());
        return itemToUpdate;
    }

    @Override
    public boolean deleteById(Long itemId) {
        return Storage.items.remove(get(itemId).orElseThrow(()
                -> new NoSuchElementException("Can`t find item to delete")));
    }

    @Override
    public boolean delete(Item item) {
        return Storage.items.remove(item);
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }
}
