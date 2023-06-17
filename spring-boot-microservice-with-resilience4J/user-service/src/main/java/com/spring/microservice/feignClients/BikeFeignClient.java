package com.spring.microservice.feignClients;

import com.spring.microservice.models.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "bike-service", url = "http://localhost:8003", path = "/api/bikes/v1")
public interface BikeFeignClient {

    @PostMapping(value = "")
    Bike createBike(@RequestBody Bike car);

    @GetMapping(value = "/byUser/{userId}")
    List<Bike> getBikes(@PathVariable Integer userId);
}
