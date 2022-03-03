package de.superchat.backendchallenge.auth.services;

import de.superchat.backendchallenge.shared.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> createUser(User user) throws Exception;

    Optional<User> createUserFromClient(String name, String email) throws Exception;

}
