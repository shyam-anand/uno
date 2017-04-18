package co.unobot.uno.integrations.facebook.services;

import co.unobot.uno.chat.services.UnoService;
import co.unobot.uno.integrations.facebook.Facebook;
import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.models.FBUser;
import co.unobot.uno.integrations.facebook.models.message.incoming.FBIncomingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by shyam on 02/04/17.
 */
@Service
public class MessengerReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MessengerReceiver.class);

    @Autowired
    private Facebook facebook;

    @Autowired
    private UnoService uno;

    @Autowired
    private FBPagesService pages;

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

                    user = messaging.getSender();
                    page = pages.get(messaging.getRecipient().getId());

                    if (messaging.getMessage() != null) {
                        // Pass the message to Uno
                        uno.messageFromFB(messaging.getMessage(), user, page);

//                        if (message.getText() != null) {
//                            logger.info("Received text message - " + message.getText());
//
//                            message.getText();
//                        }
//                        if (message.getAttachments() != null) {
//                            logger.info("Attachments -- ");
//                            message.getAttachments().forEach((attachment) -> attachment.getPayload().forEach((key, value) -> logger.info(key + " -> " + value)));
//                        }
                    }
                });
            });
        } else {
            logger.warn("Invalid value for 'object': " + fbMessage.getObject());
        }
    }
}
