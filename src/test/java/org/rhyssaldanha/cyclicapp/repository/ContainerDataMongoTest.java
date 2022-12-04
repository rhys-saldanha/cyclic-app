package org.rhyssaldanha.cyclicapp.repository;

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
@Inherited
@Retention(RUNTIME)
public @interface ContainerDataMongoTest {
}
