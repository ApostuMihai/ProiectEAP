package com.company.services;



import com.company.config.DatabaseConfiguration;
import com.company.entities.*;
import com.company.repository.CargoRepository;
import com.company.repository.PassengerAirplaneRepository;
import com.company.repository.AirportRepository;
import com.company.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Service {
    private static Service instance;
    private static TicketService ticketService = TicketService.getInstance();
    private static AirplaneService airplaneService = AirplaneService.getInstance();
    private static UserService userService = UserService.getInstance();
    private static RouteService routeService = RouteService.getInstance();
    private static AirportService airportsService = AirportService.getInstance();

    private static AuditService auditService = AuditService.getInstance();

    private static UserRepository userRepository = UserRepository.getInstance();
    private static CargoRepository cargoRepository = CargoRepository.getInstance();
    private static PassengerAirplaneRepository passengerAirplaneRepository = PassengerAirplaneRepository.getInstance();
    private static AirportRepository airportRepository = AirportRepository.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public static Service getInstance(){
        if(instance == null){
            instance = new Service();
        }
        return instance;
    }

    public void writeResults(){
        ticketService.write(ticketService.getTickets());

        airplaneService.write(airplaneService.getAirplanes());

        userService.write(userService.getUsers());

        routeService.write(routeService.getRoutes());

        airportsService.write(airportsService.getAirports());
    }

    public void createTabels(){
        airportRepository.createTable();
        userRepository.createTable();
        passengerAirplaneRepository.createTable();
        cargoRepository.createTable();
    }

    public void databaseMenu() throws ParseException {
        createTabels();
        while(true) {
            System.out.println("0 - Airport");
            System.out.println("1 - Cargo");
            System.out.println("2 - Passenger Airplane");
            System.out.println("3 - User");
            System.out.println("4 - Exit");
            int option;
            while (true) {
                String line = scanner.nextLine();
                try {
                    option = Integer.parseInt(line);
                    if (option >= 0 && option <= 4) {
                        break;
                    } else {
                        System.out.println("Enter a number between 0 and 4");
                    }
                } catch (Exception e) {
                    System.out.println("Enter a number between 0 and 4");
                }
            }
            if(option== 0) {
                databaseAirportMenu();
            } else if(option == 1) {
                databaseCargoMenu();
            } else if(option == 2) {
                databasePassengerAirplaneMenu();
            } else if (option == 3) {
                databaseUserMenu();
            } else {
                break;
            }
        }
        DatabaseConfiguration.closeDatabaseConnection();
    }

    public void databaseAirportMenu() throws ParseException {
        while(true) {
            System.out.println("0 - Insert Airport into Database");
            System.out.println("1 - Get Airport by Id from Database");
            System.out.println("2 - Get Airport by Name from Database");
            System.out.println("3 - Update Airport's Name in Database");
            System.out.println("4 - Delete Airport from Database");
            System.out.println("5 - Display Airports from Database");
            System.out.println("6 - Exit");
            int option;
            while (true) {
                String line = scanner.nextLine();
                try {
                    option = Integer.parseInt(line);
                    if (option >= 0 && option <= 6) {
                        break;
                    } else {
                        System.out.println("Enter a number between 0 and 6");
                    }
                } catch (Exception e) {
                    System.out.println("Enter a number between 0 and 6");
                }
            }
            if (option == 0) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Insert Airport into Database", timeStamp);

                Airport airport = airportsService.readAirport();
                airportRepository.insertAirport(airport);
            } else if (option == 1) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Get Airport by Id from Database", timeStamp);

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                Airport airport = airportRepository.getAirportById(index);
                if (airport != null) {
                    System.out.println(airport.toString());
                } else {
                    System.out.println("Airport doesn't exist");
                }
            } else if(option == 2) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Get Airport by Name from Database", timeStamp);

                System.out.println("name = ");
                String name = scanner.nextLine();
                Airport airport = airportRepository.getAirportByName(name);
                if (airport != null) {
                    System.out.println(airport.toString());
                } else {
                    System.out.println("Airport doesn't exist");
                }
            } else if(option == 3) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Update Airport's Name in Database", timeStamp);

                System.out.println("name = ");
                String name = scanner.nextLine();

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                airportRepository.updateAirportName(name, index);
                Airport airport = airportRepository.getAirportById(index);
                if(airport != null){
                    System.out.println("Done");
                } else {
                    System.out.println("Airport doesn't exist");
                }
            } else if(option == 4) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Delete Airport from Database", timeStamp);

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                Airport airport = airportRepository.getAirportById(index);
                airportRepository.deleteAirport(index);

                if(airport != null){
                    System.out.println("Done");
                } else {
                    System.out.println("Airport doesn't exist");
                }
            } else if(option == 5) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Display Airports from Database", timeStamp);

                airportRepository.displayAirports();
            } else {
                break;
            }
        }
    }

    public void databaseUserMenu() throws ParseException {
        while(true) {
            System.out.println("0 - Insert User into Database");
            System.out.println("1 - Get User by Id from Database");
            System.out.println("2 - Get User by Name from Database");
            System.out.println("3 - Update User's Name in Database");
            System.out.println("4 - Delete User from Database");
            System.out.println("5 - Display Users from Database");
            System.out.println("6 - Exit");
            int option;
            while (true) {
                String line = scanner.nextLine();
                try {
                    option = Integer.parseInt(line);
                    if (option >= 0 && option <= 6) {
                        break;
                    } else {
                        System.out.println("Enter a number between 0 and 6");
                    }
                } catch (Exception e) {
                    System.out.println("Enter a number between 0 and 6");
                }
            }
            if (option == 0) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Insert User into Database", timeStamp);

                User user = userService.readUser();
                userRepository.insertUser(user);
            } else if (option == 1) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Get User by Id from Database", timeStamp);

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                User user = userRepository.getUserById(index);
                if (user != null) {
                    System.out.println(user.toString());
                } else {
                    System.out.println("User doesn't exist");
                }
            } else if(option == 2) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Get User by Name from Database", timeStamp);

                System.out.println("name = ");
                String name = scanner.nextLine();
                User user = userRepository.getUserByName(name);
                if (user != null) {
                    System.out.println(user.toString());
                } else {
                    System.out.println("User doesn't exist");
                }
            } else if(option == 3) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Update User's Name in Database", timeStamp);

                System.out.println("name = ");
                String name = scanner.nextLine();

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                userRepository.updateUserName(name, index);
                User user = userRepository.getUserById(index);
                if(user != null){
                    System.out.println("Done");
                } else {
                    System.out.println("User doesn't exist");
                }
            } else if(option == 4) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Delete User from Database", timeStamp);

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                User user = userRepository.getUserById(index);
                userRepository.deleteUser(index);

                if(user != null){
                    System.out.println("Done");
                } else {
                    System.out.println("User doesn't exist");
                }
            } else if(option == 5) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Display Users from Database", timeStamp);

                userRepository.displayUsers();
            } else {
                break;
            }
        }
    }

    public void databaseCargoMenu(){
        while(true) {
            System.out.println("0 - Insert Cargo into Database");
            System.out.println("1 - Get Cargo by Id from Database");
            System.out.println("2 - Get Cargo by Name from Database");
            System.out.println("3 - Update Cargo's Name in Database");
            System.out.println("4 - Delete Cargo from Database");
            System.out.println("5 - Display Cargos from Database");
            System.out.println("6 - Exit");
            int option;
            while (true) {
                String line = scanner.nextLine();
                try {
                    option = Integer.parseInt(line);
                    if (option >= 0 && option <= 6) {
                        break;
                    } else {
                        System.out.println("Enter a number between 0 and 6");
                    }
                } catch (Exception e) {
                    System.out.println("Enter a number between 0 and 6");
                }
            }
            if (option == 0) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Insert Cargo into Database", timeStamp);

                Cargo cargo = (Cargo) airplaneService.readAirplane();
                cargoRepository.insertCargo(cargo);
            } else if (option == 1) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Get Cargo by Id from Database", timeStamp);

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                Cargo cargo = cargoRepository.getCargoById(index);
                if (cargo != null) {
                    System.out.println(cargo.toString());
                } else {
                    System.out.println("Cargo doesn't exist");
                }
            } else if(option == 2) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Get Cargo by Name from Database", timeStamp);

                System.out.println("name = ");
                String name = scanner.nextLine();
                Cargo cargo = cargoRepository.getCargoByName(name);
                if (cargo != null) {
                    System.out.println(cargo.toString());
                } else {
                    System.out.println("Cargo doesn't exist");
                }
            } else if(option == 3) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Update Cargo's Name in Database", timeStamp);

                System.out.println("name = ");
                String name = scanner.nextLine();

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                cargoRepository.updateCargoName(name, index);
                Cargo cargo = cargoRepository.getCargoById(index);
                if(cargo != null){
                    System.out.println("Done");
                } else {
                    System.out.println("Cargo doesn't exist");
                }
            } else if(option == 4) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Delete Cargo from Database", timeStamp);

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                Cargo cargo = cargoRepository.getCargoById(index);
                cargoRepository.deleteCargo(index);

                if(cargo != null){
                    System.out.println("Done");
                } else {
                    System.out.println("Cargo doesn't exist");
                }
            } else if(option == 5) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Display Cargos from Database", timeStamp);

                cargoRepository.displayCargos();
            } else {
                break;
            }
        }
    }

    public void databasePassengerAirplaneMenu(){
        while(true) {
            System.out.println("0 - Insert Passenger Airplane into Database");
            System.out.println("1 - Get Passenger Airplane by Id from Database");
            System.out.println("2 - Get Passenger Airplane by Name from Database");
            System.out.println("3 - Update Passenger Airplane's Name in Database");
            System.out.println("4 - Delete Passenger Airplane from Database");
            System.out.println("5 - Display Passenger Airplanes from Database");
            System.out.println("6 - Exit");
            int option;
            while (true) {
                String line = scanner.nextLine();
                try {
                    option = Integer.parseInt(line);
                    if (option >= 0 && option <= 6) {
                        break;
                    } else {
                        System.out.println("Enter a number between 0 and 6");
                    }
                } catch (Exception e) {
                    System.out.println("Enter a number between 0 and 6");
                }
            }
            if (option == 0) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Insert Passenger Airplane into Database", timeStamp);

                PassengerAirplane passengerAirplane = (PassengerAirplane) airplaneService.readAirplane();
                passengerAirplaneRepository.insertPassengerAirplane(passengerAirplane);
            } else if (option == 1) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Get Passenger Airplane by Id from Database", timeStamp);

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                PassengerAirplane passengerAirplane = passengerAirplaneRepository.getPassengerAirplaneById(index);
                if (passengerAirplane != null) {
                    System.out.println(passengerAirplane.toString());
                } else {
                    System.out.println("Passenger Airplane doesn't exist");
                }
            } else if(option == 2) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Get Passenger Airplane by Name from Database", timeStamp);

                System.out.println("name = ");
                String name = scanner.nextLine();
                PassengerAirplane passengerAirplane = passengerAirplaneRepository.getPassengerAirplaneByName(name);
                if (passengerAirplane != null) {
                    System.out.println(passengerAirplane.toString());
                } else {
                    System.out.println("Passenger Airplane doesn't exist");
                }
            } else if(option == 3) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Update Passenger Airplane's Name in Database", timeStamp);

                System.out.println("name = ");
                String name = scanner.nextLine();

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                passengerAirplaneRepository.updatePassengerAirplaneName(name, index);
                PassengerAirplane passengerAirplane = passengerAirplaneRepository.getPassengerAirplaneById(index);
                if(passengerAirplane != null){
                    System.out.println("Done");
                } else {
                    System.out.println("Passenger Airplane doesn't exist");
                }
            } else if(option == 4) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Delete Passenger Airplane from Database", timeStamp);

                System.out.println("id = ");
                int index;
                while (true) {
                    String line = scanner.nextLine();
                    try {
                        index = Integer.parseInt(line);
                        break;
                    } catch (Exception e) {
                        System.out.println("Enter a number");
                    }
                }
                PassengerAirplane passengerAirplane = passengerAirplaneRepository.getPassengerAirplaneById(index);
                passengerAirplaneRepository.deletePassengerAirplane(index);

                if(passengerAirplane != null){
                    System.out.println("Done");
                } else {
                    System.out.println("Passenger Airplane doesn't exist");
                }
            } else if(option == 5) {
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeStamp = date.format(new Date());
                auditService.audit("Display Passenger Airplanes from Database", timeStamp);

                passengerAirplaneRepository.displayPassengerAirplanes();
            } else {
                break;
            }
        }
    }

    public void printOptions(){
        System.out.println("0 - Tickets");
        System.out.println("1 - Airplanes");
        System.out.println("2 - Routes");
        System.out.println("3 - Airports");
        System.out.println("4 - Users");
        System.out.println("5 - Exit");
    }

    public void menu() throws ParseException {
        while(true){
            printOptions();
            int option = scanner.nextInt();
            if(option == 0){
                while(true){
                    System.out.println(" 0 - Get All");
                    System.out.println(" 1 - Get By Id");
                    System.out.println(" 2 - Add");
                    System.out.println(" 3 - Update");
                    System.out.println(" 4 - Delete");
                    System.out.println(" 5 - Exit");
                    int opt = scanner.nextInt();
                    if(opt == 0){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Print all Tickets" , timeStamp);
                        for(int i = 0; i < ticketService.getTickets().size(); ++i){
                            System.out.println(ticketService.getTickets().get(i).toString());
                        }
                    } else if(opt == 1){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Print Ticket" , timeStamp);
                        int index = scanner.nextInt();
                        for(int i = 0; i < ticketService.getTickets().size(); ++i){
                            if(ticketService.getTickets().get(i).getId() == index){
                                System.out.println(ticketService.getTickets().get(i).toString());
                                break;
                            }
                        }
                    } else if(opt == 2){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Add Ticket" , timeStamp);
                        Ticket ticket = ticketService.readTicket();
                        ticketService.addTicket(ticket);
                    } else if(opt == 3){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Update Ticket" , timeStamp);
                        int index = scanner.nextInt();
                        for(int i = 0; i < ticketService.getTickets().size(); ++i){
                            if(ticketService.getTickets().get(i).getId() == index){
                                Ticket ticket = ticketService.readTicket();
                                ticketService.updateTicket(index, ticket);
                                break;
                            }
                        }
                    } else if(opt == 4){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Delete Ticket" , timeStamp);
                        int index = scanner.nextInt();
                        ticketService.deteleTicket(index);
                    } else {
                        break;
                    }
                }
            } else if(option == 1){
                while(true){
                    System.out.println(" 0 - Get All");
                    System.out.println(" 1 - Get By Id");
                    System.out.println(" 2 - Add");
                    System.out.println(" 3 - Update");
                    System.out.println(" 4 - Delete");
                    System.out.println(" 5 - Exit");
                    int opt = scanner.nextInt();
                    if(opt == 0){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Print all Airplanes" , timeStamp);
                        for(int i = 0; i < airplaneService.getAirplanes().size(); ++i){
                            System.out.println(airplaneService.getAirplanes().get(i).toString());
                        }
                    } else if(opt == 1){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Print Airplane" , timeStamp);
                        int index = scanner.nextInt();
                        for(int i = 0; i < airplaneService.getAirplanes().size(); ++i){
                            if(airplaneService.getAirplanes().get(i).getId() == index){
                                System.out.println(airplaneService.getAirplanes().get(i).toString());
                                break;
                            }
                        }
                    } else if(opt == 2){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Add Airplane" , timeStamp);
                        Airplane airplane = airplaneService.readAirplane();
                        airplaneService.addAirplane(airplane);
                    } else if(opt == 3){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Update Airplane" , timeStamp);
                        int index = scanner.nextInt();
                        for(int i = 0; i < airplaneService.getAirplanes().size(); ++i){
                            if(airplaneService.getAirplanes().get(i).getId() == index){
                                Airplane airplane = airplaneService.readAirplane();
                                airplaneService.updateAirplane(index, airplane);
                                break;
                            }
                        }
                    } else if(opt == 4){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Delete Airplane" , timeStamp);
                        int index = scanner.nextInt();
                        airplaneService.deteleAirplane(index);
                    } else {
                        break;
                    }
                }

            } else if(option == 2){
                while(true){
                    System.out.println(" 0 - Get All");
                    System.out.println(" 1 - Get By Id");
                    System.out.println(" 2 - Add");
                    System.out.println(" 3 - Update");
                    System.out.println(" 4 - Delete");
                    System.out.println(" 5 - Exit");
                    int opt = scanner.nextInt();
                    if(opt == 0){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Print all Routes" , timeStamp);
                        for(int i = 0; i < routeService.getRoutes().size(); ++i){
                            System.out.println(routeService.getRoutes().get(i).toString());
                        }
                    } else if(opt == 1){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Print Route" , timeStamp);
                        int index = scanner.nextInt();
                        for(int i = 0; i < routeService.getRoutes().size(); ++i){
                            if(routeService.getRoutes().get(i).getId() == index){
                                System.out.println(routeService.getRoutes().get(i).toString());
                                break;
                            }
                        }
                    } else if(opt == 2){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Add Route" , timeStamp);
                        Route route = routeService.readRoute();
                        routeService.addRoute(route);
                    } else if(opt == 3){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Update Route" , timeStamp);
                        int index = scanner.nextInt();
                        for(int i = 0; i < routeService.getRoutes().size(); ++i){
                            if(routeService.getRoutes().get(i).getId() == index){
                                Route route = routeService.readRoute();
                                routeService.updateRoute(index, route);
                                break;
                            }
                        }
                    } else if(opt == 4){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Delete Route" , timeStamp);
                        int index = scanner.nextInt();
                        routeService.deteleRoute(index);
                    } else {
                        break;
                    }
                }
            } else if(option == 3){
                while(true){
                    System.out.println(" 0 - Get All");
                    System.out.println(" 1 - Get By Id");
                    System.out.println(" 2 - Add");
                    System.out.println(" 3 - Update");
                    System.out.println(" 4 - Delete");
                    System.out.println(" 5 - Exit");
                    int opt = scanner.nextInt();
                    if(opt == 0){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Print all Airports" , timeStamp);
                        for(int i = 0; i < airportsService.getAirports().size(); ++i){
                            System.out.println(airportsService.getAirports().get(i).toString());
                        }
                    } else if(opt == 1){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Print Airport" , timeStamp);
                        int index = scanner.nextInt();
                        for(int i = 0; i < airportsService.getAirports().size(); ++i){
                            if(airportsService.getAirports().get(i).getId() == index){
                                System.out.println(airportsService.getAirports().get(i).toString());
                                break;
                            }
                        }
                    } else if(opt == 2){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Add Airport" , timeStamp);
                        Airport airport = airportsService.readAirport();
                        airportsService.addAirport(airport);
                    } else if(opt == 3){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Update Airport" , timeStamp);
                        int index = scanner.nextInt();
                        for(int i = 0; i < airportsService.getAirports().size(); ++i){
                            if(airportsService.getAirports().get(i).getId() == index){
                                Airport airport = airportsService.readAirport();
                                airportsService.updateAirport(index, airport);
                                break;
                            }
                        }
                    } else if(opt == 4){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Delete Airport" , timeStamp);
                        int index = scanner.nextInt();
                        airportsService.deteleAirport(index);
                    } else {
                        break;
                    }
                }
            } else if(option == 4){
                while(true){
                    System.out.println(" 0 - Get All");
                    System.out.println(" 1 - Get By Id");
                    System.out.println(" 2 - Add");
                    System.out.println(" 3 - Update");
                    System.out.println(" 4 - Delete");
                    System.out.println(" 5 - Exit");
                    int opt = scanner.nextInt();
                    if(opt == 0){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Print all Users" , timeStamp);
                        for(int i = 0; i < userService.getUsers().size(); ++i){
                            System.out.println(userService.getUsers().get(i).toString());
                        }
                    } else if(opt == 1){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Print User" , timeStamp);
                        int index = scanner.nextInt();
                        for(int i = 0; i < userService.getUsers().size(); ++i){
                            if(userService.getUsers().get(i).getId() == index){
                                System.out.println(userService.getUsers().get(i).toString());
                                break;
                            }
                        }
                    } else if(opt == 2){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Add User" , timeStamp);
                        User user = userService.readUser();
                        userService.addUser(user);
                    } else if(opt == 3){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Update User" , timeStamp);
                        int index = scanner.nextInt();
                        for(int i = 0; i < userService.getUsers().size(); ++i){
                            if(userService.getUsers().get(i).getId() == index){
                                User user = userService.readUser();
                                userService.updateUser(index, user);
                                break;
                            }
                        }
                    } else if(opt == 4){
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String timeStamp = date.format(new Date());
                        auditService.audit("Delete User" , timeStamp);
                        int index = scanner.nextInt();
                        userService.deleteUser(index);
                    } else {
                        break;
                    }
                }
            } else if(option == 5){
                break;
            }
        }
        writeResults();
    }

}
