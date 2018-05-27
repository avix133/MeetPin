package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.PinToFriend;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface FriendRepository extends JpaRepository<PinToFriend, Integer> {
}
