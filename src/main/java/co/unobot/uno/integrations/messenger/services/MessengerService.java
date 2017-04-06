package co.unobot.uno.integrations.messenger.services;

import co.unobot.uno.chat.models.IncomingMessage;
import co.unobot.uno.chat.models.UnoResponse;
import co.unobot.uno.chat.services.UnoService;
import co.unobot.uno.integrations.messenger.Facebook;
import co.unobot.uno.integrations.messenger.models.FBUser;
import co.unobot.uno.integrations.messenger.models.message.incoming.FBIncomingMessage;
import co.unobot.uno.integrations.messenger.models.message.incoming.Message;
import co.unobot.uno.integrations.messenger.models.message.incoming.MessageObject;
import co.unobot.uno.integrations.messenger.models.message.outgoing.FBOutgoingMessage;
import co.unobot.uno.integrations.messenger.models.message.outgoing.Recipient;
import co.unobot.uno.models.User;
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

    private FBUser sender;

    @Async
    public void receive(FBIncomingMessage fbMessage) {

        logger.info("Received message - {}", fbMessage);

        if (fbMessage.getObject().equals(MessageObject.PAGE.name())) {
            fbMessage.getEntries().stream().forEach(entry -> {
                String pageId = entry.getId();
                long time = entry.getTime();

                logger.info("Page: {}, time: {}", pageId, time);

                entry.getMessaging().stream().forEach(messaging -> {
                    UnoResponse response;
                    sender = messaging.getSender();
                    if (messaging.getMessage() != null) {
                        Message message = messaging.getMessage();

                        if (message.getText() != null) {

                            logger.info("Received text message - " + message.getText());

                            IncomingMessage unoMessage = new IncomingMessage();
                            unoMessage.setMessage(message.getText());
                            User user = new User();
                            user.setId(sender.getId());
                            unoMessage.setUser(user);
                            response = uno.getResponse(unoMessage);
                            send(sender, response.getMessage());
                        }
                        if (!message.getAttachments().isEmpty()) {
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

    public void send(FBUser user, String messageText) {
        FBOutgoingMessage outgoingMessage = new FBOutgoingMessage();
        Recipient recipient = new Recipient();
        recipient.setId(user.getId());
        outgoingMessage.setRecipient(recipient);

        Message message = new Message();
        message.setText(messageText);
        outgoingMessage.setMessage(message);

        facebook.sendMessage(outgoingMessage);
    }
}
