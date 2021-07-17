package com.ebispot.palash.ontologyTools.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MongoDbSpringIntegrationTest {


  @Autowired
  MongoTemplate mongoTemplate;
  private static final String TEST_COLLECTION = "testcollections";

  @DisplayName("save an object and retieve to test if saved properly")
  @Test
  public void testSaveData() {
    // init object
    DBObject objectToSave = BasicDBObjectBuilder.start().add("name", "ontology").get();
    // when
    mongoTemplate.save(objectToSave, TEST_COLLECTION);
    // then
    assertThat(mongoTemplate.findAll(DBObject.class, TEST_COLLECTION)).extracting("name")
        .containsOnly("ontology");
  }
}
