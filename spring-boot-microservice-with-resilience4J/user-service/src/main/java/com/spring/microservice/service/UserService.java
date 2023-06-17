package com.spring.microservice.service;

import com.spring.microservice.entity.User;
import com.spring.microservice.models.Bike;
import com.spring.microservice.models.Car;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

     List<User> getAll();

     Optional<User> getUserById(int id);

     User saveUser(User user);

     User updateUser(User user);

     boolean deleteUser(int id);

     List<Car> getCars(int userId);

     List<Bike> getBikes(int userId);



     Car saveCar(int userId, Car car);

     Bike saveBike(int userId, Bike bike);

     Map<String, Object> getUserAndVehicles(int userId);
}
