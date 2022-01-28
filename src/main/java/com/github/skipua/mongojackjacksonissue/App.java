package com.github.skipua.mongojackjacksonissue;

import java.util.concurrent.TimeUnit;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.mongojack.JacksonMongoCollection;

public class App {


  public static void main(String[] args) {
    var coll = JacksonMongoCollection.builder()
        .build(MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(b -> b.serverSelectionTimeout(1, TimeUnit.MILLISECONDS))
            .build()), "test", "whatever", SampleEntity.class, UuidRepresentation.UNSPECIFIED);

    var entity = new SampleEntity();
    entity.setSomeProperty("abc");
    entity.setSomeWriteOnlyProperty("bcd");
    try {
      coll.save(entity);
    } catch (NullPointerException e) {
      System.out.println("Reproduced error: " + e);
      e.printStackTrace();
    } catch (MongoTimeoutException e) {
      // if mongo timeout is thrown then bug was not reproduced
      System.out.println("Error not reproduced");
    }

    System.out.println(entity);
  }
}
