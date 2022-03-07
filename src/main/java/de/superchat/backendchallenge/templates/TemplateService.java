package de.superchat.backendchallenge.templates;

import de.superchat.backendchallenge.shared.domain.Template;

import java.util.Optional;

public interface TemplateService {

    Optional<Template> findTemplateByName(String name) throws Exception;

    String buildMessageWithPlaceholdersValues(Object[] placeholders, String template) throws Exception;

}
