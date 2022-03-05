package de.superchat.backendchallenge.contacts;

import de.superchat.backendchallenge.shared.domain.Client;
import de.superchat.backendchallenge.shared.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findContactsByClientsIn(Set<Client> clients) throws Exception;

}
