package com.rajesh.cachingpoc.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class WeatherData implements Serializable {
    private String dateTime;
    private String description;
    private String address;
    private double temperature;
}