package de.superchat.backendchallenge.messages;

import de.superchat.backendchallenge.clients.ClientRepository;
import de.superchat.backendchallenge.config.queue.payload.Message;
import de.superchat.backendchallenge.config.queue.properties.QueueMessagesProperties;
import de.superchat.backendchallenge.contacts.ContactRepository;
import de.superchat.backendchallenge.external.CryptoCurrencyProperties;
import de.superchat.backendchallenge.external.CryptoCurrencyProviderImpl;
import de.superchat.backendchallenge.external.webhook.WebhookRequest;
import de.superchat.backendchallenge.shared.domain.Contact;
import de.superchat.backendchallenge.shared.domain.ContactChannel;
import de.superchat.backendchallenge.shared.domain.Template;
import de.superchat.backendchallenge.shared.enums.Channels;
import de.superchat.backendchallenge.shared.enums.ContactChannelStatus;
import de.superchat.backendchallenge.shared.exceptions.ContactException;
import de.superchat.backendchallenge.shared.exceptions.TemplateException;
import de.superchat.backendchallenge.shared.services.queue.message.QueueMessagesSender;
import de.superchat.backendchallenge.templates.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessagesServiceImpl implements MessagesService {

    private final MessagesRepository messagesRepository;
    private final ContactRepository contactRepository;
    private final TemplateService templateService;
    private final SMSMessageBuilder smsMessageBuilder;
    private final CryptoCurrencyProviderImpl cryptoCurrencyProviderImpl;
    private final CryptoCurrencyProperties cryptoCurrencyProperties;
    private final QueueMessagesSender queueMessagesSender;
    private final QueueMessagesProperties queueMessagesProperties;

    @Autowired
    public MessagesServiceImpl(MessagesRepository messagesRepository, ContactRepository contactRepository,
                               TemplateService templateService, SMSMessageBuilder smsMessageBuilder,
                               CryptoCurrencyProviderImpl cryptoCurrencyProviderImpl, CryptoCurrencyProperties cryptoCurrencyProperties,
                               QueueMessagesSender queueMessagesSender, QueueMessagesProperties queueMessagesProperties) {
        this.messagesRepository = messagesRepository;
        this.contactRepository = contactRepository;
        this.templateService = templateService;
        this.smsMessageBuilder = smsMessageBuilder;
        this.cryptoCurrencyProviderImpl = cryptoCurrencyProviderImpl;
        this.cryptoCurrencyProperties = cryptoCurrencyProperties;
        this.queueMessagesSender = queueMessagesSender;
        this.queueMessagesProperties = queueMessagesProperties;
    }

    @Override
    @Transactional
    public void sendMessageToContact(Long senderId, Long recipientId, Channels channel) throws Exception {

        Optional<Contact> contact = contactRepository.findById(recipientId);

        if (contact.isEmpty())
            throw new ContactException("Contact not found with Id: " + recipientId);

        Set<ContactChannel> contactChannels = contact.get().getContactChannels();

        List<ContactChannel> channels = contactChannels.stream().filter(cc ->
                        cc.getChannel().equals(channel) && cc.getStatus().equals(ContactChannelStatus.ACTIVE))
                .collect(Collectors.toList());

        if (channels.isEmpty())
            throw new ContactException("Contact does not have channel available: " + channel);

        Optional<Template> template = templateService.findTemplateByName("sms_tmpl");

        if (template.isEmpty())
            throw new TemplateException("Template not found");

        String textReadyToSend = templateService.buildMessageWithPlaceholdersValues(
                buildParamsValues(contact.get()), template.get().getMessage());

        Message messageToSend = smsMessageBuilder.buildMessage(
                textReadyToSend,
                channel,
                channels.stream().filter(c -> c.getChannel().equals(channel)).findFirst().get().getValue(),
                senderId,
                recipientId
        );

        queueMessagesSender.sendMessage(queueMessagesProperties, messageToSend);

        messagesRepository.save(buildEntityToSave(messageToSend));
    }

    @Override
    public void sendMessageFromWebhook(WebhookRequest webhookRequest) throws Exception {
        Message messageToSend = smsMessageBuilder.buildMessage(
                webhookRequest.getMessage(),
                webhookRequest.getChannel(),
                webhookRequest.getChannelValue(),
                webhookRequest.getSenderId(),
                webhookRequest.getRecipientId()
        );

        queueMessagesSender.sendMessage(queueMessagesProperties, messageToSend);

        messagesRepository.save(buildEntityToSave(messageToSend));
    }

    @Override
    public List<de.superchat.backendchallenge.shared.domain.Message> getMessagesClientContactByDate(Long senderId, Long recipientId) throws Exception {
        return messagesRepository.findMessagesByClientIdAndContactId(senderId, recipientId);
    }

    private Double getBTCPrice() throws Exception {
        cryptoCurrencyProviderImpl.initCryptoProperties(
                cryptoCurrencyProperties.getUrl() + cryptoCurrencyProperties.getPathAsset(),
                cryptoCurrencyProperties.getKeyHeader(),
                cryptoCurrencyProperties.getKey());
        return cryptoCurrencyProviderImpl.getCryptoCurrencyPrice("BTC");
    }

    private Object[] buildParamsValues(Contact contact) throws Exception {
        // TODO Implement something more dynamic to load params values to replace into the template
        Double btcPrice = getBTCPrice();
        String contactName = contact.getName() + contact.getLastName();

        return new Object[]{contactName, btcPrice};
    }

    private de.superchat.backendchallenge.shared.domain.Message buildEntityToSave(Message message) {
        de.superchat.backendchallenge.shared.domain.Message messageEntity = new de.superchat.backendchallenge.shared.domain.Message();

        messageEntity.setMessage(message.getMessage());
        messageEntity.setChannel(message.getChannel());
        messageEntity.setSenderId(message.getSenderId());
        messageEntity.setRecipientId(message.getRecipientId());

        return messageEntity;
    }
}
