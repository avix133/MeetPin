package com.coding.team.meetpin.dao.repository;

import javax.transaction.Transactional;

import com.coding.team.meetpin.dao.model.PinAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<PinAnswer, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO pin_answer (pin_id, user_id, answer) " +
            "SELECT :user_one, u.id, :user_one, 0 " +
            "FROM user u " +
            "WHERE u.email = :email",
            nativeQuery = true)
    int addAnswer(@Param("pin_id") int pin_id, @Param("user_id") int user_id);
}
