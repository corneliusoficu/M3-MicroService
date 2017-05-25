package com.hazardmanager.users.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Document(collection="Locations")
public class Location {

    @Id
    private String id;

    @NotBlank
    @Size(max=50)
    private double latitude;

    @NotBlank
    @Size(max=50)
    private double longitude;

    @NotBlank
    @Size(max=50)
    private String alias;

    @Size(max=50)
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

    public void setLatitude(double latitude) {
        validateLatitude(latitude);
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        validateLongitude(longitude);
        this.longitude = longitude;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        validateAlias(alias);
        this.alias = alias;
    }

    public String getUserId() {
        return userId;
    }


    public void validateLatitude(double latitude) throws  IllegalArgumentException {
        if (latitude < -85 || latitude > 85) {
            throw new IllegalArgumentException("Latitude value is invalid! Value has to be between -85 and 85. Passed value: " + latitude);
        }
    }

    public void validateLongitude(double longitude) throws  IllegalArgumentException  {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude value is invalid! Value has to be between -180 and 180. Passed value: " + longitude);
        }
    }

    public void validateAlias(String alias) throws  IllegalArgumentException {
        if (alias.equals(null) || alias.equals("")) {
            throw new IllegalArgumentException("Alias value is invalid! Value is null!");
        }
    }
}
