package org.rhyssaldanha.cyclicapp.repository;

import org.rhyssaldanha.cyclicapp.repository.entity.ActivityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ActivityRepositoryMongo extends MongoRepository<ActivityEntity, UUID> {
}
