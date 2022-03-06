package de.superchat.backendchallenge.auth.services;

import de.superchat.backendchallenge.config.queue.payload.UserMessage;
import de.superchat.backendchallenge.shared.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> createUserFromClient(UserMessage userMessage) throws Exception;

}
