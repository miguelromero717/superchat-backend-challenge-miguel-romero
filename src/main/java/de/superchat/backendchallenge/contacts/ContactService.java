package de.superchat.backendchallenge.contacts;

import de.superchat.backendchallenge.contacts.payload.ContactChannelRequest;
import de.superchat.backendchallenge.shared.domain.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {

    Optional<Contact> createContact(Contact contact, List<ContactChannelRequest> channels, Long clientId) throws Exception;

    List<Contact> getContactsByClient(Long clientId) throws Exception;

}
