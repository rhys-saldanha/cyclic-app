package org.rhyssaldanha.cyclicapp.repository;

import org.rhyssaldanha.cyclicapp.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ActivityRepositoryMongo extends MongoRepository<Activity, UUID> {
}
