package com.company.repository;

import com.company.config.DatabaseConfiguration;
import com.company.entities.Airport;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AirportRepository {
    private static AirportRepository instance;

    private AirportRepository(){}

    public static AirportRepository getInstance(){
        if(instance == null){
            instance = new AirportRepository();
        }
        return instance;
    }

    private Airport maptoObject(ResultSet resultSet){
        try {
            if (resultSet.next()) {
                Date date = null;
                try{
                    date = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(resultSet.getString(3));
                } catch (ParseException e){
                    e.printStackTrace();
                }
                return new Airport(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4), date);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    private List<Airport> mapToList(ResultSet resultSet){
        List<Airport> list = new ArrayList<>();
        try {
            while(true){
                Airport airport = maptoObject(resultSet);
                if (airport == null)
                    return list;
                else list.add(airport);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public List<Airport> getAll(){
        String selectSql = "SELECT * FROM airport";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectSql);
            return mapToList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Airport getMaxId(){
        String selectSql = "SELECT * FROM airport ORDER BY id DESC LIMIT 0, 1";

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
        // int id, String name, String city, String country, Date establishmentDate)
        String createSql = "CREATE TABLE IF NOT EXISTS airport " +
                "(id int PRIMARY KEY AUTO_INCREMENT, " +
                "name varchar(50), " +
                "city varchar(50), " +
                "country varchar(50), " +
                "establishmentDate varchar(50) " +
                ")";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAirport(Airport airport){
        String insertSql = "INSERT INTO airport(name, city, country, establishmentDate) " + "VALUES (?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, airport.getName());
            preparedStatement.setString(2, airport.getCity());
            preparedStatement.setString(3, airport.getCountry());
            Date date = (Date) airport.getEstablishmentDate();
            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
            preparedStatement.setString(4, dateString);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Airport getAirportById(int id) {
        String selectSql = "SELECT * FROM airport WHERE id=?";

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

    public Airport getAirportByName(String name) {
        String selectSql = "SELECT * FROM airport WHERE name=?";

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

    public void updateAirportName(String name, int id){
        String updateSql = "UPDATE airport SET name=? WHERE id=?";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAirport(int id){
        String deleteSql = "DELETE FROM airport WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayAirports(){
        String selectSql = "SELECT * FROM airport";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) { //try with resources
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                System.out.println("Id:" + resultSet.getInt(1));
                System.out.println("Name:" + resultSet.getString(2));
                System.out.println("City:" + resultSet.getString(3));
                System.out.println("Country:" + resultSet.getString(4));
                System.out.println("Establishment Date:" + resultSet.getString(5));
                System.out.println("------------------------------------------------------------- ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayIdAirports(){
        String selectSql = "SELECT id FROM airport";

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
