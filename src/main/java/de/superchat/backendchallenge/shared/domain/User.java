package de.superchat.backendchallenge.shared.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.superchat.backendchallenge.shared.enums.PostgreSQLEnumType;
import de.superchat.backendchallenge.shared.enums.UserStatus;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "users_email_key", columnList = "email", unique = true)
})
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@Getter
@Setter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "user_status")
    @Type( type = "pgsql_enum" )
    private UserStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role roles;
}