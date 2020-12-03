package com.lvtn.repository;

import com.lvtn.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Integer> {
    House findById(int id);
}
