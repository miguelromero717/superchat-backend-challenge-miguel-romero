package de.superchat.backendchallenge.contacts;

import de.superchat.backendchallenge.clients.ClientService;
import de.superchat.backendchallenge.contacts.payload.ContactRequest;
import de.superchat.backendchallenge.shared.domain.Contact;
import de.superchat.backendchallenge.shared.enums.ContactStatus;
import de.superchat.backendchallenge.shared.exceptions.ClientException;
import de.superchat.backendchallenge.shared.exceptions.ContactException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactsController {

    private final ContactService contactService;
    private final ClientService clientService;

    @Autowired
    public ContactsController(ContactService contactService, ClientService clientService) {
        this.contactService = contactService;
        this.clientService = clientService;
    }

    @PostMapping("/{clientId}/clients")
    public ResponseEntity<?> createContact(@PathVariable Long clientId,
                                           @RequestBody @Validated ContactRequest contactRequest) throws Exception {

        if (clientId < 1)
            throw new ClientException("Invalid Client Id");

        Optional<Contact> contact = Optional.of(
                contactService.createContact(buildContactFromRequest(contactRequest), clientId))
                .orElseThrow(() -> new ContactException("Error creating Contact"));

        return new ResponseEntity<>(contact.get(), HttpStatus.CREATED);
    }

    private Contact buildContactFromRequest(ContactRequest contactRequest) throws Exception {
        Contact contact = new Contact();
        contact.setName(contactRequest.getName());
        contact.setLastName(contactRequest.getLastName());
        contact.setEmail(contactRequest.getEmail());
        contact.setPhone(contactRequest.getPhone());
        contact.setStatus(ContactStatus.ACTIVE);

        return contact;
    }

}
