package org.pl.databaseRepository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.jupiter.api.Test;

public class MongoRepositoryTest {
    @Test
    void testConectivity() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://236502:ikVrSaTUOCeZOffjCT3qt94VcVtsEGs8jCJZCxzatILQER270tA6EFRSV57TYTdY8dwIf52dtEMG5Hcp92Odag==@236502.mongo.cosmos.azure.com:10255/?ssl=true&retrywrites=false&replicaSet=globaldb&maxIdleTimeMS=120000&appName=@236502@"));
        MongoDatabase database = mongoClient.getDatabase("mongodb");
        MongoCollection<Document> collection = database.getCollection("SampleCollection");
        Document document = new Document("fruit", "apple");
        collection.insertOne(document);
        Document queryResult = collection.find(Filters.eq("fruit", "apple")).first();
        assert queryResult != null;
        System.out.println(queryResult.toJson());
    }
}
