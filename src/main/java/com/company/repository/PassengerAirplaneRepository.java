package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.entities.PassengerAirplane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerAirplaneRepository {
    // int id, String name, double speed, int yearsOfFlying, double fuelCost, int numberOfSeats, int numberOfClasses

    private static PassengerAirplaneRepository instance;

    private PassengerAirplaneRepository(){}

    public static PassengerAirplaneRepository getInstance(){
        if(instance == null){
            instance = new PassengerAirplaneRepository();
        }
        return instance;
    }

    private PassengerAirplane maptoObject(ResultSet resultSet){
        try {
            if (resultSet.next()) {
                // int id, String name, double speed, int yearsOfFlying, double fuelCost, int numberOfSeats, int numberOfClasses

                return new PassengerAirplane(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3),
                        resultSet.getInt(4),  resultSet.getDouble(5) ,
                        resultSet.getInt(6), resultSet.getInt(7)) ;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    private List<PassengerAirplane> mapToList(ResultSet resultSet){
        List<PassengerAirplane> list = new ArrayList<>();
        try {
            while(true){
                PassengerAirplane PassengerAirplane = maptoObject(resultSet);
                if (PassengerAirplane == null)
                    return list;
                else list.add(PassengerAirplane);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<PassengerAirplane> getAll(){
        String selectSql = "SELECT * FROM passengerAirplane";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectSql);
            return mapToList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PassengerAirplane getMaxId(){
        String selectSql = "SELECT * FROM passengerAirplane ORDER BY id DESC LIMIT 0, 1";

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
        // int id, String name, double speed, int yearsOfFlying, double fuelCost, int numberOfSeats, int numberOfClasses
        String createSql = "CREATE TABLE IF NOT EXISTS passengerAirplane " +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(50), " +
                "speed double, " +
                "yearsOfFlying int, " +
                "fuelCost double," +
                "numberOfSeats int," +
                "numberOfClasses int" +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPassengerAirplane(PassengerAirplane passengerAirplane){
        String insertSql = "INSERT INTO passengerAirplane(name, speed, yearsOfFlying, fuelCost, numberOfSeats, numberOfClasses)"
                + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, passengerAirplane.getName());
            preparedStatement.setDouble(2, passengerAirplane.getSpeed());
            preparedStatement.setInt(3, passengerAirplane.getyearsOfFlying());
            preparedStatement.setDouble(4, passengerAirplane.getFuelCost());
            preparedStatement.setInt(5, passengerAirplane.getNumberOfSeats());
            preparedStatement.setInt(6, passengerAirplane.getNumberOfClasses());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PassengerAirplane getPassengerAirplaneById(int id) {
        String selectSql = "SELECT * FROM passengerAirplane WHERE id=?";

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

    public PassengerAirplane getPassengerAirplaneByName(String name) {
        String selectSql = "SELECT * FROM passengerAirplane WHERE name=?";

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

    public void updatePassengerAirplaneName(String name, int id){
        String updateSql = "UPDATE PassengerAirplane SET name=? WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePassengerAirplane(int id){
        String deleteSql = "DELETE FROM passengerAirplane WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayPassengerAirplanes(){
        String selectSql = "SELECT * FROM passengerAirplane";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                // PassengerAirplane(name, speed, yearsOfFlying, fuelCost, materials, quantity)
                System.out.println("Id:" + resultSet.getInt(1));
                System.out.println("Name:" + resultSet.getString(2));
                System.out.println("Speed:" + resultSet.getDouble(3));
                System.out.println("Years of flying:" + resultSet.getInt(4));
                System.out.println("Fuel Cost:" + resultSet.getDouble(5));
                System.out.println("Number of Seats:" + resultSet.getInt(6));
                System.out.println("Number of Classes:" + resultSet.getInt(7));
                System.out.println("------------------------------------------------------------- ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayIdPassengerAirplanes(){
        String selectSql = "SELECT id FROM passengerAirplane";

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
