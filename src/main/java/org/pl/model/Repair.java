package org.pl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.pl.exceptions.HardwareException;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@Access(AccessType.FIELD)
public class Repair extends AbstractEntity implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private boolean archive;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull
    Client client;
    @NotNull
    @JoinColumn(name = "hardware_id")
    @ManyToOne
    Hardware hardware;

    public double calculateRepairCost() throws HardwareException {
        return getHardware().getHardwareType().calculateRepairCost(getHardware().getPrice());
    }

    @Override
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public int getID() {
        return id;
    }
}
