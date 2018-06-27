package com.coding.team.meetpin.dao.repository;

import javax.transaction.Transactional;

import com.coding.team.meetpin.dao.model.PinAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<PinAnswer, Integer> {

}
