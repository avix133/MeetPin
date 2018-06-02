package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.PinToFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<PinToFriend, Integer> {
}
