package de.superchat.backendchallenge.templates;

import de.superchat.backendchallenge.shared.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findTemplateByName(String name) throws Exception;

}
