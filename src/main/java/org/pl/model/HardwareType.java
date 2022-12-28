package org.pl.model;

import org.pl.exceptions.HardwareException;

public abstract class HardwareType {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public abstract double calculateRepairCost(int price) throws HardwareException;
}
