package com.gen.blogen.repository;

import com.gen.blogen.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUser(String user);

    List<User> findAllByNameContainingIgnoreCase(@Param("name") String name);
}
