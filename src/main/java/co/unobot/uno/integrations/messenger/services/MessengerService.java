package co.unobot.uno.integrations.messenger.services;

import co.unobot.uno.chat.models.IncomingMessage;
import co.unobot.uno.chat.models.UnoResponse;
import co.unobot.uno.chat.services.UnoService;
import co.unobot.uno.integrations.messenger.Facebook;
import co.unobot.uno.integrations.messenger.models.FBUser;
import co.unobot.uno.integrations.messenger.models.message.incoming.FBIncomingMessage;
import co.unobot.uno.integrations.messenger.models.message.incoming.Message;
import co.unobot.uno.integrations.messenger.models.message.incoming.MessageObject;
import co.unobot.uno.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by shyam on 02/04/17.
 */
@Service
public class MessengerService extends Facebook {

    private static final Logger logger = LoggerFactory.getLogger(MessengerService.class);

    @Autowired
    private UnoService uno;

    private FBUser sender;

    @Autowired
    public MessengerService(@Value("${uri}") String uri, @Value("${page.accessToken}") String pageAccessToken) {
        super(uri, pageAccessToken);
    }

    @Async
    public void receive(FBIncomingMessage fbMessage) {
        if (fbMessage.getObject().equals(MessageObject.PAGE)) {
            fbMessage.getEntries().stream().forEach(entry -> {
                String pageId = entry.getId();
                long time = entry.getTime();

                entry.getMessaging().stream().forEach(messaging -> {
                    UnoResponse response;
                    sender = messaging.getSender();
                    if (messaging.getMessage() != null) {
                        Message message = messaging.getMessage();
                        if (message.getText() != null) {
                            IncomingMessage unoMessage = new IncomingMessage();
                            unoMessage.setMessage(message.getText());
                            User user = new User();
                            user.setId(sender.getId());
                            unoMessage.setUser(user);
                            response = uno.getResponse(unoMessage);
                            send(sender, response.getMessage());
                        }
                        if (!message.getAttachments().isEmpty()) {

                        }
                    }
                });
            });
        }
    }

    public void send(FBUser user, String messageText) {

    }
}
