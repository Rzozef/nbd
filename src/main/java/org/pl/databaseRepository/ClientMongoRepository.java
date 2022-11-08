package org.pl.databaseRepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.pl.databaseModel.AddressMgd;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.ClientTypeMgd;
import org.pl.exceptions.ClientException;

import java.util.ArrayList;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class ClientMongoRepository extends MongoRepository {
    private MongoCollection<ClientAddressMgd> clientsCollection;
    public ClientMongoRepository() {
        initConnection();
        clientsCollection = getMongoDB().getCollection("clients", ClientAddressMgd.class);
    }

    private boolean contains(ClientAddressMgd client) {
        Bson filter;
        filter = eq("_id", client.getEntityId());

        ArrayList<ClientAddressMgd> output = clientsCollection.find(filter).into(new ArrayList<>());
        return !output.isEmpty();
    }

    public boolean add(ClientAddressMgd client) {
        if(contains(client)) {
            return false;
        }
        clientsCollection.insertOne(client);
        return true;
    }

    public ArrayList<ClientAddressMgd> findAll() {
        return clientsCollection.find().into(new ArrayList<>());
    }


    public ArrayList<AddressMgd> findAllAddresses() {
        return clientsCollection.find(AddressMgd.class).into(new ArrayList<>());
    }

    public ClientAddressMgd findClientByClientId(UUID id) {
        Bson filter = eq("_id", id);
        return clientsCollection.find(filter, ClientAddressMgd.class).first();
    }

    public AddressMgd findAddressByClientId(UUID id) {
        Bson filter = eq("_id", id);
        return clientsCollection.find(filter, AddressMgd.class).first();
    }

    public ClientAddressMgd remove(UUID id) {
        Bson filter = eq("_id", id);
        return clientsCollection.findOneAndDelete(filter);
    }

    public void removeAll() {
        clientsCollection.deleteMany(new Document());
    }

    public ClientAddressMgd updateArchive(UUID id, boolean isArchive) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("archive", isArchive);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateBalance(UUID id, double newBalance) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("balance", newBalance);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateFirstName(UUID id, String newFirstName) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("firstName", newFirstName);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateLastName(UUID id, String newLastName) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("lastName", newLastName);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updatePhoneNumber(UUID id, String newPhoneNumber) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("phoneNumber", newPhoneNumber);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateClientType(UUID id, ClientTypeMgd newClientType) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("clientType", newClientType);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateCity(UUID id, String newCity) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("city", newCity);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateNumber(UUID id, String newNumber) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("number", newNumber);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateStreet(UUID id, String newStreet) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("street", newStreet);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public ClientAddressMgd updateRepairs(UUID id) throws ClientException {
        Bson filter = eq("_id", id);
        ClientAddressMgd client = clientsCollection.find(filter).first();
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        if (client.getRepairs() + 1 > client.getClientType().getMaxRepairs()) {
            throw new ClientException(ClientException.CLIENT_MAX_REPAIRS_EXCEEDED);
        }
        Bson setUpdate = Updates.set("repairs", client.getRepairs() + 1);
        return clientsCollection.findOneAndUpdate(filter, setUpdate, options);
    }
}
