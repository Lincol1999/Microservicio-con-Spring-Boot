package com.spring.microservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bike {

    private String brand;

    private String model;

    private boolean active;

    private int userId;
}
