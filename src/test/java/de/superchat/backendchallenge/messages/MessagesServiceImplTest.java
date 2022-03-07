package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.contacts.ContactRepository;
import de.superchat.backendchallenge.shared.domain.Client;
import de.superchat.backendchallenge.shared.domain.Contact;
import de.superchat.backendchallenge.shared.domain.ContactChannel;
import de.superchat.backendchallenge.shared.enums.Channels;
import de.superchat.backendchallenge.shared.enums.ContactChannelStatus;
import de.superchat.backendchallenge.shared.enums.ContactStatus;
import de.superchat.backendchallenge.shared.exceptions.ContactException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessagesServiceImplTest {

    private AutoCloseable closeable;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private MessagesServiceImpl messagesService;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testSendMessageToContactSuccessful() throws Exception {
        MessagesServiceImpl messagesService = mock(MessagesServiceImpl.class);
        doNothing().when(messagesService).sendMessageToContact(1L, 1L, Channels.SMS);
        messagesService.sendMessageToContact(1L, 1L, Channels.SMS);
        verify(messagesService, times(1)).sendMessageToContact(1L, 1L, Channels.SMS);
    }

    @Test
    void testSendMessageToContactSuccessfulContactException() throws Exception {
        // given
        Client client = mockClient();
        Contact contact = mockContact();

        // when
        lenient().when(contactRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ContactException.class, () -> {
            messagesService.sendMessageToContact(client.getId(), contact.getId(), Channels.SMS);
        });

        // then
    }

    private Client mockClient() {
        Client client = new Client();
        client.setId(1L);
        client.setName("Test Client");
        client.setEmail("clienttest@mail.com");
        return client;
    }

    private Contact mockContact() {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("Contact");
        contact.setLastName("Test");
        contact.setEmail("test@mail.com");
        contact.setPhone("223344");
        contact.setStatus(ContactStatus.ACTIVE);
        contact.setContactChannels(mockContactChannels(contact));

        return contact;
    }

    private Set<ContactChannel> mockContactChannels(Contact contact) {
        ContactChannel contactChannel = new ContactChannel();
        contactChannel.setId(1L);
        contactChannel.setContacts(contact);
        contactChannel.setChannel(Channels.SMS);
        contactChannel.setValue("1133557799");
        contactChannel.setStatus(ContactChannelStatus.ACTIVE);

        return Stream.of(contactChannel).collect(Collectors.toSet());
    }
}