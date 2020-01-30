package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) throws DataProcessingException {
        String query = "INSERT INTO test.users(name, surname, login, password, salt)"
                + "VALUES(?, ?, ?, ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(query,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, user.getPassword());
            stmt.setBytes(5, user.getSalt());
            stmt.executeUpdate();
            addRoles(user, user.getRoles());
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user " + e);
        }
    }

    @Override
    public Optional<User> get(Long userId) throws DataProcessingException {
        String query = "SELECT * FROM test.users WHERE user_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = userSetFields(rs);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user by id " + userId + e);
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) throws DataProcessingException {
        String query = "UPDATE test.users"
                + "SET name = ?, surname = ?, login = ?, password = ?, salt = ?"
                + "WHERE user_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, user.getPassword());
            stmt.setBytes(5, user.getSalt());
            stmt.setLong(6, user.getId());
            stmt.executeUpdate();

            Set<Role> oldRoles = getRoles(user);
            Set<Role> newRoles = user.getRoles();

            Set<Role> rolesToDelete = new HashSet<>(oldRoles);
            rolesToDelete.removeAll(newRoles);
            deleteRoles(user, rolesToDelete);

            Set<Role> rolesToAdd = new HashSet<>(newRoles);
            rolesToAdd.removeAll(oldRoles);
            addRoles(user, rolesToAdd);
        } catch (SQLException e) {
            throw  new DataProcessingException("Can't update user by id " + user.getId() + e);
        }
        return user;
    }

    @Override
    public boolean delete(User user) throws DataProcessingException {
        deleteRoles(user, user.getRoles());
        String query = "DELETE FROM test.users WHERE user_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, user.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user " + e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws DataProcessingException {
        String query = "SELECT * FROM test.users WHERE login = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = userSetFields(rs);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user by login " + e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        String query = "SELECT * FROM test.users WHERE token = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = userSetFields(rs);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by token " + e);
        }
        return Optional.empty();
    }

    private User userSetFields(ResultSet rs) throws SQLException, DataProcessingException {
        Long userId = rs.getLong("user_id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String login = rs.getString("login");
        String password = rs.getString("password");
        String token = rs.getString("token");
        byte[] salt = rs.getBytes("salt");
        User user = new User(name);
        user.setId(userId);
        user.setSurname(surname);
        user.setLogin(login);
        user.setPassword(password);
        user.setToken(token);
        user.setSalt(salt);
        user.setRoles(getRoles(user));
        return user;
    }

    private void addRoles(User user, Set<Role> roles) throws DataProcessingException {
        String query =
                "INSERT INTO test.users_roles (user_id, role_id) VALUES"
                + "(?, (SELECT role_id FROM test.roles WHERE role_name = ?));";
        changeRolesExecute(user, roles, query);
    }

    private void deleteRoles(User user, Set<Role> roles) throws DataProcessingException {
        String query =
                "DELETE FROM test.users_roles WHERE user_id = ? AND "
                        + "role_id = (SELECT role_id FROM test.roles WHERE role_name = ?);";
        changeRolesExecute(user, roles, query);
    }

    private void changeRolesExecute(User user, Set<Role> roles, String query)
                throws DataProcessingException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Role role : roles) {
                statement.setLong(1, user.getId());
                statement.setString(2, role.getRoleName().toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw  new DataProcessingException("Can't update user roles " + e);
        }
    }

    private Set<Role> getRoles(User user) throws DataProcessingException {
        Set<Role> roles = new HashSet<>();
        String query =
                "SELECT role_name, r.role_id FROM test.roles r JOIN test.users_roles ur"
                + " ON r.role_id = ur.role_id AND user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String roleName = rs.getString("role_name");
                Long roleId = rs.getLong("r.role_id");
                Role role = Role.of(roleName);
                role.setId(roleId);
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user roles " + e);
        }
    }
}
