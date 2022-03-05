package de.superchat.backendchallenge.auth.services;

import de.superchat.backendchallenge.auth.repositories.RoleRepository;
import de.superchat.backendchallenge.auth.repositories.UserRepository;
import de.superchat.backendchallenge.shared.domain.Role;
import de.superchat.backendchallenge.shared.domain.User;
import de.superchat.backendchallenge.shared.enums.Roles;
import de.superchat.backendchallenge.shared.enums.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Value("${superchat.app.user-password}")
    private String newPassword;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user) throws Exception {
        // TODO Implement something or delete this method if it's not needed
        Assert.notNull(user, "Invalid data to create a new user");
        return Optional.empty();
    }

    @Override
    public Optional<User> createUserFromClient(String name, String email) throws Exception {
        Assert.notNull(name, "Invalid name to create a new user");
        Assert.notNull(email, "Invalid email to create a new user");

        Optional<Role> role = roleRepository.findByRole(Roles.CLIENT.toString());

        if (role.isEmpty())
            throw new Exception("Invalid Role");

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(newPassword));
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(role.get());

        return Optional.of(userRepository.save(user));
    }
}
