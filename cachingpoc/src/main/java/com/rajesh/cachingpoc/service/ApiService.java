package com.rajesh.cachingpoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajesh.cachingpoc.model.WeatherData;

@Service
public class ApiService {
    
    private final RestTemplate restTemplate;

    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "weather_single", key = "#location")
    public WeatherData callExternalApi(String location) throws JsonProcessingException{
        //String url = "https://api.example.com/data"; // Replace with your API endpoint
    	String API_KEY="HYDTFKPHURE5EF62XTFWDZUGT";
        String url= "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/"
        		+ location + "?unitGroup=us&key=" + API_KEY +"&contentType=json" ;
        	//	HYDTFKPHURE5EF62XTFWDZUGT&contentType=json";
        
        
        String response = restTemplate.getForObject(url, String.class);
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        JsonNode rootNode =objectMapper.readTree(response);
        
        var days = rootNode.path("days");
        var today = days.get(0);
        
        String address = rootNode.get("address").asText();
        String todayDate = today.get("datetime").asText();
        String todayDescription = today.get("description").asText();
        double temperature = today.get("temp").asDouble();
        
        var todayWeather = new WeatherData(todayDate, todayDescription, address, temperature);
        return todayWeather;
    }
}
