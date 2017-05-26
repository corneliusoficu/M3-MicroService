package com.hazardmanager.users.helpers;

import com.hazardmanager.users.DTO.CreatingLocationDto;
import com.hazardmanager.users.DTO.LocationDto;
import com.hazardmanager.users.models.Location;

public class LocationConverter {

    public static LocationDto toDto(Location savedLocation) {
        LocationDto locationDto = new LocationDto();
        locationDto.id = savedLocation.getId();
        locationDto.userId = savedLocation.getUserId();
        locationDto.alias = savedLocation.getAlias();
        locationDto.latitude = savedLocation.getLatitude();
        locationDto.longitude = savedLocation.getLongitude();
        return locationDto;
    }

    public static Location toCreatingModel(CreatingLocationDto creatingLocationDto, String userId) {
        Location location = new Location(userId);
        location.setAlias(creatingLocationDto.alias);
        location.setLatitude(creatingLocationDto.latitude);
        location.setLongitude(creatingLocationDto.longitude);
        return location;
    }
}
