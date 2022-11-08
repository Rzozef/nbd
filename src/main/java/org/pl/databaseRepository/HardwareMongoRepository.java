package org.pl.databaseRepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.pl.databaseModel.*;

import java.util.ArrayList;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class HardwareMongoRepository extends MongoRepository {
    private MongoCollection<HardwareMgd> hardwareCollection;
    public HardwareMongoRepository() {
        initConnection();
        hardwareCollection = getMongoDB().getCollection("hardwares", HardwareMgd.class);
    }

    public boolean add(HardwareMgd hardware) {
        if(contains(hardware)) {
            return false;
        }
        hardwareCollection.insertOne(hardware);
        return true;
    }

    private boolean contains(HardwareMgd hardware) {
        Bson filter;
        filter = eq("_id", hardware.getEntityId());

        ArrayList<HardwareMgd> output = hardwareCollection.find(filter).into(new ArrayList<>());
        return !output.isEmpty();
    }

    public ArrayList<HardwareMgd> findAll() {
        return hardwareCollection.find().into(new ArrayList<>());
    }

    public ArrayList<ComputerMgd> findAllComputers() {
        Bson filter;
        filter = eq("hardwareType._clazz", "computer");
        return hardwareCollection.find(filter, ComputerMgd.class).into(new ArrayList<>());
    }

    public ArrayList<ConsoleMgd> findAllConsoles() {
        Bson filter;
        filter = eq("hardwareType._clazz", "console");
        return hardwareCollection.find(filter, ConsoleMgd.class).into(new ArrayList<>());
    }

    public ArrayList<PhoneMgd> findAllPhones() {
        Bson filter;
        filter = eq("hardwareType._clazz", "phone");
        return hardwareCollection.find(filter, PhoneMgd.class).into(new ArrayList<>());
    }

    public ArrayList<MonitorMgd> findAllMonitors() {
        Bson filter;
        filter = eq("hardwareType._clazz", "monitor");
        return hardwareCollection.find(filter, MonitorMgd.class).into(new ArrayList<>());
    }


    public HardwareMgd find(UUID id) {
        Bson filter = eq("_id", id);
        return hardwareCollection.find(filter, HardwareMgd.class).first();
    }

    public HardwareMgd remove(UUID id) {
        Bson filter = eq("_id", id);
        return hardwareCollection.findOneAndDelete(filter);
    }

    public void removeAll() {
        hardwareCollection.deleteMany(new Document());
    }

    public HardwareMgd updateArchive(UUID id, boolean isArchive) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("archive", isArchive);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return hardwareCollection.findOneAndUpdate(filter, setUpdate, options);
    }

    public HardwareMgd updatePrice(UUID id, int price) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("price", price);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions();
        options.returnDocument(ReturnDocument.AFTER);
        return hardwareCollection.findOneAndUpdate(filter, setUpdate, options);
    }
}
