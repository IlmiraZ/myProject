package ru.ilmira.myProject.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ilmira.myProject.persist.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findById(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByUsername(@Param("username") String username);

    boolean existsByUsername(String username);
}
