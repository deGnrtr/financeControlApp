package by.degen.DAO.remote;

import by.degen.DAO.DAOException;
import by.degen.DAO.mappers.UserMapper;
import by.degen.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDatabaseDAO extends AbstractDatabaseDAO<User>{

    @Override
    public User findById(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT username password FROM users WHERE user_id = (?)",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            result.next();
            String username = result.getString("username");
            String password = result.getString("password");
            return new User(username, password);
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public User findByName(String name) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = (?)",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            result.next();
            return UserMapper.getUser(result);
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public List<User> findAll() throws DAOException {
        try (Statement statement = connection.createStatement()){
            String quarry = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(quarry);
            ArrayList<User> allUsers = new ArrayList<>();
            while (result.next()){
                allUsers.add(UserMapper.getUser(result));
            }
            return allUsers;
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public void create(User user) throws DAOException {
        String username = user.getUsername();
        String password = user.getPassword();
        String quarry = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(quarry, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setUserId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public void update(User updatedUser, int id) throws DAOException {
        String username = updatedUser.getUsername();
        String password = updatedUser.getPassword();
        String quarry = "UPDATE users SET username = (?), password = (?) WHERE user_id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(quarry)){
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }

    @Override
    public void delete(User user) throws DAOException {
        String username = user.getUsername();
        String password = user.getPassword();
        String quarry = "DELETE FROM users WHERE username = (?) AND password = (?)";
        if (user.getUserId() != 0){
            try (PreparedStatement statement = connection.prepareStatement(quarry)){
                statement.setString(1, username);
                statement.setString(2, password);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException("SQL error!" + e.getMessage(), e);
            }
        }else {
            throw new DAOException("No target element found!");
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        String quarry = "DELETE FROM users WHERE user_id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(quarry)){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("SQL error!" + e.getMessage(), e);
        }
    }
}
