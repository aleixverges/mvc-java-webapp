package com.shibsted.mvc.model;

import java.sql.*;

public class UserRepository extends AbstractRepository {

    public User userOfUsernameAndPassword(String username, String password) {

        User user = null;

        try {
            String userOfUsernameAndPasswordQuery = "SELECT * from USERS WHERE username = '" + username + "' AND password = '" + password + "'";
            PreparedStatement statement = this.connection.prepareStatement(userOfUsernameAndPasswordQuery);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String foundUsername = resultSet.getString(2);
                String foundRole = resultSet.getString(3);
                String foundPassword = resultSet.getString(4);

                user = new User(foundUsername, foundPassword);
                user.setRole(foundRole);

                return user;
            }

        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }

        return user;
    }
}
