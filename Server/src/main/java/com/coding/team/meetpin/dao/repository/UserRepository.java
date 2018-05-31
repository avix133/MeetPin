package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUserByUsername(String username);
    User findUserById(int id);
}
