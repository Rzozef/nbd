package org.pl.cassandra.repositories;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;

@Mapper
public interface HardwareMapper {
    @DaoFactory
    HardwareDao hardwareDao();
}
