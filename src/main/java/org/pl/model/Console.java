package org.pl.model;

import org.pl.exceptions.HardwareException;

import java.util.Objects;

public class Console extends HardwareType {
    public Condition condition;

    @Override
    public Condition getCondition() {
        return condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return condition == console.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition);
    }

    @Override
    public String toString() {
        return "Console{" +
                "condition=" + condition +
                '}';
    }

    @Override
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Console(Condition condition) {
        this.condition = condition;
    }

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
