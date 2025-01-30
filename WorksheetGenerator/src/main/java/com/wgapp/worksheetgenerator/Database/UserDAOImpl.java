package com.wgapp.worksheetgenerator.Database;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    @Override
    public UserEntity createUser(String username, String password) {

        String sql = "INSERT INTO users (user_name, user_password) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Hash the password before storing it
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

            // Set values in prepared statement
            pstmt.setString(1, username);  // Use username parameter
            pstmt.setString(2, hashedPassword);  // Use hashed password

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated ID
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        // Create a UserEntity and set its ID
                        UserEntity user = new UserEntity();
                        user.setUserId(rs.getInt(1));  // Set generated user ID
                        user.setUsername(username);  // Set username
                        user.setPassword(hashedPassword);  // Set hashed password
                        return user;
                    }
                }
            }

            throw new SQLException("Failed to create user, no rows affected.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating user in database", e);
        }
    }
// userdaoImpl
@Override
public UserEntity findUserByUsername(String username, String password) {
    String sql = "SELECT user_name, user_password, user_pinNumber FROM users WHERE user_name = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement pstmt = connection.prepareStatement(sql)) {

        pstmt.setString(1, username);
        System.out.println("from DA)" + password);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                String storedHashedPassword = rs.getString("user_password");

                // Check if the provided password matches the stored hash
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    // Create UserEntity and return it
                    UserEntity user = new UserEntity();
                    user.setUsername(rs.getString("user_name"));
                    user.setPassword(storedHashedPassword); // Store hashed password (not raw)
                    user.setPinNumber(rs.getInt("user_pinNumber")); // Include other user details

                    return user;
                } else {
                    throw new RuntimeException("Invalid password");
                }
            }
        }
        throw new RuntimeException("User not found");

    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Error retrieving user", e);
    }
}


}



