package org.pl.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.pl.exceptions.HardwareException;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Access(AccessType.FIELD)
public abstract class HardwareType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private Condition condition;
    @NotNull
    protected String name;

    public abstract double calculateRepairCost(int price) throws HardwareException;
}
