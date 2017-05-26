package com.hazardmanager.users.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Size;
import java.util.regex.Pattern;

@Document(collection = "Users")
public class User {
    private static final int MAX_FIELD_LENGTH = 50;
    private static final int MIN_FIELD_LENGTH = 2;
    private static final int MAX_PHONE_LENGTH = 15;
    private static final int MAX_PASSWORD_LENGTH = 60;
    private static final int MAX_ROLE_LENGTH = 30;
    @Id
    private String id;

    @NotBlank
    @Size(max = MAX_FIELD_LENGTH, min = MIN_FIELD_LENGTH)
    private String firstName;

    @NotBlank
    @Size(max = MAX_FIELD_LENGTH, min = MIN_FIELD_LENGTH)
    private String lastName;

    @NotBlank
    @Size(max = MAX_FIELD_LENGTH, min = MIN_FIELD_LENGTH)
    private String userName;

    @NotBlank
    @Size(max = MAX_PASSWORD_LENGTH, min = MIN_FIELD_LENGTH)
    private String password;

    @NotBlank
    @Size(max = MAX_ROLE_LENGTH, min = MIN_FIELD_LENGTH)
    private String role;

    @NotBlank
    @Size(max = MAX_FIELD_LENGTH, min = MIN_FIELD_LENGTH)
    private String email;

    @NotBlank
    @Size(max = MAX_PHONE_LENGTH, min = MIN_FIELD_LENGTH)
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

    public boolean isFirstNameValid(String firstName) {
        return firstName.length() < MAX_FIELD_LENGTH && firstName.length() > MIN_FIELD_LENGTH;
    }

    public boolean isLastNameValid(String lastName) {
        return lastName.length() < MAX_FIELD_LENGTH && lastName.length() > MIN_FIELD_LENGTH;
    }

    public boolean isUserNameValid(String userName) {
        return userName.length() < MAX_FIELD_LENGTH && userName.length() > MIN_FIELD_LENGTH;
    }

    public boolean isPasswordValid(String password) {
        return password.length() < MAX_PASSWORD_LENGTH && password.length() > MIN_FIELD_LENGTH;
    }

    public boolean isEmailValid(String email) {
        return email.length() < MAX_FIELD_LENGTH && email.length() > MIN_FIELD_LENGTH && email.contains("@");
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.length() < MAX_PHONE_LENGTH && phoneNumber.length() > MIN_FIELD_LENGTH && Pattern.matches("[0-9]+", phoneNumber);
    }
}
