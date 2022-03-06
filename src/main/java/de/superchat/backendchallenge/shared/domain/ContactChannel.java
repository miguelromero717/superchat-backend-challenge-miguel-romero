package de.superchat.backendchallenge.shared.domain;

import de.superchat.backendchallenge.shared.enums.Channels;
import de.superchat.backendchallenge.shared.enums.ContactChannelStatus;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contact_channels")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@Getter
@Setter
public class ContactChannel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contacts;

    @Column(name = "value", nullable = false, length = 250)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "channel")
    @Type( type = "pgsql_enum" )
    private Channels channel;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "contact_channel_status")
    @Type( type = "pgsql_enum" )
    private ContactChannelStatus status;
}