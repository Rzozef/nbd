package org.pl.model;

import org.pl.exceptions.HardwareException;

import java.util.Objects;
import java.util.UUID;

public class Repair implements EntityInterface {
    public Client getClient() {
        return client;
    }
    public Hardware getHardware() {
        return hardware;
    }

    public Repair(UUID id, boolean archive, Client client, Hardware hardware) {
        this.id = id;
        this.archive = archive;
        this.client = client;
        this.hardware = hardware;
    }

    private UUID id;
    private boolean archive;
    Client client;
    Hardware hardware;

    public double calculateRepairCost() throws HardwareException {
        return getHardware().getHardwareType().calculateRepairCost(getHardware().getPrice());
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public boolean isArchive() {
        return archive;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setClient(Client newClient) {
        this.client = newClient;
    }

    public void setHardware(Hardware newHardware) {
        this.hardware = newHardware;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repair repair = (Repair) o;
        return archive == repair.archive && id.equals(repair.id) && client.equals(repair.client) && hardware.equals(repair.hardware);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, archive, client, hardware);
    }

    @Override
    public String toString() {
        return "Repair{" +
                "id=" + id +
                ", archive=" + archive +
                ", client=" + client +
                ", hardware=" + hardware +
                '}';
    }
}
