package de.superchat.backendchallenge.contacts;

import de.superchat.backendchallenge.shared.domain.Contact;

import java.util.Optional;

public interface ContactService {

    Optional<Contact> createContact(Contact contact, Long clientId) throws Exception;

}
