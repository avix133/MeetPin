package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.PinAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AnswerRepository extends JpaRepository<PinAnswer, Integer> {

    @Transactional
    @Query(value="INSERT INTO pin_answer (pin_id, user_id, answer)" +
            "VALUES (:pinId, :userId, :answer)",
            nativeQuery = true)
    PinAnswer addAnswer(@Param("pinId") int pinId, @Param("userId") int userId, @Param("answer") boolean answer);

    @Query("SELECT a FROM PinAnswer a " +
        "WHERE a.pin = :pinId "  +
        "AND a.user = :userId")
    PinAnswer getAnswer(@Param("pinId") int pinId, @Param("userId") int userId);
}
