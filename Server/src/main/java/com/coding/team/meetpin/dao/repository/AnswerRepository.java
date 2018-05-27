package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.PinAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface AnswerRepository extends JpaRepository<PinAnswer, Integer> {
}
