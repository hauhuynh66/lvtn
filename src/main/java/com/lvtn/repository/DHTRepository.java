package com.lvtn.repository;

import com.lvtn.model.DHT;
import com.lvtn.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DHTRepository extends JpaRepository<DHT, Integer> {
    List<DHT> getAllByHouse(House house);
    List<DHT> getAllByHouseAndDateGreaterThanAndDateLessThan(House house, Date from, Date to);
    List<DHT> getAllByHouseAndDateGreaterThanAndDateLessThanAndTempGreaterThan(House house, Date from, Date to, double temp);
    List<DHT> getAllByHouseAndDateGreaterThanAndDateLessThanAndHumidGreaterThan(House house, Date from, Date to, double humid);
    List<DHT> getTop20ByHouseOrderByDateDesc(House house);
    DHT getTopByHouseOrderByDateDesc(House house);
    DHT getTopByHouseAndDateGreaterThanAndDateLessThanOrderByTempDesc(House house, Date from, Date to);
    DHT getTopByHouseAndDateGreaterThanAndDateLessThanOrderByHumidDesc(House house, Date from, Date to);
}
