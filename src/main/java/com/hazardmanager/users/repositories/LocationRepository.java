package com.hazardmanager.users.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.hazardmanager.users.models.Location;
public interface LocationRepository extends MongoRepository<Location, String> {
}
