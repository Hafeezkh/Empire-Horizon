package com.EmpireHotel.Hotel.Service;

import com.EmpireHotel.Hotel.model.User;

import java.util.List;


public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}