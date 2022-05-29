package com.company.services;

import com.company.entities.User;

import java.text.ParseException;
import java.util.ArrayList;

public interface UserInterface {
    public ArrayList<User> getUsers();

    public User getUserById(int index);

    public void updateUser(int index, User user);

    public void addUser(User user);

    public void deleteUser(int index);

    public User readUser() throws ParseException;
}
