package mate.academy.internetshop.dao.impl;

import java.util.Optional;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long userId) {
        for (User user : Storage.users) {
            if (user.getId().equals(userId)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) {
        for (int i = 0; i < Storage.users.size(); i++) {
            if (Storage.users.get(i).getId().equals(user.getId())) {
                Storage.users.set(i, user);
                return user;
            }
        }
        return create(user);
    }

    @Override
    public boolean deleteById(Long userId) {
        for (int i = 0; i < Storage.users.size(); i++) {
            if (Storage.users.get(i).getId().equals(userId)) {
                return Storage.users.remove(i) != null ? true : false;
            }
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        for (int i = 0; i < Storage.users.size(); i++) {
            if (Storage.users.get(i).getId().equals(user.getId())) {
                return Storage.users.remove(i) != null ? true : false;
            }
        }
        return false;
    }
}
