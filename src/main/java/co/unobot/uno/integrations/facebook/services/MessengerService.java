package co.unobot.uno.integrations.facebook.services;

import co.unobot.uno.chat.models.IncomingMessage;
import co.unobot.uno.chat.models.UnoResponse;
import co.unobot.uno.chat.services.UnoService;
import co.unobot.uno.integrations.facebook.Facebook;
import co.unobot.uno.integrations.facebook.graphapi.GraphAPIError;
import co.unobot.uno.integrations.facebook.graphapi.GraphAPIErrorCode;
import co.unobot.uno.integrations.facebook.graphapi.GraphApiFailureException;
import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.models.FBUser;
import co.unobot.uno.integrations.facebook.models.message.incoming.FBIncomingMessage;
import co.unobot.uno.integrations.facebook.models.message.incoming.Message;
import co.unobot.uno.integrations.facebook.models.message.outgoing.FBOutgoingMessage;
import co.unobot.uno.users.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by shyam on 02/04/17.
 */
@Service
public class MessengerService {

    private static final Logger logger = LoggerFactory.getLogger(MessengerService.class);

    @Autowired
    private Facebook facebook;

    @Autowired
    private UnoService uno;

    @Autowired
    private PagesService pages;

    private FBUser user;
    private FBPage page;

    @Async
    public void receive(FBIncomingMessage fbMessage) {

        logger.info("Received message - {}", fbMessage);

        if (fbMessage.getObject().equals("page")) {
            fbMessage.getEntries().stream().forEach(entry -> {
                String pageId = entry.getId();
                long time = entry.getTime();

                logger.info("Page: {}, time: {}", pageId, time);

                entry.getMessaging().stream().forEach(messaging -> {
                    UnoResponse response;
                    user = messaging.getSender();
                    page = pages.get(messaging.getRecipient().getId());

                    if (messaging.getMessage() != null) {
                        Message message = messaging.getMessage();

                        if (message.getText() != null) {

                            logger.info("Received text message - " + message.getText());

                            IncomingMessage unoMessage = new IncomingMessage();
                            unoMessage.setMessage(message.getText());
                            User user = new User();
                            user.setId(this.user.getId());
                            unoMessage.setUser(user);
                            response = uno.getResponse(unoMessage);
                            send(response.getMessage());
                        }
                        if (message.getAttachments() != null) {
                            logger.info("Attachments -- ");
                            message.getAttachments().forEach((attachment) -> attachment.getPayload().forEach((key, value) -> logger.info(key + " -> " + value)));
                        }
                    }
                });
            });
        } else {
            logger.warn("Invalid value for 'object': " + fbMessage.getObject());
        }
    }

    public void send(String messageText) {
        FBOutgoingMessage outgoingMessage = new FBOutgoingMessage(user.getId(), messageText);

        try {
            facebook.sendMessage(outgoingMessage, page.getAccessToken());
        } catch (GraphAPIError error) {
            if (error.getCode() == GraphAPIErrorCode.CODE_INVALID_ACCESS_TOKEN.code() && error.getSubCode() == GraphAPIErrorCode.SUBCODE_SESSION_EXPIRED.code()) {
                page = pages.renewAccessToken(page);
                send(messageText);
            }
        } catch (GraphApiFailureException e) {
            logger.error("Message sending failed: " + e.getMessage());
        }
    }
}
