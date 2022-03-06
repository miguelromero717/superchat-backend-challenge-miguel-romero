package de.superchat.backendchallenge.contacts;

import de.superchat.backendchallenge.clients.ClientRepository;
import de.superchat.backendchallenge.contacts.payload.ContactChannelRequest;
import de.superchat.backendchallenge.shared.domain.Client;
import de.superchat.backendchallenge.shared.domain.Contact;
import de.superchat.backendchallenge.shared.domain.ContactChannel;
import de.superchat.backendchallenge.shared.exceptions.ClientException;
import de.superchat.backendchallenge.shared.exceptions.ContactException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, ClientRepository clientRepository) {
        this.contactRepository = contactRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public Optional<Contact> createContact(Contact contact, List<ContactChannelRequest> channels, Long clientId) throws Exception {
        Assert.notNull(contact, "Contact data cannot be null");

        Optional<Client> client = Optional.of(
                        clientRepository.findById(clientId))
                .orElseThrow(() -> new ClientException("Client not found with id: " + clientId));

        client.get().getContacts().add(contact);

        contact.getClients().add(client.get());

        Optional<Contact> contactDB = Optional.ofNullable(
                Optional.of(contactRepository.save(contact))
                .orElseThrow(() -> new ContactException("Error creating Contact")));

        if (contactDB.isEmpty())
            throw new ContactException("Error creating Contact");

        Contact contactTmp = contactDB.get();
        contactTmp.setContactChannels(buildContactChannelsList(channels, contactTmp));

        return Optional.ofNullable(
                Optional.of(contactRepository.save(contactTmp))
                        .orElseThrow(() -> new ContactException("Error creating Contact")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> getContactsByClient(Long clientId) throws Exception {
        Set<Client> clients = Stream.of(clientRepository.findById(clientId).get()).collect(Collectors.toSet());
        return contactRepository.findContactsByClientsIn(clients);
    }

    private Set<ContactChannel> buildContactChannelsList(List<ContactChannelRequest> channels, Contact contact) {
        return channels.stream().map(channel -> {
            ContactChannel contactChannel = new ContactChannel();
            contactChannel.setChannel(channel.getChannel());
            contactChannel.setValue(channel.getValue());
            contactChannel.setStatus(channel.getStatus());
            contactChannel.setContacts(contact);

            return contactChannel;
        }).collect(Collectors.toSet());
    }
}
