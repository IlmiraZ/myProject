package ru.ilmira.myProject.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilmira.myProject.persist.model.ERole;
import ru.ilmira.myProject.persist.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(ERole name);
}
