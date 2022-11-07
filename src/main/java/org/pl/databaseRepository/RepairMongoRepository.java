package org.pl.databaseRepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.ClientMgd;
import org.pl.databaseModel.HardwareMgd;
import org.pl.databaseModel.RepairEmbeddedMgd;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class RepairMongoRepository extends MongoRepository {
    private MongoCollection<RepairEmbeddedMgd> repairCollection;
    public RepairMongoRepository() {
        initConnection();
        repairCollection = getMongoDB().getCollection("repairs", RepairEmbeddedMgd.class);
    }

    public boolean add(RepairEmbeddedMgd repair) {
        if(contains(repair)) {
            return false;
        }
        repairCollection.insertOne(repair);
        return true;
    }

    private boolean contains(RepairEmbeddedMgd repair) {
        Bson filter;
        filter = eq("_id", repair.getEntityId());

        ArrayList<RepairEmbeddedMgd> output = repairCollection.find(filter).into(new ArrayList<>());
        return !output.isEmpty();
    }

    public ArrayList<RepairEmbeddedMgd> findAll() {
        return repairCollection.find().into(new ArrayList<>());
    }

    public ArrayList<RepairEmbeddedMgd> findAllRepairsByClientId(int clientId) {
        Bson filter = eq("client._id", clientId);
        return repairCollection.find(filter, RepairEmbeddedMgd.class).into(new ArrayList<>());
    }

    public ArrayList<ClientMgd> findAllClients() {
        ArrayList<ClientMgd> clientMgds = repairCollection.aggregate(List.of(Aggregates.replaceRoot("$client")), ClientMgd.class).into(new ArrayList<>());
        return clientMgds;
    }

    public ArrayList<HardwareMgd> findAllHardwares() {
        ArrayList<HardwareMgd> hardwareMgds = repairCollection.aggregate(List.of(Aggregates.replaceRoot("$hardware")), HardwareMgd.class).into(new ArrayList<>());
        return hardwareMgds;
    }

    public ArrayList<RepairEmbeddedMgd> find(int id) {
        Bson filter = eq("_id", id);
        return repairCollection.find(filter, RepairEmbeddedMgd.class).into(new ArrayList<>());
    }

    public RepairEmbeddedMgd remove(int id) {
        Bson filter = eq("_id", id);
        return repairCollection.findOneAndDelete(filter);
    }

    public RepairEmbeddedMgd updateArchive(int id, boolean isArchive) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("archive", isArchive);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return repairCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public RepairEmbeddedMgd updateClient(int repairId, ClientAddressMgd client) {
        Bson filter = eq("_id", repairId);
        Bson setUpdate = Updates.set("client", client);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return repairCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public RepairEmbeddedMgd updateHardware(int repairId, HardwareMgd hardware) {
        Bson filter = eq("_id", repairId);
        Bson setUpdate = Updates.set("hardware", hardware);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return repairCollection.findOneAndUpdate(filter, setUpdate, options);
    }
}
