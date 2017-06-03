package com.hazardmanager.users.controllers;

import com.hazardmanager.users.DTO.AreaDto;
import com.hazardmanager.users.DTO.CreatingLocationDto;
import com.hazardmanager.users.DTO.LocationDto;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.hazardmanager.users.helpers.LocationConverter.*;

@RestController
@RequestMapping("/v1/users")
public class LocationController {

    @Autowired
    private LocationService service;

    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = {"/{userId}/locations"}, method = RequestMethod.GET)
    public ResponseEntity<List<LocationDto>> getAllLocationsById(@PathVariable("userId") String userId) {

        List<Location> locationModels = this.service.getAllUserLocations(userId);

        if (locationModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<LocationDto> result = new ArrayList<>();
        for (Location location : locationModels) {
            LocationDto dto = toDto(location);
            result.add(dto);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = {"/locations"}, method = RequestMethod.GET)
    public ResponseEntity<List<LocationDto>> getAllLocationsInArea(@RequestParam("latitude") double latitude, @RequestParam double longitude, @RequestParam double radius) {
        AreaDto area = new AreaDto();
        area.longitude = longitude;
        area.latitude = latitude;
        area.radius = radius;
        List<Location> locationModels = this.service.getLocationsWithinEventArea(area);
        if (locationModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<LocationDto> result = new ArrayList<>();
        for (Location location : locationModels) {
            LocationDto dto = toDto(location);
            result.add(dto);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = {"/{userId}/locations"}, method = RequestMethod.POST)
    public ResponseEntity<LocationDto> addNewLocation(@RequestBody CreatingLocationDto creatinglocationDto, @PathVariable("userId") String userId) {
        Location possibleLocation = this.service.getLocationByUserIdAndAlias(userId, creatinglocationDto.alias);

        if (possibleLocation != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Location location = toCreatingModel(creatinglocationDto, userId);

        if (!isLocationValid(location)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Location savedLocation = this.service.save(location);
        return new ResponseEntity<>(toDto(savedLocation), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = {"/{userId}/locations/{alias}"}, method = RequestMethod.PUT)
    public ResponseEntity<LocationDto> modifyLocation(@RequestBody CreatingLocationDto locationDto, @PathVariable("userId") String userId, @PathVariable("alias") String alias) {

        Location location = this.service.getLocationByUserIdAndAlias(userId, alias);

        if (location == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (!isLocationValid(location)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!alias.equals(locationDto.alias) && this.service.getLocationByUserIdAndAlias(userId, alias) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            modifyLocationAccordingToDTO(location, locationDto);
            this.service.save(location);
            return new ResponseEntity<>(toDto(location), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = {"/{userId}/locations/{alias}"}, method = RequestMethod.DELETE)
    public ResponseEntity deleteLocation(@PathVariable("userId") String userId, @PathVariable("alias") String alias) {
        Location location = this.service.getLocationByUserIdAndAlias(userId, alias);
        if (location == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        this.service.delete(location.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @RequestMapping(value = {"/{userId}/locations"}, method = RequestMethod.DELETE)
    public ResponseEntity deleteLocations(@PathVariable("userId") String userId) {

        List<Location> locations = this.service.getAllUserLocations(userId);

        if (locations.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        for (Location location : locations) {
            this.service.delete(location.getId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    private void modifyLocationAccordingToDTO(Location location, CreatingLocationDto locationDto) {
        location.setAlias(locationDto.alias);
        location.setLatitude(locationDto.latitude);
        location.setLongitude(locationDto.longitude);
    }

    private boolean isLocationValid(Location location) {
        return location.isLatitudeValid(location.getLatitude()) &&
                location.isLongitudeValid(location.getLongitude()) &&
                location.isAliasValid(location.getAlias());
    }
}
