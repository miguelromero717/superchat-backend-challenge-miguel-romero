package de.superchat.backendchallenge.auth.repositories;

import de.superchat.backendchallenge.shared.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(String role) throws Exception;

}
