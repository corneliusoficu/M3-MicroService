package com.hazardmanager.users.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Size;
import java.util.regex.Pattern;

@Document(collection="Users")
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(max=50)
    private String firstName;

    @NotBlank
    @Size(max=50)
    private String lastName;

    @NotBlank
    @Size(max=50)
    private String userName;

    @NotBlank
    @Size(max=60)
    private String password;

    @NotBlank
    @Size(max=30)
    private String role;

    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 15)
    private String phoneNumber;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void validateFirstName(String text)throws IllegalArgumentException {
        if(text.length()>50)
            throw new IllegalArgumentException("First Name must be under 50 characters long. Passed name has " + text.length()+ " characters");
    }

    public void validateLastName(String text)throws IllegalArgumentException{
        if(text.length()>50)
            throw new IllegalArgumentException("Last Name must be under 50 characters long. Passed name has " + text.length()+ " characters");
    }

    public void validateUserName(String text)throws IllegalArgumentException {
        if(text.length()>50)
            throw new IllegalArgumentException("Username must be under 50 characters long. Passed name has " + text.length()+ " characters");
    }

    public void validatePassword(String text)throws IllegalArgumentException {
        if(text.length()>50)
            throw new IllegalArgumentException("Password must be under 50 characters long. Passed name has " + text.length()+ " characters");
    }

    public void validateEmail(String text)throws IllegalArgumentException {
        if(text.length()>50)
            throw new IllegalArgumentException("Email must be under 50 characters long. Passed name has " + text.length()+ " characters");
        if(!text.contains("@"))
            throw new IllegalArgumentException("Email must be a valid email.");
    }

    public void validatePhoneNumber(String text)throws IllegalArgumentException {
        if(text.length()>50)
            throw new IllegalArgumentException("Phone Number must be under 50 characters long. Passed name has " + text.length()+ " characters");
        if (Pattern.matches("[a-zA-Z]+", text)) {
            throw new IllegalArgumentException("Phone Number must be a valid phone number. It must contain only digits.");
        }
    }
}
