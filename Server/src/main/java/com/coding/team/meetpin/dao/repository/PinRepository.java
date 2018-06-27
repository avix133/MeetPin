package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PinRepository extends JpaRepository<Pin, Integer> {

    Pin findPinById(int id);

    @Query("SELECT p FROM Pin p " +
            "INNER JOIN p.pinToGlobal g " +
            "ON p.id = g.pin_id")
    List<Pin> fetchGlobalPins();

    @Query(value = "SELECT p.* FROM pin p " +
            "INNER JOIN pin_to_friend f " +
            "ON p.id = f.pin_id " +
            "WHERE f.to_user_id = :id",
            nativeQuery = true)
    List<Pin> fetchPinsAddressedToMe(@Param("id") int user_id);

    @Query(value =
            "(SELECT p.* " +
            "FROM pin p " +
            "INNER JOIN pin_to_global g ON p.id = g.pin_id) " +
            "UNION " +
            "(SELECT p.* FROM pin p " +
            "INNER JOIN pin_to_friend f ON p.id = f.pin_id " +
            "WHERE f.to_user_id = :id)", nativeQuery = true)
    List<Pin> fetchDisplayPins(@Param("id") int user_id);
}

