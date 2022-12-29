package org.pl.cassandra.mappers;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.pl.cassandra.daos.ClientDao;

@Mapper
public interface ClientMapper {
    @DaoFactory
    ClientDao clientDao();
}
