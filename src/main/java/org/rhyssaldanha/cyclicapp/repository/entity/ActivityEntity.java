package org.rhyssaldanha.cyclicapp.repository.entity;

import lombok.Value;
import org.mongojack.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;
import java.util.UUID;

@Document(collection = "activities")
@Value
public class ActivityEntity {
    @Id
    UUID id;

    @Field
    Map<String, Object> value;
}
