package com.hazardmanager.users.controllers;

import com.hazardmanager.users.DTO.AreaDto;
import com.hazardmanager.users.DTO.CreatingUserDto;
import com.hazardmanager.users.DTO.UserDto;
import com.hazardmanager.users.helpers.UserConverter;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.models.User;
import com.hazardmanager.users.services.LocationService;
import com.hazardmanager.users.services.RandomEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.hazardmanager.users.services.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hazardmanager.users.helpers.UserConverter.toCreatingModel;
import static com.hazardmanager.users.helpers.UserConverter.toDto;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private LocationService locationService;

    @Autowired
    private RandomEntityService randomEntityService;    //For production only


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<User> users = this.service.getAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<UserDto> result = new ArrayList<>();

        for (User user : users) {
            UserDto dto = toDto(user);
            result.add(dto);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{latitude}/{longitude}/{radius}"}, method = RequestMethod.GET)
    public ResponseEntity<Set<UserDto>> getAllUsersInArea(@PathVariable("latitude") double latitude, @PathVariable("longitude") double longitude, @PathVariable("radius") double radius) {
        AreaDto area = new AreaDto();
        area.longitude = longitude;
        area.latitude = latitude;
        area.radius = radius;
        List<Location> locations = this.locationService.getLocationsWithinEventArea(area);
        Set<User> usersInArea = new HashSet<>();
        for (Location location : locations){
            usersInArea.add(this.service.getById(location.getUserId()));
        }
        if(usersInArea.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            Set<UserDto> userDtos = usersInArea.stream().map(UserConverter::toDto).collect(Collectors.toSet());
            return new ResponseEntity<Set<UserDto>>(userDtos, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/generate/{usersCount}/{locationsPerUserCount}" ,method = RequestMethod.POST)
    public ResponseEntity<List<UserDto>> generateUsers(@PathVariable("usersCount") int usersCount, @PathVariable("locationsPerUserCount") int locationsPerUserCount){

        List<User> randomGeneratedUsers = randomEntityService.generateUsers(usersCount);

        List<UserDto> userDtos = new ArrayList<>();

        List<Location> locations = new ArrayList<>();

        randomGeneratedUsers.forEach(e->{
            this.service.save(e);
            userDtos.add(UserConverter.toDto(e));
            locations.addAll(randomEntityService.generateLocations(e.getId(),locationsPerUserCount));
        });

        locations.forEach(this.locationService::save);


        return new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> addUser(@RequestBody CreatingUserDto userDto) {
        User user = toCreatingModel(userDto);
        if (!isUserValid(user)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User savedUser = this.service.save(user);
        return new ResponseEntity<>(toDto(savedUser), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id) {
        User user = this.service.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(toDto(user), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> modifyUser(@RequestBody CreatingUserDto userDto, @PathVariable("id") String id) {
        User user = this.service.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        modifyUserAccordingToDTO(user, userDto);
        if (isUserValid(user)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.service.save(user);
        return new ResponseEntity<>(toDto(user), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") String id){
        User user = this.service.getById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        this.service.delete(user.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    private void modifyUserAccordingToDTO(User user, CreatingUserDto userDto) {
        if (userDto.firstName != null) {
            user.setFirstName(userDto.firstName);
        }
        if (userDto.lastName != null) {
            user.setLastName(userDto.lastName);
        }
        if (userDto.userName != null) {
            user.setUserName(userDto.userName);
        }
        if (userDto.password != null) {
            user.setPassword(userDto.password);
        }
        if (userDto.email != null) {
            user.setEmail(userDto.email);
        }
        if (userDto.phoneNumber != null) {
            user.setPhoneNumber(userDto.phoneNumber);
        }
    }

    private boolean isUserValid(User user) {
        return user.isFirstNameValid(user.getFirstName()) &&
            user.isLastNameValid(user.getLastName()) &&
            user.isUserNameValid(user.getUserName()) &&
            user.isEmailValid(user.getEmail()) &&
            user.isPhoneNumberValid(user.getPhoneNumber());
    }
}
