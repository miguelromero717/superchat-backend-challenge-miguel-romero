package de.superchat.backendchallenge.auth.services.impl;

import de.superchat.backendchallenge.auth.repositories.RoleRepository;
import de.superchat.backendchallenge.auth.repositories.UserRepository;
import de.superchat.backendchallenge.auth.services.UserService;
import de.superchat.backendchallenge.config.queue.payload.UserMessage;
import de.superchat.backendchallenge.shared.domain.Role;
import de.superchat.backendchallenge.shared.domain.User;
import de.superchat.backendchallenge.shared.enums.Roles;
import de.superchat.backendchallenge.shared.enums.UserStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

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

    @RabbitListener(queues = "${superchat.queue-user.name}")
    public void listener (UserMessage userMessage) throws Exception {
        createUserFromClient(userMessage);
    }

    @Override
    public Optional<User> createUserFromClient(UserMessage userMessage) throws Exception {
        Assert.notNull(userMessage.getName(), "Invalid name to create a new user");
        Assert.notNull(userMessage.getEmail(), "Invalid email to create a new user");

        Optional<Role> role = roleRepository.findByRole(Roles.CLIENT.toString());

        if (role.isEmpty())
            throw new Exception("Invalid Role");

        User user = new User();
        user.setName(userMessage.getName());
        user.setEmail(userMessage.getEmail());
        user.setPassword(encoder.encode(newPassword));
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(role.get());

        return Optional.of(userRepository.save(user));
    }
}
