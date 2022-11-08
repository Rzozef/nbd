package org.pl.databaseRepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.pl.databaseModel.AddressMgd;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.ClientTypeMgd;

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


    public ArrayList<AddressMgd> findAllAddresses() {
        return clientsCollection.find(AddressMgd.class).into(new ArrayList<>());
    }

    public ClientAddressMgd findClientByClientId(int id) {
        Bson filter = eq("_id", id);
        return clientsCollection.find(filter, ClientAddressMgd.class).first();
    }

    public AddressMgd findAddressByClientId(int id) {
        Bson filter = eq("_id", id);
        return clientsCollection.find(filter, AddressMgd.class).first();
    }

    public ClientTypeMgd findClientTypeByClientId(int id) {
        Bson filter = eq("_id", id);
        return clientsCollection.find(filter, ClientTypeMgd.class).first();
    }

    public ClientAddressMgd remove(int id) {
        Bson filter = eq("_id", id);
        return clientsCollection.findOneAndDelete(filter);
    }

    public ClientAddressMgd updateArchive(int id, boolean isArchive) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("archive", isArchive);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateBalance(int id, double newBalance) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("balance", newBalance);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateFirstName(int id, String newFirstName) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("firstName", newFirstName);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateLastName(int id, String newLastName) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("lastName", newLastName);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updatePhoneNumber(int id, String newPhoneNumber) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("firstName", newPhoneNumber);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateClientType(int id, ClientTypeMgd newClientType) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("clientType", newClientType);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateCity(int id, String newCity) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("city", newCity);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateNumber(int id, String newNumber) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("number", newNumber);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateStreet(int id, String newStreet) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("street", newStreet);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public int getNumberOfDocuments() {
        return (int) clientsCollection.countDocuments();
    }
}
