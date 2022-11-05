package org.pl.databaseRepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.pl.databaseModel.RepairEmbeddedMgd;

import java.util.ArrayList;

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
}
