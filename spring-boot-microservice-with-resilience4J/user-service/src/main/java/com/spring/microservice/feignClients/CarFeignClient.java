package com.spring.microservice.feignClients;

import com.spring.microservice.models.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "car-service", url = "http://localhost:8002", path = "/api/cars/v1")
public interface CarFeignClient {
    @PostMapping(value = "")
    Car createCar(@RequestBody Car car);

    @GetMapping(value = "/byUser/{userId}")
    List<Car> getCars(@PathVariable Integer userId);
}
