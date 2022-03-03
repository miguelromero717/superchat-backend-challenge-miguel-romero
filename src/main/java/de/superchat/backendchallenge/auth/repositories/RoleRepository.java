package de.superchat.backendchallenge.auth.repositories;

import de.superchat.backendchallenge.shared.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findRoleByRole(String role) throws Exception;

}
