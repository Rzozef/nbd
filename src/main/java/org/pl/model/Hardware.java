package org.pl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.pl.model.Condition.FINE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@Access(AccessType.FIELD)
public class Hardware extends AbstractEntity implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private boolean archive;
    @NotNull
    private int price;
    @ManyToOne
    @NotNull
    private HardwareType hardwareType;

    public void repair() {
        hardwareType.setCondition(FINE);
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
