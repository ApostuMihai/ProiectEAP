package com.company.services;

import com.company.entities.*;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class UserService implements UserInterface, CSVReaderWriter<User>{
    private ArrayList<User> users = new ArrayList<>();

    private UserService(){
        read();
    };
    private static UserService instance;

    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> usersCopy = new ArrayList<>();
        usersCopy.addAll(this.users);
        return usersCopy;
    }

    public User getUserById(int index){
        User user = new User();
        for(int i = 0; i < this.users.size(); ++i){
            if(this.users.get(i).getId() == index){
                user = this.users.get(i);
            }
        }
        return user;
    }

    public void updateUser(int index, User user){
        for(int i = 0; i < this.users.size(); ++i){
            if(this.users.get(i).getId() == index){
                this.users.remove(i);
                this.users.add(index, user);
                break;
            }
        }
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void deleteUser(int index){
        for(int i = 0; i < this.users.size(); ++i){
            if(this.users.get(i).getId() == index) {
                this.users.remove(i);
                break;
            }
        }
    }

    public User readUser() throws ParseException {
        User user = new User();
        Scanner scanner = new Scanner(System.in);
//        System.out.println("id = ");
//        user.setId(scanner.nextInt());

        user.setId(getMaxId() + 1);

        System.out.println("name = ");
        user.setName(scanner.next());

        System.out.println("phone number = ");
        user.setPhoneNumber(scanner.next());

        System.out.println("email = ");
        user.setEmail(scanner.next());

        System.out.println("address = ");
        user.setAddress(scanner.next());

        List<Ticket> tickets = new ArrayList<>();
        TicketService ticketService = TicketService.getInstance();
        System.out.println("number of tickets = ");
        int nr = scanner.nextInt();
        System.out.println("tickets = ");
        for(int i = 0; i < nr; ++i){
            Ticket ticket = ticketService.readTicket();
            tickets.add(ticket);
            ticketService.addTicket(ticket);
        }
        user.setTickets(tickets);
        return user;
    }

    @Override
    public String getAntet() {
        return null;
    }

    public int getMaxId() {
        int max = 0;
        for (int i = 0; i < users.size(); ++i) {
            if (users.get(i).getId() > max) {
                max = users.get(i).getId();
            }
        }
        return max;
    }

    @Override
    public User processLine(String line) throws ParseException {
        //Id,Name,Phone Number,Email,Address
        //1,Popescu Ana,745678901,popescuana@gmail.com,Str Florilor Nr 9
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

        String phoneNumber = fields[2];

        String email = fields[3];

        String address = fields[4];

        return new User(id, name, phoneNumber, email, address, Collections.<Ticket>emptyList());
    }

    @Override
    public String getFileName() {
        String path = "src/main/java/com/company/resources/data - User.csv";
        return path;
    }

    @Override
    public String convertObjectToString(User object) {
        String line = object.getId() + separator + object.getName() + separator + object.getPhoneNumber() + separator + object.getEmail() + separator+ object.getAddress() + "\n";
        return line;
    }

    @Override
    public void initList(List<User> objects) {
        users = new ArrayList<>(objects);
    }

    @Override
    public List<User> read(){
        String fileName = this.getFileName();
        File file = new File(fileName);
        String extraFileName = "src/main/java/com/company/resources/data - User_Info.csv";
        File extraFile = new File(extraFileName);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<User> result;

            try {
                List<User> resultLines = new ArrayList<User>();
                bufferedReader.readLine(); // skip first line
                String currentLine = bufferedReader.readLine();

                while (true) {
                    if (currentLine == null) {
                        result = resultLines;
                        break;
                    }
                    User obj = this.processLine(currentLine);
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
                        User user = resultLines.stream()
                                .filter(r -> r.getId() == id)
                                .findAny()
                                .orElse(null);
                        if(user != null){
                            if(user.getTickets().size() == 0){
                                List<Ticket> objs = new ArrayList<Ticket>();
                                int idTicket = -1;
                                try{
                                    idTicket = Integer.parseInt(fields[1]);
                                } catch (Exception e){
                                    System.out.println("The id must be an int");
                                }
                                TicketService ticketService = TicketService.getInstance();
                                for(int i = 0; i < ticketService.getTickets().size(); ++i){
                                    Ticket ticket = ticketService.getTickets().get(i);
                                    if(ticket.getId() == idTicket){
                                        if(ticket instanceof FirstClass){
                                            if(ticket instanceof PrivateLounge) {
                                                PrivateLounge privateLounge = (PrivateLounge) ticket;
                                                objs.add(privateLounge);
                                            } else {
                                                FirstClass firstClass = (FirstClass) ticket;
                                                objs.add(firstClass);
                                            }
                                        } else if(ticket instanceof SecondClass){
                                            SecondClass secondClass = (SecondClass) ticket;
                                            objs.add(secondClass);
                                        }
                                        break;
                                    }
                                }
                                user.setTickets(objs);
                            } else {
                                List<Ticket> objs = user.getTickets();
                                int idTicket = -1;
                                try{
                                    idTicket = Integer.parseInt(fields[1]);
                                } catch (Exception e){
                                    System.out.println("The id must be an int");
                                }
                                TicketService ticketService = TicketService.getInstance();
                                for(int i = 0; i < ticketService.getTickets().size(); ++i){
                                    Ticket ticket = ticketService.getTickets().get(i);
                                    if(ticket.getId() == idTicket){
                                        if(ticket instanceof FirstClass){
                                            if(ticket instanceof PrivateLounge) {
                                                PrivateLounge privateLounge = (PrivateLounge) ticket;
                                                objs.add(privateLounge);
                                            } else {
                                                FirstClass firstClass = (FirstClass) ticket;
                                                objs.add(firstClass);
                                            }
                                        } else if(ticket instanceof SecondClass){
                                            SecondClass secondClass = (SecondClass) ticket;
                                            objs.add(secondClass);
                                        }
                                        break;
                                    }
                                }
                                user.setTickets(objs);
                            }

                            int index = 0;
                            for(User element : resultLines){
                                if(element.getId() == user.getId()){
                                    resultLines.set(index, user);
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
            initList(result);
            return result;
        } catch (FileNotFoundException e1) {
            System.out.println("File not found");
            initList(Collections.emptyList());
            return Collections.emptyList();
        } catch (IOException | ParseException e2) {
            System.out.println("Cannot read from file");
            initList(Collections.emptyList());
            return Collections.emptyList();
        }
    }

    @Override
    public void write(List<User> objects){
        String fileName = this.getFileName();
        File file = new File(fileName);

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            try{
                String CSVline = "Id,Name,Phone Number,Email,Address\n";
                bufferedWriter.write(CSVline);
            } catch (Throwable anything){
                throw anything;
            }
            if(objects != null){
                for(User user : objects){
                    try{
                        String CSVline = this.convertObjectToString(user);
                        bufferedWriter.write(CSVline);
                    } catch (Throwable anything){
                        throw anything;
                    }
                }

            }
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        fileName = "src/main/java/com/company/resources/data - User_Info.csv";
        file = new File(fileName);

        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            try{
                String CSVline = "Id User,Id Ticket\n";
                bufferedWriter.write(CSVline);
            } catch (Throwable anything){
                throw anything;
            }
            if(objects != null){
                for(User object : objects){
                    List<Ticket> objs = object.getTickets();
                    int nr = objs.size();
                    while(nr > 0){
                        String CSVline = object.getId() + separator;
                        if(objs.size() > 0){
                            CSVline += objs.get(0).getId();
                            objs.remove(0);
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
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
