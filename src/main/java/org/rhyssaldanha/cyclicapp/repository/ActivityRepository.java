package org.rhyssaldanha.cyclicapp.repository;

import lombok.RequiredArgsConstructor;
import org.rhyssaldanha.cyclicapp.model.Activity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityRepository {
    private final ActivityRepositoryMongo activityRepositoryMongo;

    public Optional<Activity> findById(final UUID id) {
        return activityRepositoryMongo.findById(id);
    }

    public void save(final Activity activity) {
        activityRepositoryMongo.save(activity);
    }
}
