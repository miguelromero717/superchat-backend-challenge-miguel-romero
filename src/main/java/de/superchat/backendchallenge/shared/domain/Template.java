package de.superchat.backendchallenge.shared.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "templates")
@Getter
@Setter
public class Template extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "message", nullable = false, columnDefinition = "text")
    private String message;

    @ManyToMany
    @JoinTable(name = "templates_x_placeholders",
            joinColumns = @JoinColumn(name = "template_id"),
            inverseJoinColumns = @JoinColumn(name = "placeholder_id"))
    private Set<Placeholder> placeholders = new LinkedHashSet<>();
}