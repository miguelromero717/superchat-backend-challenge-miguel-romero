package de.superchat.backendchallenge.shared.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.superchat.backendchallenge.shared.enums.ClientStatus;
import de.superchat.backendchallenge.shared.enums.PostgreSQLEnumType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients", indexes = {
        @Index(name = "clients_name_uindex", columnList = "name", unique = true)
})
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@Getter
@Setter
public class Client extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "client_status")
    @Type(type = "pgsql_enum")
    private ClientStatus status;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "contacts_x_clients",
            joinColumns = {@JoinColumn(name = "client_id")},
            inverseJoinColumns = {@JoinColumn(name = "contact_id")})
    private Set<Contact> contacts = new HashSet<>();

    public void addContact(Contact contact) {
        this.contacts.add(contact);
        contact.getClients().add(this);
    }

    public void removeContact(Long contactId) {
        Contact contact = this.contacts.stream().filter(c -> c.getId() == contactId).findFirst().orElse(null);
        if (contact != null)
            this.contacts.remove(contact);
        contact.getClients().remove(this);
    }
}