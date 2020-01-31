package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.HashUtil;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) throws DataProcessingException {
        user = setAdditionalParameters(user);
        return userDao.create(user);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    private User setAdditionalParameters(User user) {
        byte[] salt = HashUtil.getSalt();
        String hashPassword = HashUtil.hashPassword(user.getPassword(), salt);
        user.setPassword(hashPassword);
        user.setSalt(salt);
        user.setToken(getToken());
        return user;
    }

    @Override
    public User get(Long userId) throws DataProcessingException {
        return userDao.get(userId).orElseThrow(()
                -> new NoSuchElementException("There is no user with id " + userId));
    }

    @Override
    public User update(User user) throws DataProcessingException {
        user = setAdditionalParameters(user);
        return userDao.update(user);
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        return userDao.delete(user);
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User login(String login, String password)
            throws AuthenticationException, DataProcessingException {
        Optional<User> user = userDao.findByLogin(login);
        if (user.isPresent()) {
            String currentHashPassword = HashUtil.hashPassword(password, user.get().getSalt());
            if (currentHashPassword.equals(user.get().getPassword())) {
                return user.get();
            }
        }
        throw new AuthenticationException("Incorrect username or password");
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        return userDao.getByToken(token);
    }
}
