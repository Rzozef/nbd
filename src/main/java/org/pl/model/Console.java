package org.pl.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.pl.exceptions.HardwareException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("console")
public class Console extends HardwareType {
    @NotNull
    public Condition condition;

    public double calculateRepairCost(int price) throws HardwareException {
        if (price < 0) {
            throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.9 * price;
            case BAD -> 0.8 * price;
            case AVERAGE -> 0.6 * price;
            case DUSTY -> 100;
            case FINE ->
                    throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
