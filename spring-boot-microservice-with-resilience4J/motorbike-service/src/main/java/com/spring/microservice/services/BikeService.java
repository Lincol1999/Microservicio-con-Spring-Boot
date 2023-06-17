package com.spring.microservice.services;

import com.spring.microservice.entity.Bike;

import java.util.List;
import java.util.Optional;

public interface BikeService {

    List<Bike> getBikes();

    Optional<Bike> getBikeById(Integer id);

    Bike createBike(Bike bike);

    Bike updateBike(Bike bike);

    boolean deleteBikeById(Integer id);

    List<Bike> byUserId(Integer userId);

}
