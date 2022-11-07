package org.pl.converters;

import org.pl.databaseModel.ClientTypeMgd;
import org.pl.exceptions.ClientException;
import org.pl.model.Basic;
import org.pl.model.ClientType;
import org.pl.model.Premium;
import org.pl.model.Vip;

public class ClientTypeConverter {
    public static ClientTypeMgd toRepositoryModel(ClientType clientType) {
        return new ClientTypeMgd(clientType.getFactor(), clientType.getMaxRepairs(), clientType.getTypeName());
    }

    public static ClientType fromRepositoryModel(ClientTypeMgd clientTypeMgd) throws ClientException {
        ClientType clientType;
        if (clientTypeMgd.getTypeName().equals("Basic")) {
            clientType = new Basic();
            return clientType;
        } else if (clientTypeMgd.getTypeName().equals("Premium")) {
            clientType = new Premium();
            return clientType;
        } else if (clientTypeMgd.getTypeName().equals("Vip")) {
            clientType = new Vip();
            return clientType;
        } else throw new ClientException(ClientException.CLIENT_TYPE_EXCEPTION);
    }
}
