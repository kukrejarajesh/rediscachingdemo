package com.rajesh.cachingpoc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rajesh.cachingpoc.model.WeatherData;
import com.rajesh.cachingpoc.service.ApiService;

import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/redis")
public class WeatherController {
	
	private static final String STRING_KEY_PREFIX = "redi2read:strings:";
	private final ApiService apiService;
	
	@Autowired
	private RedisTemplate<String, String> template;
	
	public WeatherController(ApiService apiService) {
        this.apiService = apiService;
    }
	
	@PostMapping("/strings")
	@ResponseStatus(HttpStatus.CREATED)
	public Map.Entry<String, String> setString(@RequestBody Map.Entry<String, String> kvp) {
		
		 template.opsForValue().set(STRING_KEY_PREFIX + kvp.getKey(), kvp.getValue());

	  return kvp;
	}
	
	@GetMapping("/weather")
	public ResponseEntity<?>  getWeather(@RequestParam String location) {
		try {
			if(location==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Location need to be provided as parameter " );
		return new ResponseEntity<>(apiService.callExternalApi(location), HttpStatus.OK);
		}
		
		catch (JsonProcessingException e) {
            // Log the error and return a meaningful response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Invalid JSON data provided: " + e.getMessage());
        }
		
	}
}
