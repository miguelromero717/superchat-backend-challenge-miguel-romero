package de.superchat.backendchallenge.contacts;

import de.superchat.backendchallenge.clients.ClientRepository;
import de.superchat.backendchallenge.shared.domain.Client;
import de.superchat.backendchallenge.shared.domain.Contact;
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
    public Optional<Contact> createContact(Contact contact, Long clientId) throws Exception {
        Assert.notNull(contact, "Contact data cannot be null");

        Optional<Client> client = Optional.of(
                        clientRepository.findById(clientId))
                .orElseThrow(() -> new ClientException("Client not found with id: " + clientId));

        client.get().getContacts().add(contact);

        contact.getClients().add(client.get());

        return Optional.ofNullable(
                Optional.of(contactRepository.save(contact))
                        .orElseThrow(() -> new ContactException("Error creating Contact")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contact> getContactsByClient(Long clientId) throws Exception {
        Set<Client> clients = Stream.of(clientRepository.findById(clientId).get()).collect(Collectors.toSet());
        return contactRepository.findContactsByClientsIn(clients);
    }
}
