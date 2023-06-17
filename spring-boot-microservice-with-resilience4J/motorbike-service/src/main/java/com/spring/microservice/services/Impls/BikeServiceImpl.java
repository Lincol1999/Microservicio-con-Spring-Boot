package com.spring.microservice.services.Impls;

import com.spring.microservice.entity.Bike;
import com.spring.microservice.repository.BikeRepository;
import com.spring.microservice.services.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    private BikeRepository bikeRepository;
    @Override
    public List<Bike> getBikes() {
        return bikeRepository.findAll();
    }

    @Override
    public Optional<Bike> getBikeById(Integer id) {
        return bikeRepository.findById(id);
    }

    @Override
    public Bike createBike(Bike car) {
        return bikeRepository.save(car);
    }

    @Override
    public Bike updateBike(Bike bike) {
        boolean exists = bikeRepository.existsById(bike.getId());
        return exists ? bikeRepository.save(bike) : null;
    }

    @Override
    public boolean deleteBikeById(Integer id) {
        boolean deleted = false;
        try {
            boolean exists = bikeRepository.existsById(id);
            if (exists) bikeRepository.deleteById(id);
            deleted = true;
        }catch (RuntimeException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    @Override
    public List<Bike> byUserId(Integer userId) {
        return bikeRepository.findByUserId(userId);
    }
}
