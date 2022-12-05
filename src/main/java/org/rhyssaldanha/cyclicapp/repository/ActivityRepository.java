package org.rhyssaldanha.cyclicapp.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import org.rhyssaldanha.cyclicapp.model.Activity;
import org.rhyssaldanha.cyclicapp.repository.entity.ActivityEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityRepository {
    private final ActivityRepositoryMongo activityRepositoryMongo;
    private final ObjectMapper objectMapper;

    public Optional<Activity> findById(final UUID id) {
        return activityRepositoryMongo.findById(id)
                .map(ActivityEntity::getValue)
                .map(valueMap -> objectMapper.convertValue(valueMap, Activity.class));
    }

    public void save(final Activity activity) {
        Map<String, Object> value = objectMapper.convertValue(activity, TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class));
        activityRepositoryMongo.save(new ActivityEntity(activity.getId(), value));
    }
}
