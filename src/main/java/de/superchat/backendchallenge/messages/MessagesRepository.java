package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.shared.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {

    @Query(value = "select * from messages m where m.sender_id = ?1 and m.recipient_id = ?2 " +
            "union " +
            "select * from messages m where m.sender_id = ?2 and m.recipient_id = ?1",
            nativeQuery = true)
    List<Message> findMessagesByClientIdAndContactId(
            @Param("senderId") Long senderId,
            @Param("recipientId") Long recipientId
    ) throws Exception;
}
