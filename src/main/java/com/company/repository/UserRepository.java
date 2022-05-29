package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.entities.User;
import com.company.entities.Ticket;
import com.company.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRepository {
    private static UserRepository instance;

    private UserRepository(){}

    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    private User maptoObject(ResultSet resultSet){
        try {
            if (resultSet.next()) {
                // (int id, String name, String phoneNumber, String email, String address, List<Ticket> tickets)
                List<Ticket> tickets = new ArrayList<>();
                return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4),  resultSet.getString(5) , tickets) ;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    private List<User> mapToList(ResultSet resultSet){
        List<User> list = new ArrayList<>();
        try {
            while(true){
                User user = maptoObject(resultSet);
                if (user == null)
                    return list;
                else list.add(user);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<User> getAll(){
        String selectSql = "SELECT * FROM user";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectSql);
            return mapToList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getMaxId(){
        String selectSql = "SELECT * FROM user ORDER BY id DESC LIMIT 0, 1";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectSql);
            if (resultSet.next())
                return maptoObject(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTable() {
        // (int id, String name, String phoneNumber, String email, String address, List<Ticket> tickets)
        String createSql = "CREATE TABLE IF NOT EXISTS user " +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(50), " +
                "phoneNumber varchar(50) ," +
                "email varchar(50), " +
                "address varchar(50)" +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(User user){
        String insertSql = "INSERT INTO user(name, phoneNumber, email, address)"
                + "VALUES (?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getAddress());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        String selectSql = "SELECT * FROM user WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return maptoObject(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByName(String name) {
        String selectSql = "SELECT * FROM user WHERE name=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            return maptoObject(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUserName(String name, int id){
        String updateSql = "UPDATE user SET name=? WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id){
        String deleteSql = "DELETE FROM user WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayUsers(){
        String selectSql = "SELECT * FROM user";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                // user(name, phoneNumber, email, address)
                System.out.println("Id:" + resultSet.getInt(1));
                System.out.println("Name:" + resultSet.getString(2));
                System.out.println("Phone Number:" + resultSet.getString(3));
                System.out.println("Email" + resultSet.getString(4));
                System.out.println("Address:" + resultSet.getString(5));
                System.out.println("------------------------------------------------------------- ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayIdUsers(){
        String selectSql = "SELECT id FROM user";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Id:" + resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
