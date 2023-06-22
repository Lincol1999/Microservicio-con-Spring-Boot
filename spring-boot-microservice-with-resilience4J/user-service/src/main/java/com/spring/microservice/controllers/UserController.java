package com.spring.microservice.controllers;

import com.spring.microservice.entity.User;
import com.spring.microservice.models.Bike;
import com.spring.microservice.models.Car;
import com.spring.microservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/users/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    public ResponseEntity<List<User>> getUsers(){
        try {
            List<User> users = userService.getAll();
            if (users.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id){
        try {
            Optional<User> user = userService.getUserById(id);

            return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            User newUser = userService.saveUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }catch (RuntimeException e){
            log.error("Error while creating product");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        try {
            User userActualizado = userService.updateUser(user);
            return new ResponseEntity<>(userActualizado, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id ){
        try {
            boolean deteled = userService.deleteUser(id);

            return new ResponseEntity<>(deteled, HttpStatus.NO_CONTENT);

        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping(value = "/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable int userId){
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        List<Car> cars = userService.getCars(userId);

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping(value = "/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable int userId, @RequestBody Car car){

        if (userService.getUserById(userId).isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Car newCar = userService.saveCar(userId, car);
        return new ResponseEntity<>(newCar, HttpStatus.OK);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
    @GetMapping(value = "/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable int userId){
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        List<Bike> bikes = userService.getBikes(userId);

        return new ResponseEntity<>(bikes, HttpStatus.OK);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
    @PostMapping(value = "/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable int userId, @RequestBody Bike bike){
        if (userService.getUserById(userId).isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Bike newBike = userService.saveBike(userId, bike);
        return new ResponseEntity<>(newBike, HttpStatus.OK);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping(value = "/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable int userId){
        Map<String, Object> result = userService.getUserAndVehicles(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable int userId, RuntimeException e){
        return new ResponseEntity("El usuario " + userId + "tiene los coches en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCar(@PathVariable int userId, @RequestBody Car car, RuntimeException e){
        return new ResponseEntity("El usuario " + userId + "no tiene dinero para los coches", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable int userId, RuntimeException e){
        return new ResponseEntity("El usuario " + userId + "tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveBike(@PathVariable int userId, @RequestBody Bike bike, RuntimeException e){
        return new ResponseEntity("El usuario " + userId + "no tiene dinero para las motos", HttpStatus.OK);
    }


    public ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable int userId, RuntimeException e){
        return new ResponseEntity("El usuario " + userId + "tiene los vehiculos en el taller", HttpStatus.OK);
    }
}
