package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
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
        return Storage.users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst();
    }

    @Override
    public User update(User user) {
        User userToUpdate = get(user.getId())
                .orElseThrow(() -> new NoSuchElementException("Can`t find user to update"));
        userToUpdate.setName(user.getName());
        return userToUpdate;
    }

    @Override
    public boolean delete(User user) {
        return Storage.users.remove(user);
    }

    @Override
    public Optional<User> findByLogin(String login) throws AuthenticationException {
        return Storage.users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Storage.users.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst();
    }
}
