package com.hazardmanager.users.services;

import com.hazardmanager.users.DTO.AreaDto;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.repositories.LocationRepository;
import com.hazardmanager.users.utilis.DistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Location save(Location entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<Location> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Location getById(String id) {
        return this.repository.findOne(id);
    }

    @Override
    public void delete(String id) {
        this.repository.delete(id);
    }

    @Override
    public List<Location> getAllUserLocations(String userId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Location.class);
    }

    @Override
    public Location getLocationByUserIdAndAlias(String userId, String alias) {

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("alias").is(alias));
        List<Location> locations = mongoTemplate.find(query, Location.class);
        if (locations.isEmpty()) {
            return null;
        } else {
            return locations.get(0);
        }
    }

    @Override
    public List<Location> getLocationsWithinEventArea(AreaDto area) {
        List<Location> locations = this.repository.findAll();
        return locations.stream().filter(location -> isInArea(location,area)).collect(Collectors.toList());
    }

    public boolean isInArea(Location location, AreaDto area){
        double distance = DistanceCalculator.distance(location.getLatitude(),location.getLongitude(),area.latitude,area.longitude,"N");
        return distance < area.radius;
    }

}
