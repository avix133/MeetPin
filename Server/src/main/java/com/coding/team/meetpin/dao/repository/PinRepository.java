package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PinRepository extends JpaRepository<Pin, Integer> {
    List<Pin> findPinByUser(String username);
    Pin findPinById(int id);
    @Query("SELECT p from Pin p inner join p.pinToGlobal g on p.id = g.pin_id")
    List<Pin> fetchGlobalPins();
}
