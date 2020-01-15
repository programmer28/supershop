package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) {
        user.setToken(getToken());
        return userDao.create(user);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User get(Long userId) {
        return userDao.get(userId).orElseThrow(()
                -> new NoSuchElementException("There is no user with id " + userId));
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(User user) {
        return userDao.delete(user);
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        return userDao.findByLogin(login, password);
    }

    @Override
    public Optional<User> getByToken(String token) {
        return userDao.getByToken(token);
    }
}
