package com.spring.microservice.controllers;

import com.spring.microservice.entity.Bike;
import com.spring.microservice.services.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bikes/v1")
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @GetMapping(value = "")
    public ResponseEntity<List<Bike>> getBikes(){

        try {
            List<Bike> bikes = bikeService.getBikes();
            if (bikes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(bikes, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable Integer id){
        try {
            Optional<Bike> bike = bikeService.getBikeById(id);
            return new ResponseEntity<>(bike.get(), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "")
    public ResponseEntity<Bike> createBike(@RequestBody Bike car){
        try {
            Bike newBike = bikeService.createBike(car);
            return new ResponseEntity<>(newBike, HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "")
    public ResponseEntity<Bike> updateBike(@RequestBody Bike car){
        try {
            Bike bikeActualizado = bikeService.updateBike(car);
            return new ResponseEntity<>(bikeActualizado, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteBike(@PathVariable Integer id){
        try {
            boolean deleted = bikeService.deleteBikeById(id);
            return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/byUser/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable int userId){
        List<Bike> bikes = bikeService.byUserId(userId);
        return new ResponseEntity<>(bikes, HttpStatus.OK);
    }
}
