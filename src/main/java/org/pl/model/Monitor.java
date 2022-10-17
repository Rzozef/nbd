package org.pl.model;


import jakarta.persistence.Entity;
import lombok.*;
import org.pl.exceptions.HardwareException;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Monitor extends HardwareType {
    private Condition condition;
    public Monitor(Condition condition) {
        this.condition = condition;
        this.name = "monitor";
    }
    public double calculateRepairCost(int price) throws HardwareException {
        if (price < 0) {
            throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.95 * price;
            case BAD -> 0.9 * price;
            case AVERAGE -> 0.8 * price;
            case DUSTY -> 10;
            case FINE ->
                    throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
