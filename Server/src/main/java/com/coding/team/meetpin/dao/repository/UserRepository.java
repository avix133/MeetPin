package com.coding.team.meetpin.dao.repository;

import com.coding.team.meetpin.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u " +
            "INNER JOIN Relationship r " +
            "ON u.id = r.user_two " +
            "WHERE r.action_user = :id " +
            "AND r.status = FALSE")
    List<User> fetchPendingInvitations(@Param("id") int user_id);

    @Query("SELECT u FROM User u " +
            "INNER JOIN Relationship r " +
            "ON u.id = r.user_two " +
            "WHERE r.action_user = :id " +
            "AND r.status = TRUE")
    List<User> fetchFriendList(@Param("id") int user_id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO relationship " +
            "(user_one_id, user_two_id, action_user_id, status) " +
            "VALUES (:user_one, :user_two, :user_one, 0)",
            nativeQuery = true)
    int inviteFriend(@Param("user_one") int user_one, @Param("user_two") int user_two);

    @Modifying
    @Transactional
    @Query("DELETE FROM Relationship " +
            "WHERE status = TRUE " +
            "AND action_user = :user " +
            "AND user_two = :friend_to_delete")
    int removeFriend(@Param("user") int action_user, @Param("friend_to_delete") int user_two);
}
