package com.spring.microservice.service.Impls;

import com.spring.microservice.entity.User;
import com.spring.microservice.feignClients.BikeFeignClient;
import com.spring.microservice.feignClients.CarFeignClient;
import com.spring.microservice.models.Bike;
import com.spring.microservice.models.Car;
import com.spring.microservice.repository.UserRepository;
import com.spring.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private BikeFeignClient bikeFeignClient;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        boolean exist = userRepository.existsById(user.getId());
        return exist ? userRepository.save(user) : null;

    }

    @Override
    public boolean deleteUser(int id) {

        boolean deleted = false;

        try {
            boolean exist = userRepository.existsById(id);
            if (exist) userRepository.deleteById(id);
            deleted = true;

        }catch (RuntimeException e){
            e.printStackTrace();
        }

        return deleted;
    }

    @Override
    public List<Car> getCars(int userId){
        List<Car> cars = restTemplate.getForObject("http://localhost:8002/api/cars/v1/byUser/" + userId, List.class);
        return cars;
    }

    @Override
    public List<Bike> getBikes(int userId){
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/api/bikes/v1/byUser/" + userId, List.class);
        return bikes;
    }

    @Override
    public Car saveCar(int userId, Car car) {
        car.setUserId(userId);
        return carFeignClient.createCar(car);
    }

    @Override
    public Bike saveBike(int userId, Bike bike) {
        bike.setUserId(userId);
        return bikeFeignClient.createBike(bike);
    }

    @Override
    public Map<String, Object> getUserAndVehicles(int userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            result.put("Mensaje", "no existe el usuario");
            return result;
        }
        result.put("User", user);
        List<Car> cars = carFeignClient.getCars(userId);
        if (cars.isEmpty()){
            result.put("Cars", "ese user no tiene coches");
        }else {
            result.put("Cars", cars);
        }

        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if (bikes.isEmpty()){
            result.put("Bikes", "ese user no tiene motos");
        }else{
            result.put("Bikes", bikes);
        }
        return result;
    }
}
