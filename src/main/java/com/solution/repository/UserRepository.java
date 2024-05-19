package com.solution.repository;

import com.solution.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM User u " +
            "WHERE (u.email = :email) " +
            "AND u.id <> :id")
    Boolean findDuplicates(@Param("email") String email,
                           @Param("id") Long id);
}
