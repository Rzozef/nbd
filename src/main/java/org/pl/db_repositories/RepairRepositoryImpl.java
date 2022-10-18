package org.pl.db_repositories;

import jakarta.persistence.EntityManager;
import org.pl.model.Repair;

public class RepairRepositoryImpl implements RepairRepository {
    private EntityManager em;

    public RepairRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Repair getRepairById(int id) {
        return em.find(Repair.class, id);
    }

    @Override
    public Repair saveRepair(Repair repair) {
        if (repair.getID() == 0) {
            em.persist(repair);
        } else {
            repair = em.merge(repair);
        }
        return repair;
    }

    @Override
    public void deleteRepair(Repair repair) {
        if (em.contains(repair)) {
            em.remove(repair);
        } else {
            em.merge(repair);
        }
    }
}