package org.pl.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pl.exceptions.HardwareException;

@Data
@Entity
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,
        name = "name")
@Access(AccessType.FIELD)
public abstract class HardwareType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private Condition condition;

    public abstract double calculateRepairCost(int price) throws HardwareException;
}
