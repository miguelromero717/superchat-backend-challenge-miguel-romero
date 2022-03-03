package de.superchat.backendchallenge.shared.domain;

import de.superchat.backendchallenge.shared.enums.ContactStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contacts")
@Getter
@Setter
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 250)
    private String email;

    @Column(name = "phone", nullable = false, length = 25)
    private String phone;

    @Enumerated(EnumType.STRING)
    private ContactStatus status;

    @OneToMany(mappedBy = "contact")
    private List<ContactsXClient> contactsXClients = new ArrayList<>();

    public List<ContactsXClient> getContactsXClients() {
        return contactsXClients;
    }
}