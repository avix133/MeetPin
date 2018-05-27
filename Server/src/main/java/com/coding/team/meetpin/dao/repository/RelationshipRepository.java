package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {
}
