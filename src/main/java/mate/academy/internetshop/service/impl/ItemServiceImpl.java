package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    @Inject
    private static ItemDao itemDao;

    @Override
    public Item create(Item item) throws DataProcessingException {
        return itemDao.create(item);
    }

    @Override
    public Item get(Long itemId) throws DataProcessingException {
        return itemDao.get(itemId).orElseThrow(()
                -> new NoSuchElementException("There is no item with id " + itemId));
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        return itemDao.update(item);
    }

    @Override
    public boolean deleteById(Long itemId) throws DataProcessingException {
        return itemDao.deleteById(itemId);
    }

    @Override
    public boolean delete(Item item) throws DataProcessingException {
        return itemDao.delete(item);
    }

    @Override
    public List<Item> getAllItems() throws DataProcessingException {
        return itemDao.getAll();
    }
}
