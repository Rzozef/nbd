package org.pl.cassandra.converters;

import org.pl.cassandra.model.ClientCassandra;
import org.pl.exceptions.ClientException;
import org.pl.model.Basic;
import org.pl.model.ClientType;
import org.pl.model.Premium;
import org.pl.model.Vip;

public class ClientTypeConverter {
    public static ClientType fromRepositoryModel(ClientCassandra clientCassandra) throws ClientException {
        ClientType clientType;
        switch (clientCassandra.getTypeName()) {
            case "Basic" -> {
                clientType = new Basic();
                return clientType;
            }
            case "Premium" -> {
                clientType = new Premium();
                return clientType;
            }
            case "Vip" -> {
                clientType = new Vip();
                return clientType;
            }
            default -> throw new ClientException(ClientException.CLIENT_TYPE_EXCEPTION);
        }
    }
}
