package org.pl.databaseRepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.pl.databaseModel.ClientAddressMgd;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class ClientMongoRepository extends MongoRepository {
    private MongoCollection<ClientAddressMgd> clientsCollection;
    public ClientMongoRepository() {
        initConnection();
        clientsCollection = getMongoDB().getCollection("clients", ClientAddressMgd.class);
    }

    public boolean add(ClientAddressMgd client) {
        if(contains(client)) {
            return false;
        }
        clientsCollection.insertOne(client);
        return true;
    }

    private boolean contains(ClientAddressMgd client) {
        Bson filter;
        filter = eq("_id", client.getEntityId());

        ArrayList<ClientAddressMgd> output = clientsCollection.find(filter).into(new ArrayList<>());
        return !output.isEmpty();
    }

    public ArrayList<ClientAddressMgd> findAll() {
        return clientsCollection.find().into(new ArrayList<>());
    }

    public ArrayList<ClientAddressMgd> find(int id) {
        Bson filter = eq("_id", id);
        return clientsCollection.find(filter, ClientAddressMgd.class).into(new ArrayList<>());
    }

    public ClientAddressMgd remove(int id) {
        Bson filter = eq("_id", id);
        return clientsCollection.findOneAndDelete(filter);
    }

    public ClientAddressMgd updateArchive(int id, boolean isArchive) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("archive", isArchive);
        clientsCollection.updateOne(filter, setUpdate);
        return clientsCollection.find(filter, ClientAddressMgd.class).first();
    }


}
