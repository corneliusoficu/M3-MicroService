package com.hazardmanager.users.controllers;

import com.hazardmanager.users.DTO.AreaDto;
import com.hazardmanager.users.DTO.CreatingUserDto;
import com.hazardmanager.users.DTO.LocationDto;
import com.hazardmanager.users.DTO.UserDto;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.models.User;
import com.hazardmanager.users.services.LocationService;
import com.hazardmanager.users.utilis.RandomImportsGenerator;
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

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    RandomImportsGenerator r;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {

//        r.generate();

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
        Set<UserDto> usersInArea = new HashSet<>();
        for (Location location : locations){
            usersInArea.add(toDto(this.service.getById(location.getUserId())));
        }
        if(usersInArea.size() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(usersInArea, HttpStatus.OK);
        }

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> addUser(@RequestBody CreatingUserDto userDto) {
        User user = toCreatingModel(userDto);
        try {
            checkIfValidUser(user);
        }catch (IllegalArgumentException e){
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
        try {
            checkIfValidUser(user);
        }catch (IllegalArgumentException e){
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

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.firstName = user.getFirstName();
        dto.lastName = user.getLastName();
        dto.userName = user.getUserName();
        dto.password = user.getPassword();
        dto.email = user.getEmail();
        dto.phoneNumber = user.getPhoneNumber();
        dto.role = user.getRole();
        return dto;
    }

    private User toCreatingModel(CreatingUserDto dto) {
        User user = new User();
        user.setFirstName(dto.firstName);
        user.setLastName(dto.lastName);
        user.setUserName(dto.userName);

        user.setPassword(passwordEncoder.encode(dto.password));
        user.setPhoneNumber(dto.phoneNumber);
        user.setEmail(dto.email);
        user.setRole("ROLE_USER");
        return user;
    }

    private void checkIfValidUser(User user)
    {

            user.validateFirstName(user.getFirstName());
            user.validateLastName(user.getLastName());
            user.validateUserName(user.getUserName());
            user.validateEmail(user.getEmail());
            user.validatePhoneNumber(user.getPhoneNumber());

    }
}
