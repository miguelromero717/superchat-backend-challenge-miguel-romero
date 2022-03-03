package de.superchat.backendchallenge.shared.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.superchat.backendchallenge.shared.enums.ClientStatus;
import de.superchat.backendchallenge.shared.enums.PostgreSQLEnumType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "client_status")
    @Type( type = "pgsql_enum" )
    private ClientStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<ContactsXClient> contactsXClients = new LinkedHashSet<>();
}