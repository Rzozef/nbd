package org.pl.databaseRepository;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

public class MongoRepository {
    public MongoRepository() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://236502:ikVrSaTUOCeZOffjCT3qt94VcVtsEGs8jCJZCxzatILQER270tA6EFRSV57TYTdY8dwIf52dtEMG5Hcp92Odag==@236502.mongo.cosmos.azure.com:10255/?ssl=true&retrywrites=false&replicaSet=globaldb&maxIdleTimeMS=120000&appName=@236502@"));
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(PojoCodecProvider.builder()
                                .automatic(true)
                                .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
                                .build()))).build();
        MongoDatabase database = mongoClient.getDatabase("mongodb");
        MongoCollection<Document> collection = database.getCollection("SampleCollection");
    }
}
