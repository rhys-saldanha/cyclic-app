package org.rhyssaldanha.cyclicapp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rhyssaldanha.cyclicapp.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAndIs;
import static org.hamcrest.MatcherAssert.assertThat;

@ContainerDataMongoTest
class ActivityRepositoryTest extends BaseMongoTest {

    @Autowired
    private ActivityRepositoryMongo activityRepositoryMongo;

    private ActivityRepository activityRepository;

    @BeforeEach
    void setUp() {
        activityRepository = new ActivityRepository(activityRepositoryMongo);
    }

    /*@AfterEach
    void cleanUp() {
        activityRepositoryMongo.deleteAll();
    }*/

    @Test
    void shouldInsertAndFindActivity() {
        var expectedActivity = new Activity(UUID.randomUUID(), "take out bins");
        activityRepository.save(expectedActivity);

        Optional<Activity> actualActivity = activityRepository.findById(expectedActivity.id());

        assertThat(actualActivity, isPresentAndIs(expectedActivity));
    }
}