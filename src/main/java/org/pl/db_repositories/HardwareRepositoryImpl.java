package org.pl.db_repositories;

import jakarta.persistence.EntityManager;
import org.pl.model.Hardware;

public class HardwareRepositoryImpl implements HardwareRepository {
    private EntityManager em;

    public HardwareRepositoryImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public Hardware getHardwareById(int id) {
        return em.find(Hardware.class, id);
    }

    @Override
    public Hardware saveHardware(Hardware hardware) {
        if (hardware.getID() == 0) {
            em.persist(hardware);
        } else {
            hardware = em.merge(hardware);
        }
        return hardware;
    }

    @Override
    public void deleteHardware(Hardware hardware) {
        if (em.contains(hardware)) {
            em.remove(hardware);
        } else {
            em.merge(hardware);
        }
    }
}