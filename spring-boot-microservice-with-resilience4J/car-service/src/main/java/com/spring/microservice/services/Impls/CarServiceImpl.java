package com.spring.microservice.services.Impls;

import com.spring.microservice.entity.Car;
import com.spring.microservice.repository.CarRepository;
import com.spring.microservice.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;
    @Override
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCardById(Integer id) {
        return carRepository.findById(id);
    }

    @Override
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Car car) {
        boolean exists = carRepository.existsById(car.getId());
        return exists ? carRepository.save(car) : null;
    }

    @Override
    public boolean deleteCarById(Integer id) {
        boolean deleted = false;
        try {
            boolean exists = carRepository.existsById(id);
            if (exists) carRepository.deleteById(id);
            deleted = true;
        }catch (RuntimeException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    @Override
    public List<Car> byUserId(Integer userId) {
        return carRepository.findByUserId(userId);
    }
}
