package de.superchat.backendchallenge.shared.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.superchat.backendchallenge.shared.enums.PlaceholderStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

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
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "placeholders")
@Getter
@Setter
public class Placeholder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "placeholder", nullable = false, length = 30)
    private String placeholder;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "placeholder_status")
    @Type(type = "pgsql_enum")
    private PlaceholderStatus status;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "placeholders")
    private Set<Template> templates = new HashSet<>();

}