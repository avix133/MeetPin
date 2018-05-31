package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.PinAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<PinAnswer, Integer> {
}
