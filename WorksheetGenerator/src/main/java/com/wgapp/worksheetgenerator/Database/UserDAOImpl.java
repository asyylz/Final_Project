package com.wgapp.worksheetgenerator.Database;

import com.wgapp.worksheetgenerator.Exceptions.CustomDatabaseException;
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

            throw new CustomDatabaseException("Failed to create user, no rows affected.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new CustomDatabaseException(e);
        }
    }

    // userdaoImpl
    @Override
    public UserEntity findUserByUsername(String username, String password) {
        String sql = "SELECT user_name, user_password, user_pinNumber FROM users WHERE user_name = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, username);
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
                        throw new CustomDatabaseException("Your password is incorrect. Please try again.");
                    }
                }
            }
            throw new CustomDatabaseException("Your username is incorrect.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving user", e);
        }
    }



    //Repo/DAO
    @Override
    public void setPinNumber(int pinNumber, String username) {
        String sql = "UPDATE users SET user_pinNumber = ? WHERE user_name = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Set values in the prepared statement
            pstmt.setInt(1, pinNumber);   // Set the PIN
            pstmt.setString(2, username); // Identify user by username

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating PIN failed, no user found with username: " + username);
            }

        } catch (SQLException e) {
            throw new CustomDatabaseException(e);
        }


    }

    @Override
    public void updatePassword(String username, String oldPassword, String newPassword) {
        String sqlSelect = "SELECT user_password FROM users WHERE user_name = ?";
        String sqlUpdate = "UPDATE users SET user_password = ? WHERE user_name = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(sqlSelect);
             PreparedStatement updateStmt = connection.prepareStatement(sqlUpdate)) {

            // Step 1: Fetch the stored hashed password from the database
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                throw new CustomDatabaseException("User not found with username: " + username);
            }

            String storedHashedPassword = rs.getString("user_password");
            System.out.println(storedHashedPassword);
            // Step 2: Compare the old password with the stored hash
            if (!BCrypt.checkpw(oldPassword, storedHashedPassword)) {
                throw new CustomDatabaseException("Incorrect old password.");
            }

            // Step 3: Hash the new password
            String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));

            // Step 4: Update the password in the database
            updateStmt.setString(1, newHashedPassword);
            updateStmt.setString(2, username);
            int affectedRows = updateStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating password failed, no user found with username: " + username);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating password in database", e);
        }
    }

}





