package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.User;

public interface UserService {
    User create(User user);

    User get(Long userId);

    User update(User user);

    boolean delete(User user);

    List<User> getAll();

    User login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);
}
