package com.hazardmanager.users.services;

import com.github.javafaker.Faker;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Andrei on 5/13/2017.
 */

@Service
public class RandomEntityService {

    private Random random = new Random();
    private List<User> generatedUsers = new ArrayList<>();
    private List<Location> generatedLocations = new ArrayList<>();
    private Faker faker = new Faker(random);

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> generateUsers(int count){

            generatedUsers.clear();
            for(int index = 0 ; index < count; index++){
                User user = new User();
                user.setFirstName(faker.name().firstName());
                user.setLastName(faker.name().lastName());
                user.setUserName(user.getFirstName()+"."+user.getLastName());
                user.setPhoneNumber("07"+faker.number().digits(8));
                user.setEmail(user.getFirstName()+"."+user.getLastName()+index+"@hazard.com");
                user.setPassword(passwordEncoder.encode("verybadpassword"));
                user.setRole("ROLE_USER");
                generatedUsers.add(user);

            }
            return generatedUsers;
    }


    public List<Location> generateLocations(String userId, int locationsPerUserCount) {
        generatedLocations.clear();
        for(int index = 0 ; index < locationsPerUserCount ; index ++){
            Location location = new Location(userId);
            double latitude = 44+(46 - 44) * random.nextDouble();
            double longitude = 22 + (28 - 22) * random.nextDouble();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            generatedLocations.add(location);
        }
        return generatedLocations;
    }
}
