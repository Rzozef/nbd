package org.pl.converters;

import org.pl.databaseModel.ClientTypeRedis;
import org.pl.exceptions.ClientException;
import org.pl.model.Basic;
import org.pl.model.ClientType;
import org.pl.model.Premium;
import org.pl.model.Vip;

public class ClientTypeRedisConverter {
    public static ClientTypeRedis toRepositoryModel(ClientType clientType) {
        return new ClientTypeRedis(clientType.getFactor(), clientType.getMaxRepairs(), clientType.getTypeName());
    }

    public static ClientType fromRepositoryModel(ClientTypeRedis clientTypeRedis) throws ClientException {
        ClientType clientType;
        if (clientTypeRedis.getTypeName().equals("Basic")) {
            clientType = new Basic();
            return clientType;
        } else if (clientTypeRedis.getTypeName().equals("Premium")) {
            clientType = new Premium();
            return clientType;
        } else if (clientTypeRedis.getTypeName().equals("Vip")) {
            clientType = new Vip();
            return clientType;
        } else throw new ClientException(ClientException.CLIENT_TYPE_EXCEPTION);
    }
}
