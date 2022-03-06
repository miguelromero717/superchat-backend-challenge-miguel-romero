package de.superchat.backendchallenge.shared.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.superchat.backendchallenge.shared.enums.ContactStatus;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contacts")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
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
    @Column(columnDefinition = "contact_status")
    @Type(type = "pgsql_enum")
    private ContactStatus status;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "contacts")
    private Set<Client> clients = new HashSet<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "contacts")
    @JsonIgnore
    private Set<ContactChannel> contactChannels = new HashSet<>();
}