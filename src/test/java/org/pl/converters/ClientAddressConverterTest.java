//package org.pl.converters;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.pl.databaseModel.AddressMgd;
//import org.pl.databaseModel.ClientAddressMgd;
//import org.pl.databaseModel.ClientTypeMgd;
//import org.pl.exceptions.ClientException;
//import org.pl.model.Address;
//import org.pl.model.Basic;
//import org.pl.model.Client;
//
//import java.util.UUID;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ClientAddressConverterTest {
//    private Client client;
//    private Address address;
//
//    @Test
//    void convertClientToClientAddressMgdTest() {
//        address = Address.builder()
//                .street("White")
//                .number("123")
//                .city("Lodz")
//                .build();
//
//        client = Client.builder()
//                .entityId(UUID.randomUUID())
//                .personalId("0")
//                .clientType(new Basic())
//                .phoneNumber("535-535-535")
//                .balance(100)
//                .firstName("John")
//                .lastName("Doe")
//                .address(address)
//                .build();
//        ClientAddressMgd clientAddressMgd = ClientAddressConverter.toRepositoryModel(client);
//        assertEquals(clientAddressMgd.getClass(), ClientAddressMgd.class);
//    }
//    @Test
//    void convertAddresMgdToModelAddress() {
//        AddressMgd addressMgd = new AddressMgd("city", "6", "street");
//        Address address1 = ClientAddressConverter.fromRepositoryModelAddress(addressMgd);
//        assertEquals(address1.getClass(), Address.class);
//    }
//    @Test
//    void convertClientAddressMgdToModelAddress() throws ClientException {
//        ClientTypeMgd clientTypeMgd = new ClientTypeMgd((float) 1.2, 4, "Basic");
//        ClientAddressMgd clientAddressMgd = new ClientAddressMgd(UUID.randomUUID(), false, 200, "Jan", "Kowal", "12345678902", "123456789", clientTypeMgd, 0, "Las", "3", "Lasowa");
//        Client client1 = ClientAddressConverter.fromRepositoryModelClient(clientAddressMgd);
//        assertEquals(client1.getClass(), Client.class);
//    }
//}
