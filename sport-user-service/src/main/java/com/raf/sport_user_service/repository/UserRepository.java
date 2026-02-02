package com.raf.sport_user_service.repository;

import com.raf.sport_user_service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // Za login
    Optional<User> findByUsername(String username);

    // Za proveru duplikata pri registraciji
    Optional<User> findByEmail(String email);

}
