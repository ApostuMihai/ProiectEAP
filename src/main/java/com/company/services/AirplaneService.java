package com.company.services;

import com.company.entities.*;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class AirplaneService implements AirplaneInterface, CSVReaderWriter<Airplane>{
    private ArrayList<Airplane> airplanes = new ArrayList<>();

    private AirplaneService() {
        read();
    }
    private static AirplaneService instance;

    public static AirplaneService getInstance(){
        if(instance == null){
            instance = new AirplaneService();
        }
        return instance;
    }

    public ArrayList<Airplane> getAirplanes() {
        ArrayList<Airplane> airplanesCopy = new ArrayList<>();
        airplanesCopy.addAll(this.airplanes);
        return airplanesCopy;
    }

    public Airplane getTicketById(int index){
        Airplane airplane = null;
        for(int i = 0; i < this.airplanes.size(); ++i){
            if(this.airplanes.get(i).getId() == index){
                if(this.airplanes.get(i) instanceof Cargo){
                    airplane = (Cargo) this.airplanes.get(i);
                }
                else if (this.airplanes.get(i) instanceof PassengerAirplane){
                    airplane = (PassengerAirplane) this.airplanes.get(i);
                }
            }
        }
        return airplane;
    }

    public void updateAirplane(int index, Airplane airplane){
        for(int i = 0; i < this.airplanes.size(); ++i){
            if(this.airplanes.get(i).getId() == index){
                this.airplanes.remove(i);
                this.airplanes.add(index, airplane);
                break;
            }
        }
    }

    public void addAirplane(Airplane airplane){
        this.airplanes.add(airplane);
    }

    public void deteleAirplane(int index){
        for(int i = 0; i < this.airplanes.size(); ++i){
            if(this.airplanes.get(i).getId() == index) {
                this.airplanes.remove(i);
                break;
            }
        }
    }

    public Airplane readAirplane(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("0 - Cargo");
        System.out.println("1 - Passenger Airplane");
        int option = scanner.nextInt();
        if(option == 0){
            Cargo cargo = new Cargo();
//            System.out.println("id = ");
//            cargo.setId(scanner.nextInt());

            cargo.setId(getMaxId() + 1);

            System.out.println("name = ");
            cargo.setName(scanner.next());

            System.out.println("speed = ");
            cargo.setSpeed(scanner.nextDouble());

            System.out.println("Years of flying = ");
            cargo.setyearsOfFlying(scanner.nextInt());

            System.out.println("fuel cost = ");
            cargo.setFuelCost(scanner.nextDouble());

            System.out.println("number of materials and quantities = ");
            int nr = scanner.nextInt();
            ArrayList<String> mat = new ArrayList<>();
            ArrayList<Double> qua = new ArrayList<>();
            System.out.println("materials and quantities = ");
            for(int i = 0; i < nr; ++i){
                System.out.println("material = ");
                mat.add(scanner.next());
                System.out.println("quantity = ");
                qua.add(scanner.nextDouble());
            }
            cargo.setMaterials(mat);
            cargo.setQuantity(qua);

            return cargo;
        } else {
            PassengerAirplane passengerAirplane = new PassengerAirplane();
//            System.out.println("id = ");
//            passengerAirplane.setId(scanner.nextInt());

            passengerAirplane.setId(getMaxId() + 1);

            System.out.println("name = ");
            passengerAirplane.setName(scanner.next());

            System.out.println("speed = ");
            passengerAirplane.setSpeed(scanner.nextDouble());

            System.out.println("Years of flying = ");
            passengerAirplane.setyearsOfFlying(scanner.nextInt());

            System.out.println("fuel cost = ");
            passengerAirplane.setFuelCost(scanner.nextDouble());

            System.out.println("number of seats = ");
            passengerAirplane.setNumberOfSeats(scanner.nextInt());

            System.out.println("number of classes = ");
            passengerAirplane.setNumberOfClasses(scanner.nextInt());

            return passengerAirplane;
        }
    }

    @Override
    public String getAntet() {
        return null;
    }

    public int getMaxId() {
        int max = 0;
        for (int i = 0; i < airplanes.size(); ++i) {
            if (airplanes.get(i).getId() > max) {
                max = airplanes.get(i).getId();
            }
        }
        return max;
    }

    @Override
    public Airplane processLine(String line) throws ParseException {
        return null;
    }

    @Override
    public String getFileName() {
        return null;
    }

    @Override
    public String convertObjectToString(Airplane object) {
        String line = null;
        if(object instanceof Cargo cargo)
        {
            //Id,Name,Speed,Years of flying,Fuel Cost
            //1,B1215,150,20,200
            line = cargo.getId() + separator + cargo.getName() + separator + cargo.getSpeed() + separator + cargo.getyearsOfFlying() + separator + cargo.getFuelCost() + "\n";
        } else if (object instanceof PassengerAirplane passengerAirplane) {
            //Id,Name,Speed,Years of flying,Fuel Cost,Number of Seats,Number of Classes
            //6,IR1352,30,30,200,600,1
            line = passengerAirplane.getId() + separator + passengerAirplane.getName() + separator + passengerAirplane.getSpeed() + separator + passengerAirplane.getyearsOfFlying()
                    + separator + passengerAirplane.getFuelCost() + separator + passengerAirplane.getNumberOfSeats()
                    + separator + passengerAirplane.getNumberOfClasses() + "\n";
        }
        return line;
    }

    @Override
    public void initList(List<Airplane> objects) {
        airplanes = new ArrayList<Airplane>(objects);
    }


    public Cargo processCargo(String line){
        // Id,Name,Speed,Years of flying,Fuel Cost
        // 1,B1215,150,20,200
        String[] fields = line.split(separator);
        int id = 0;
        if(Objects.equals(fields[0], "null")){
            id = getMaxId();
        } else {
            try {
                id = Integer.parseInt(fields[0]);
            } catch (Exception e) {
                System.out.println("The id must be an int");
            }
        }
        String name = fields[1];

        double speed = Double.parseDouble(fields[2]);

        int numberOfWagons = Integer.parseInt(fields[3]);

        double fuelCost = Double.parseDouble(fields[4]);

        return new Cargo(id, name, speed, numberOfWagons, fuelCost, new ArrayList<>(), new ArrayList<>());
    }

    public PassengerAirplane processPassengerAirplane(String line){
        //Id,Name,Speed,Years of flying,Fuel Cost,Number of Seats,Number of Classes
        //6,IR1352,30,30,200,600,1
        String[] fields = line.split(separator);
        int id = 0;
        if(Objects.equals(fields[0], "null")){
            id = getMaxId();
        } else {
            try {
                id = Integer.parseInt(fields[0]);
            } catch (Exception e) {
                System.out.println("The id must be an int");
            }
        }
        String name = fields[1];

        double speed = Double.parseDouble(fields[2]);

        int numberOfWagons = Integer.parseInt(fields[3]);

        double fuelCost = Double.parseDouble(fields[4]);

        int numberOfSeats = Integer.parseInt(fields[5]);

        int numberOfClasses = Integer.parseInt(fields[6]);

        return new PassengerAirplane(id, name, speed, numberOfWagons, fuelCost, numberOfSeats, numberOfClasses);
    }

    public List<Cargo> readCargos(){
        String fileName = "src/main/java/com/company/resources/data - Cargo.csv";
        File file = new File(fileName);
        String extraFileName = "src/main/java/com/company/resources/data - Cargo_Info.csv";
        File extraFile = new File(extraFileName);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<Cargo> result;

            try {
                List<Cargo> resultLines = new ArrayList<Cargo>();
                bufferedReader.readLine(); // skip first line
                String currentLine = bufferedReader.readLine();

                while (true) {
                    if (currentLine == null) {
                        result = resultLines;
                        break;
                    }
                    Cargo obj = processCargo(currentLine);
                    resultLines.add(obj);
                    currentLine = bufferedReader.readLine();
                }
                BufferedReader extra = new BufferedReader(new FileReader(extraFile));
                try{
                    extra.readLine();
                    String line = extra.readLine();
                    while (true) {
                        if (line == null) {
                            break;
                        }
                        String[] fields = line.split(separator);
                        int id = Integer.parseInt(fields[0]);
                        Cargo cargo = resultLines.stream()
                                .filter(r -> r.getId() == id)
                                .findAny()
                                .orElse(null);
                        if(cargo != null){
                            if(cargo.getMaterials().size() == 0){
                                List<String> materials = new ArrayList<String>();
                                materials.add(fields[1]);
                                cargo.setMaterials((ArrayList<String>) materials);
                            } else {
                                List<String> objs = cargo.getMaterials();
                                objs.add(fields[1]);
                                cargo.setMaterials((ArrayList<String>) objs);
                            }

                            if(cargo.getQuantity().size() == 0){
                                List<Double> materials = new ArrayList<Double>();
                                materials.add(Double.parseDouble(fields[2]));
                                cargo.setQuantity((ArrayList<Double>) materials);
                            } else {
                                List<Double> objs = cargo.getQuantity();
                                objs.add(Double.parseDouble(fields[2]));
                                cargo.setQuantity((ArrayList<Double>) objs);
                            }

                            int index = 0;
                            for(Cargo element : resultLines){
                                if(element.getId() == cargo.getId()){
                                    resultLines.set(index, cargo);
                                    break;
                                }
                                index += 1;
                            }
                        }

                        line = extra.readLine();
                    }
                } catch (Throwable t){
                    try {
                        extra.close();
                    } catch (Throwable s) {
                        t.addSuppressed(s);
                    }
                    throw t;
                }
                result = resultLines;

            } catch (Throwable anything) {
                try {
                    bufferedReader.close();
                } catch (Throwable something) {
                    anything.addSuppressed(something);
                }
                throw anything;
            }

            //bufferedReader.close();
            //initList(result);
            return result;
        } catch (IOException e1) {
            System.out.println("File not found");
            initList(Collections.emptyList());
            return Collections.emptyList();
        }
    }

    public List<PassengerAirplane> readPassangerAirplane(){
        String fileName = "src/main/java/com/company/resources/data - PassengerAirplane.csv";
        File file = new File(fileName);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<PassengerAirplane> result;

            try {
                List<PassengerAirplane> resultLines = new ArrayList<PassengerAirplane>();
                bufferedReader.readLine(); // skip first line
                String currentLine = bufferedReader.readLine();

                while (true) {
                    if (currentLine == null) {
                        result = resultLines;
                        break;
                    }
                    PassengerAirplane obj = processPassengerAirplane(currentLine);
                    resultLines.add(obj);
                    currentLine = bufferedReader.readLine();
                }
            } catch (Throwable anything) {
                try {
                    bufferedReader.close();
                } catch (Throwable something) {
                    anything.addSuppressed(something);
                }
                throw anything;
            }

            //bufferedReader.close();
            //initList(result);
            return result;
        } catch (FileNotFoundException e1) {
            System.out.println("File not found");
            initList(Collections.emptyList());
            return Collections.emptyList();
        } catch (IOException e2) {
            System.out.println("Cannot read from file");
            initList(Collections.emptyList());
            return Collections.emptyList();
        }
    }


    public List<Airplane> read() {
        List<Cargo> cargos = readCargos();
        List<PassengerAirplane> passengerAirplanes = readPassangerAirplane();
        List<Airplane> airplanes = new ArrayList<>();
        airplanes.addAll(cargos);
        airplanes.addAll(passengerAirplanes);
        initList(airplanes);
        return airplanes;
    }

    @Override
    public void write(List<Airplane> objects){
        String fileName = "src/main/java/com/company/resources/data - Cargo.csv";
        File file = new File(fileName);

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            try{
                String CSVline = "Id,Name,Speed,Years of flying,Fuel Cost\n";
                bufferedWriter.write(CSVline);
            } catch (Throwable anything){
                throw anything;
            }
            if(objects != null){
                for(Airplane airplane : objects){
                    if(airplane instanceof Cargo cargo){
                        try{
                            String CSVline = this.convertObjectToString(cargo);
                            bufferedWriter.write(CSVline);
                        } catch (Throwable anything){
                            throw anything;
                        }
                    }

                }

            }
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        fileName = "src/main/java/com/company/resources/data - Cargo_Info.csv";
        file = new File(fileName);

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            try{
                String CSVline = "Id,Material,Quantity\n";
                bufferedWriter.write(CSVline);
            } catch (Throwable anything){
                throw anything;
            }
            if(objects != null){
                for(Airplane object : objects){
                    if(object instanceof Cargo cargo){
                        List<String> materials = cargo.getMaterials();
                        List<Double> quantity = cargo.getQuantity();
                        int nr = materials.size();
                        while(nr > 0){
                            String CSVline = cargo.getId() + separator;
                            if(materials.size() > 0){
                                CSVline += materials.get(0) + separator;
                                materials.remove(0);
                            } else {
                                CSVline += "null" + separator;
                            }
                            if(quantity.size() > 0){
                                CSVline += quantity.get(0);
                                quantity.remove(0);
                            } else {
                                CSVline += "null";
                            }

                            try{
                                CSVline +=  "\n";
                                bufferedWriter.write(CSVline);
                            } catch (Throwable anything){
                                throw anything;
                            }
                            nr -= 1;
                        }
                    }

                }

            }
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String fileName1 = "src/main/java/com/company/resources/data - PassengerAirplane.csv";
        File file1 = new File(fileName1);

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file1, false));
            try{
                String CSVline = "Id,Name,Speed,Years of flying,Fuel Cost,Number of Seats,Number of Classes\n";
                bufferedWriter.write(CSVline);
            } catch (Throwable anything){
                throw anything;
            }
            if(objects != null){
                for(Airplane airplane : objects){
                    if(airplane instanceof PassengerAirplane passengerAirplane){
                        try{
                            String CSVline = this.convertObjectToString(passengerAirplane);
                            bufferedWriter.write(CSVline);
                        } catch (Throwable anything){
                            throw anything;
                        }
                    }

                }

            }
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
