package org.pl.databaseRepository;

import org.junit.jupiter.api.Test;
import org.pl.databaseModel.ClientAddressMgd;
import org.pl.databaseModel.ClientTypeMgd;

public class MongoRepositoryTest {
    @Test
    void testConnectivity() {
        ClientMongoRepository clientMongoRepository = new ClientMongoRepository();
        ClientTypeMgd clientTypeMgd = new ClientTypeMgd((float) 1.2, 3, "basic");
        ClientAddressMgd clientAddressMgd = new ClientAddressMgd(0, false, 200, "Jan", "Kowalski", "12345678901", "123456789", clientTypeMgd, "Lodz", "7", "Zielona");
        clientMongoRepository.initConnection();
        clientMongoRepository.add(clientAddressMgd);
    }
}
