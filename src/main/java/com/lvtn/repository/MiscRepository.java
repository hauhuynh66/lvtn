package com.lvtn.repository;

import com.lvtn.model.House;
import com.lvtn.model.Misc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MiscRepository extends JpaRepository<Misc, Integer> {
    List<Misc> getAllByHouse(House house);
    List<Misc> getAllByHouseAndDateGreaterThanAndDateLessThan(House house, Date from, Date to);
    List<Misc> getTop20ByHouseOrderByDateDesc(House house);
    List<Misc> getAllByHouseAndDateGreaterThanAndDateLessThanAndLightGreaterThan(House house, Date from, Date to, double light);
    List<Misc> getAllByHouseAndDateGreaterThanAndDateLessThanAndSmokeGreaterThan(House house, Date from, Date to, double smoke);
    Misc getTopByHouseOrderByDateDesc(House house);
    Misc getTopByHouseAndDateGreaterThanAndDateLessThanOrderByLightDesc(House house, Date from, Date to);
    Misc getTopByHouseAndDateGreaterThanAndDateLessThanOrderBySmokeDesc(House house, Date from, Date to);
}
