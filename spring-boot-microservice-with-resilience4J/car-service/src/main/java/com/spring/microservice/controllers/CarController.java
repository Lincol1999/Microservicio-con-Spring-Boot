package com.spring.microservice.controllers;

import com.spring.microservice.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.microservice.services.CarService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars/v1")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping(value = "")
    public ResponseEntity<List<Car>> getCars(){

        try {
            List<Car> cars = carService.getCars();
            if (cars.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cars, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Integer id){
        try {
            Optional<Car> car = carService.getCardById(id);
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Car> createCar(@RequestBody Car car){
        try {
            Car newCar = carService.createCar(car);
            return new ResponseEntity<>(newCar, HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "")
    public ResponseEntity<Car> updateCar(@RequestBody Car car){
        try {
            Car carActualizado = carService.updateCar(car);
            return new ResponseEntity<>(carActualizado, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteCar(@PathVariable Integer id){
        try {
            boolean deleted = carService.deleteCarById(id);
            return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/byUser/{userId}")
    public ResponseEntity<List<Car>> getByUserId(@PathVariable Integer userId){
        List<Car> cars = carService.byUserId(userId);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
}
