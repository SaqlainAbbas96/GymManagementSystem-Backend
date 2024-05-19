package com.solution.repository;

import com.solution.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAll();
    Member findByIdAndStatusTrue(Long id);
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
            "FROM Member m " +
            "WHERE (m.email = :email OR m.cardNo = :cardNo) " +
            "AND m.id <> :id")
    Boolean findDuplicates(@Param("email") String email,
                           @Param("cardNo") String cardNo,
                           @Param("id") Long id);
}
