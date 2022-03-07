package de.superchat.backendchallenge.templates;

import de.superchat.backendchallenge.shared.domain.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

@Service
public class TemplateServiceImpl implements TemplateService{

    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public Optional<Template> findTemplateByName(String name) throws Exception {
        return templateRepository.findTemplateByName(name);
    }

    @Override
    public String buildMessageWithPlaceholdersValues(Object[] placeholders, String template) throws Exception {
        MessageFormat mf = new MessageFormat(template);
        return mf.format(placeholders);
    }
}
