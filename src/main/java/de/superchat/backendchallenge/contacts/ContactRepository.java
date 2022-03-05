package de.superchat.backendchallenge.contacts;

import de.superchat.backendchallenge.shared.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
