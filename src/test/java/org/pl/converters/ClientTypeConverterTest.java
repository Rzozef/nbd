package org.pl.converters;



import org.junit.jupiter.api.Test;
import org.pl.databaseModel.ClientTypeMgd;
import org.pl.exceptions.ClientException;
import org.pl.model.Basic;
import org.pl.model.ClientType;
import org.pl.model.Premium;
import org.pl.model.Vip;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTypeConverterTest {
    @Test
    void convertClientTypeToClientTypeMgdTest() {
        ClientType basicClientType = new Basic();
        ClientType premiumClientType = new Premium();
        ClientType vipClientType = new Vip();
        assertEquals(ClientTypeConverter.toRepositoryModel(basicClientType).getClass(), ClientTypeMgd.class);
        assertEquals(ClientTypeConverter.toRepositoryModel(basicClientType).getTypeName(), "Basic");
        assertEquals(ClientTypeConverter.toRepositoryModel(premiumClientType).getClass(), ClientTypeMgd.class);
        assertEquals(ClientTypeConverter.toRepositoryModel(premiumClientType).getTypeName(), "Premium");
        assertEquals(ClientTypeConverter.toRepositoryModel(vipClientType).getClass(), ClientTypeMgd.class);
        assertEquals(ClientTypeConverter.toRepositoryModel(vipClientType).getTypeName(), "Vip");
    }

    @Test
    void convertClientTypeMgdToClientTypeTest() throws ClientException {
        ClientTypeMgd basicClientTypeMgd = new ClientTypeMgd(1.0f, 2, "Basic");
        ClientTypeMgd vipClientTypeMgd = new ClientTypeMgd(0.8f, 10, "Vip");
        ClientTypeMgd premiumClientTypeMgd = new ClientTypeMgd(0.9f, 5, "Premium");
        assertEquals(ClientTypeConverter.fromRepositoryModel(basicClientTypeMgd).getClass(), Basic.class);
        assertEquals(ClientTypeConverter.fromRepositoryModel(vipClientTypeMgd).getClass(), Vip.class);
        assertEquals(ClientTypeConverter.fromRepositoryModel(premiumClientTypeMgd).getClass(), Premium.class);
    }
}
