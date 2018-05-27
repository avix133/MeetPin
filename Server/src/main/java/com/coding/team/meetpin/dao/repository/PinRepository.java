package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PinRepository extends JpaRepository<Pin, Integer> {

    Pin findPinById(int id);

    @Query("SELECT p from Pin p inner join p.pinToGlobal g on p.id = g.pin_id")
    List<Pin> fetchGlobalPins();

    @Query(value = "SELECT p.* from pin p inner join pin_to_friend f on p.id = f.pin_id where f.to_user_id = :id", nativeQuery = true)
    List<Pin> fetchPinsAddressedToMe(@Param("id") int user_id);
}
