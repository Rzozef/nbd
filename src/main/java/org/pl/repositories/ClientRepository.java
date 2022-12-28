package org.pl.repositories;

import org.pl.model.Client;
import java.util.ArrayList;

public class ClientRepository extends Repository<Client> {
    public ClientRepository(ArrayList<Client> elements) {
        super(elements);
    }
}