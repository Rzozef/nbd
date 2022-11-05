package org.pl.databaseRepository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.pl.databaseModel.HardwareMgd;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class HardwareMongoRepository extends MongoRepository {
    private MongoCollection<HardwareMgd> hardwareCollection;
    public HardwareMongoRepository() {
        initConnection();
        hardwareCollection = getMongoDB().getCollection("hardware", HardwareMgd.class);
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

    public ArrayList<HardwareMgd> find(int id) {
        Bson filter = eq("_id", id);
        return hardwareCollection.find(filter, HardwareMgd.class).into(new ArrayList<>());
    }

    public HardwareMgd remove(int id) {
        Bson filter = eq("_id", id);
        return hardwareCollection.findOneAndDelete(filter);
    }

    public HardwareMgd updateArchive(int id, boolean isArchive) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("archive", isArchive);
        hardwareCollection.updateOne(filter, setUpdate);
        return hardwareCollection.find(filter, HardwareMgd.class).first();
    }

    public HardwareMgd updatePrice(int id, int price) {
        Bson filter = eq("_id", id);
        Bson setUpdate = Updates.set("price", price);
        hardwareCollection.updateOne(filter, setUpdate);
        return hardwareCollection.find(filter, HardwareMgd.class).first();
    }
}
