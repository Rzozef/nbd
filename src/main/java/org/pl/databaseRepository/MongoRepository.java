package org.pl.databaseRepository;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.pl.codecs.ConditionCodec;
import org.pl.codecs.HardwareTypeCodecProvider;

import java.util.List;

@Getter
public class MongoRepository {
    private MongoCredential credential = MongoCredential.createCredential("236502", "mongodb", "ikVrSaTUOCeZOffjCT3qt94VcVtsEGs8jCJZCxzatILQER270tA6EFRSV57TYTdY8dwIf52dtEMG5Hcp92Odag==".toCharArray());
    private ConnectionString connectionString = new ConnectionString("mongodb://236502:ikVrSaTUOCeZOffjCT3qt94VcVtsEGs8jCJZCxzatILQER270tA6EFRSV57TYTdY8dwIf52dtEMG5Hcp92Odag==@236502.mongo.cosmos.azure.com:10255/?ssl=true&replicaSet=globaldb&retrywrites=false&maxIdleTimeMS=120000&appName=@236502@");
    private CodecRegistry codecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());
    private MongoClient mongoClient;
    private MongoDatabase mongoDB;

    protected void initConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .codecRegistry(CodecRegistries.fromRegistries(CodecRegistries.fromCodecs(new ConditionCodec()),
                        CodecRegistries.fromProviders(new HardwareTypeCodecProvider(),
                                new UuidCodecProvider(UuidRepresentation.C_SHARP_LEGACY)),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        codecRegistry))
                .build();
        mongoClient = MongoClients.create(settings);
        mongoDB = mongoClient.getDatabase("mongodb");
    }
}
