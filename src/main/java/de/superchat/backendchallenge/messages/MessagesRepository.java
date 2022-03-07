package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.shared.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {
}
