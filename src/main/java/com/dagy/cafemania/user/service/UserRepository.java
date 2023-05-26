package com.dagy.cafemania.user.service;

import com.dagy.cafemania.user.Role;
import com.dagy.cafemania.user.Status;
import com.dagy.cafemania.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(Role role);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = TRUE, u.status = :status WHERE u.id = :id")
    Integer enableUser(@Param("id") Integer id, @Param("status") Status status);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = FALSE,  u.status = :status WHERE  u.id =:id")
    Integer disableUser(@Param("id") Integer id, @Param("status") Status status);
}
