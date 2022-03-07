package de.superchat.backendchallenge.external.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.superchat.backendchallenge.config.TestSecurityConfig;
import de.superchat.backendchallenge.messages.MessagesService;
import de.superchat.backendchallenge.shared.enums.Channels;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestSecurityConfig.class)
@AutoConfigureMockMvc
class WebhookControllerTest {

    @MockBean
    MessagesService messagesService;

    @Autowired
    MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testReceiveMessageWebhook() throws Exception {
        WebhookRequest webhookRequest = new WebhookRequest();
        webhookRequest.setMessage("Testing");
        webhookRequest.setChannel(Channels.SMS);
        webhookRequest.setChannelValue("2244668800");
        webhookRequest.setSenderId(1L);
        webhookRequest.setRecipientId(1L);

        String valueJson = mapper.writeValueAsString(webhookRequest);

        mockMvc.perform(post("/api/v1/webhook").contentType(MediaType.APPLICATION_JSON).content(valueJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Message Sent"));
    }
}