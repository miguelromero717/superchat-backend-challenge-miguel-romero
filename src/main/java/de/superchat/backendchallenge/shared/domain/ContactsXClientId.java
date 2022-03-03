package de.superchat.backendchallenge.shared.domain;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ContactsXClientId implements Serializable {
    private static final long serialVersionUID = 3155781440213130713L;
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    @Column(name = "contact_id", nullable = false)
    private Long contactId;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, contactId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ContactsXClientId entity = (ContactsXClientId) o;
        return Objects.equals(this.clientId, entity.clientId) &&
                Objects.equals(this.contactId, entity.contactId);
    }
}