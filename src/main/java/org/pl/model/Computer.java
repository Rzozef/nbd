package org.pl.model;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import org.pl.exceptions.HardwareException;

import java.util.Objects;

@Entity
@PropertyStrategy(mutable = false)
public class Computer extends HardwareType {
    private Condition condition;

    public Computer(Condition condition) {
        this.condition = condition;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
    @Override
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Computer computer = (Computer) o;
        return condition == computer.condition;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "condition=" + condition +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition);
    }

    public double calculateRepairCost(int price) throws HardwareException {
        if (price < 0) {
            throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_ZERO_EXCEPTION);
        }
        return switch (getCondition()) {
            case UNREPAIRABLE ->
                    throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_UNREPAIRABLE_EXCEPTION);
            case VERY_BAD -> 0.7 * price;
            case BAD -> 0.5 * price;
            case AVERAGE -> 0.2 * price;
            case DUSTY -> 5;
            case FINE ->
                    throw new HardwareException(HardwareException.HARDWARE_TYPE_CALCULATE_REPAIR_COST_BELOW_FINE_EXCEPTION);
        };
    }
}
