package org.rhyssaldanha.cyclicapp.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import org.bson.BsonDocumentReader;
import org.bson.BsonDocumentWriter;
import org.bson.UuidRepresentation;
import org.bson.conversions.Bson;
import org.mongojack.JacksonCodecRegistry;
import org.mongojack.MongoJsonMappingException;
import org.mongojack.internal.stream.DBDecoderBsonParser;
import org.mongojack.internal.stream.DBEncoderBsonGenerator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;

import java.io.IOException;
import java.io.InputStream;

//@Configuration
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class JacksonMongoConfiguration {

    //    @Autowired
    public void configureMappingMongoConverter(final ObjectMapper objectMapper,
                                               final MongoDatabaseFactory mongoDatabaseFactory,
                                               final MappingMongoConverter mappingMongoConverter) {
        var jacksonCodecRegistry = new JacksonCodecRegistry(objectMapper, mongoDatabaseFactory.getCodecRegistry(), UuidRepresentation.STANDARD);
        mappingMongoConverter.setCodecRegistryProvider(() -> jacksonCodecRegistry);
    }

    @Bean
    public JacksonMappingMongoConverter jacksonMappingMongoConverter(ObjectMapper objectMapper,
                                                                     MongoDatabaseFactory factory,
                                                                     MongoMappingContext context,
                                                                     MongoCustomConversions conversions) {
        var dbRefResolver = new DefaultDbRefResolver(factory);
        var jacksonMappingMongoConverter = new JacksonMappingMongoConverter(objectMapper, dbRefResolver, context);
        jacksonMappingMongoConverter.setCustomConversions(conversions);
        return jacksonMappingMongoConverter;
    }

    static class JacksonMappingMongoConverter extends MappingMongoConverter {

        private static final InputStream EMPTY_INPUT_STREAM = new EmptyInputStream();
        public static final UuidRepresentation UUID_REPRESENTATION = UuidRepresentation.STANDARD;

        private final ObjectMapper objectMapper;

        public JacksonMappingMongoConverter(ObjectMapper objectMapper,
                                            DbRefResolver dbRefResolver,
                                            MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {
            super(dbRefResolver, mappingContext);
            this.objectMapper = objectMapper;
        }

        @Override
        public <S> S read(Class<S> clazz, Bson bson) {
            try (DBDecoderBsonParser parser = new DBDecoderBsonParser(new IOContext(new BufferRecycler(), EMPTY_INPUT_STREAM, false), 0, new BsonDocumentReader(bson.toBsonDocument()), objectMapper, UUID_REPRESENTATION)) {
                return objectMapper.reader().forType(clazz).readValue(parser);
            } catch (IOException e) {
                throw new RuntimeException("IOException encountered while parsing", e);
            }
        }

        @Override
        public void write(Object obj, Bson bson) {
            try (JsonGenerator generator = new DBEncoderBsonGenerator(new BsonDocumentWriter(bson.toBsonDocument()), UUID_REPRESENTATION)) {
                objectMapper.writeValue(generator, obj);
                generator.flush();
            } catch (JsonMappingException e) {
                throw new MongoJsonMappingException(e);
            } catch (IOException e) {
                throw new MongoException("Error writing object out", e);
            }
        }

        private static class EmptyInputStream extends InputStream {

            @Override
            public int available() {
                return 0;
            }

            public int read() {
                return -1;
            }

        }
    }
}
