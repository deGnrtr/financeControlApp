package by.degen.DAO.mappers;

import by.degen.entities.User;
import java.sql.*;

public class UserMapper {
    public static User getUser (ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("user_id");
        String username = resultSet.getString("username");
        String password  = resultSet.getString("password");
        User requestedUser = new User(username, password);
        requestedUser.setUserId(userId);
        return requestedUser;
    }

    public static void uploadUser (Connection conn, User user) throws SQLException{
        PreparedStatement statement = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getPassword());
        statement.executeUpdate();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        user.setUserId(resultSet.getInt(1));
        statement.close();
    }
}
