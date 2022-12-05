package org.rhyssaldanha.cyclicapp.model;


import lombok.Value;

import java.util.UUID;

//@Document(collection = "activities")
@Value
public class Activity {
    //    @Id
    //    @Getter(onMethod = @__(@JsonIgnore))
    //    @JsonProperty(value = "_id")
    UUID id;

    //    @Field
    //    @Getter(onMethod = @__(@JsonIgnore))
    //    @JsonProperty
    String name;
}
