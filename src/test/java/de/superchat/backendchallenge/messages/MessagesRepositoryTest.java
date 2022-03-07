package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.shared.domain.Message;
import de.superchat.backendchallenge.shared.enums.Channels;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessagesRepositoryTest {

    @Autowired
    MessagesRepository messagesRepository;

    @Test
    void shouldReturnMessages() throws Exception {
        Message message = new Message();
        message.setMessage("Test 1");
        message.setSenderId(1L);
        message.setRecipientId(2L);
        message.setChannel(Channels.SMS);
        messagesRepository.save(message);

        Message message2 = new Message();
        message2.setMessage("Test 1");
        message2.setSenderId(2L);
        message2.setRecipientId(1L);
        message2.setChannel(Channels.SMS);
        messagesRepository.save(message2);

        List<Message> messages = messagesRepository.findMessagesByClientIdAndContactId(1L, 2L);
        assertThat(messages).hasSize(2).contains(message, message2);
    }

}