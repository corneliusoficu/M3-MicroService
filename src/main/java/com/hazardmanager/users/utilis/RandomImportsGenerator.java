package com.hazardmanager.users.utilis;

import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.models.User;
import com.hazardmanager.users.services.LocationService;
import com.hazardmanager.users.services.LocationServiceImpl;
import com.hazardmanager.users.services.UserService;
import com.hazardmanager.users.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Random;

/**
 * Created by Andrei on 5/13/2017.
 */
@Service
public class RandomImportsGenerator {

    @Autowired
    UserService userService;

    @Autowired
    LocationService locationService;

    User user;
    Location location;
    Random random = new Random();
    BufferedReader reader;
    public void generate(){
        try {
            reader = new BufferedReader(new FileReader("names.txt"));
            String line;
            String[] name;
            while((line = reader.readLine())!= null){
                name = line.split(" ");
                user = new User();
                user.setFirstName(name[0]);
                user.setLastName(name[1]);
                user.setEmail(name[0]+"."+name[1]+"@hazard.com");
                user.setPhoneNumber("0743345009");
                user.setRole("ROLE_USER");
                User saved = userService.save(user);
                for (int i = 0; i < 5 ; i++) {
                    double latitude = 44+ (46 - 44) * random.nextDouble();
                    double longitude = 22+ (28 - 22) * random.nextDouble();;
                    location = new Location(saved.getId());
                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
                    locationService.save(location);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
