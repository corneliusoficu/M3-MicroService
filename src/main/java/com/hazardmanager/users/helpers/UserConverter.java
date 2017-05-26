package com.hazardmanager.users.helpers;

import com.hazardmanager.users.DTO.CreatingUserDto;
import com.hazardmanager.users.DTO.UserDto;
import com.hazardmanager.users.models.User;
import com.hazardmanager.users.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserConverter {
    @Autowired
    private static PasswordEncoder passwordEncoder;

    public static UserDto toDto(User user) {
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

    public static User toCreatingModel(CreatingUserDto dto) {
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
}
