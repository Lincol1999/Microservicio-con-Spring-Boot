package com.spring.microservice.services;

import com.spring.microservice.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> getCars();

    Optional<Car> getCardById(Integer id);

    Car createCar(Car car);

    Car updateCar(Car car);

    boolean deleteCarById(Integer id);

    List<Car> byUserId(Integer userId);

}
