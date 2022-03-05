package de.superchat.backendchallenge.auth.services;

import de.superchat.backendchallenge.shared.domain.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findRoleByName(String name) throws Exception;

}
