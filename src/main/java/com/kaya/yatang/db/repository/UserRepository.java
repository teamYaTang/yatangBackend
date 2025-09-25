package com.kaya.yatang.db.repository;

import com.kaya.yatang.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByNicknameAndIdNot(String nickname, Long id);
    Optional<User> findByUserid(String userid);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}