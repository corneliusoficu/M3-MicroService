package com.hazardmanager.users.controllers;

import com.hazardmanager.users.DTO.CreatingLocationDto;
import com.hazardmanager.users.DTO.CreatingUserDto;
import com.hazardmanager.users.DTO.LocationDto;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.services.LocationService;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 04-May-17.
 */
@RestController
@RequestMapping("/v1/users")
public class LocationController {
    @Autowired
    private LocationService service;
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/{userId}/locations"}, method = RequestMethod.GET)
    public ResponseEntity<List<LocationDto>> getAllLocationsById(@PathVariable("userId") String userId) {
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"), "hazardmanager");
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        List<Location> locations = mongoTemplate.find(query, Location.class);

        if (locations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<LocationDto> result = new ArrayList<>();
        for (Location location : locations) {
            LocationDto dto = toDto(location);
            result.add(dto);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/{userId}/locations"}, method = RequestMethod.POST)
    public ResponseEntity<LocationDto> addNewLocation(@RequestBody CreatingLocationDto creatinglocationDto, @PathVariable("userId") String userId) {
        Location location = toCreatingModel(creatinglocationDto, userId);
        Location savedLocation = this.service.save(location);
        return new ResponseEntity<>(toDto(savedLocation), HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/{userId}/locations/{alias}"}, method = RequestMethod.PUT)
    public ResponseEntity<LocationDto> modifyLocation(@RequestBody CreatingLocationDto locationDto, @PathVariable("userId") String userId, @PathVariable("alias") String alias) {
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"), "hazardmanager");
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("alias").is(alias));
        List<Location> locations = mongoTemplate.find(query, Location.class);

        Location location = locations.get(0);
        if (location == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        modifyLocationAccordingToDTO(location, locationDto);
        this.service.save(location);
        return new ResponseEntity<>(toDto(location), HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/{userId}/locations/{alias}"}, method = RequestMethod.DELETE)
    public ResponseEntity deleteLocation(@PathVariable("userId") String userId, @PathVariable("alias") String alias){
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"), "hazardmanager");
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("alias").is(alias));
        List<Location> locations = mongoTemplate.find(query, Location.class);

        Location location = locations.get(0);
        if (location == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        this.service.delete(location.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/{userId}/locations"}, method = RequestMethod.DELETE)
    public ResponseEntity deleteLocations(@PathVariable("userId") String userId){
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"), "hazardmanager");
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        List<Location> locations = mongoTemplate.find(query, Location.class);

        for (Location location : locations)
            this.service.delete(location.getId());
        return new ResponseEntity(HttpStatus.OK);
    }



    private LocationDto toDto(Location savedLocation) {
        LocationDto locationDto = new LocationDto();
        locationDto.id = savedLocation.getId();
        locationDto.userId = savedLocation.getUserId();
        locationDto.alias = savedLocation.getAlias();
        locationDto.latitude = savedLocation.getLatitude();
        locationDto.longitude = savedLocation.getLongitude();
        return locationDto;
    }

    private Location toCreatingModel(CreatingLocationDto creatingLocationDto, String userId) {
        Location location = new Location();
        location.setUserId(userId);
        location.setAlias(creatingLocationDto.alias);
        location.setLatitude(creatingLocationDto.latitude);
        location.setLongitude(creatingLocationDto.longitude);
        return location;
    }

    private void modifyLocationAccordingToDTO(Location location, CreatingLocationDto locationDto) {
        if (locationDto.alias != null) {
            MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"), "hazardmanager");
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(location.getUserId()));
            query.addCriteria(Criteria.where("alias").is(locationDto.alias));
            List<Location> locations = mongoTemplate.find(query, Location.class);
            if (locations.isEmpty())
                location.setAlias(locationDto.alias);
        }
        if (locationDto.latitude >= -90 && locationDto.latitude <= 90) {
            location.setLatitude(locationDto.latitude);
        }
        if (locationDto.longitude >= -180 && locationDto.longitude <= 180) {
            location.setLongitude(locationDto.longitude);
        }
    }


}
