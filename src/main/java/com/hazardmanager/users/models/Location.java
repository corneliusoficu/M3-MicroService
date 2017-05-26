package com.hazardmanager.users.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Document(collection = "Locations")
public class Location {

    private static final double LATITUDE_UPPER_BOUND = 85;
    private static final double LATITUDE_LOWER_BOUND = -85.05115;
    private static final double LONGITUDE_LOWER_BOUND = -180;
    private static final double LONGITUDE_UPPER_BOUND = 180;
    @Id
    private String id;

    @NotBlank
    @Size(max = 50)
    private double latitude;

    @NotBlank
    @Size(max = 50)
    private double longitude;

    @NotBlank
    @Size(max = 50)
    private String alias;

    @Size(max = 50)
    private String userId;

    public Location(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) throws IllegalArgumentException {
        if (isLatitudeValid(latitude)) {
            this.latitude = latitude;
        } else {
            throw new IllegalArgumentException("Latitude cannot be null.");
        }
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) throws IllegalArgumentException {
        if (isLongitudeValid(longitude)) {
            this.longitude = longitude;
        } else {
            throw new IllegalArgumentException("Longitude cannot be null.");
        }
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) throws IllegalArgumentException {
        if (isAliasValid(alias)) {
            this.alias = alias;
        } else {
            throw new IllegalArgumentException("Alias cannot be null.");
        }
    }

    public String getUserId() {
        return userId;
    }

    public boolean isLatitudeValid(double latitude) {
        return !(latitude < LATITUDE_LOWER_BOUND || latitude > LATITUDE_UPPER_BOUND);
    }

    public boolean isLongitudeValid(double longitude) {
        return !(longitude < LONGITUDE_LOWER_BOUND || longitude > LONGITUDE_UPPER_BOUND);
    }

    public boolean isAliasValid(String alias) {
        return !(alias == null || alias.isEmpty());
    }
}
