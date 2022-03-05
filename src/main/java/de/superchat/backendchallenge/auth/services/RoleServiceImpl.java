package de.superchat.backendchallenge.auth.services;

import de.superchat.backendchallenge.auth.repositories.RoleRepository;
import de.superchat.backendchallenge.shared.domain.Role;
import de.superchat.backendchallenge.shared.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findRoleByName(String name) throws Exception {
        return roleRepository.findByRole(name);
    }
}
