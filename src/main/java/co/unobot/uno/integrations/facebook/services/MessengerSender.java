package co.unobot.uno.integrations.facebook.services;

import co.unobot.uno.integrations.facebook.Facebook;
import co.unobot.uno.integrations.facebook.graphapi.GraphAPIError;
import co.unobot.uno.integrations.facebook.graphapi.GraphAPIErrorCode;
import co.unobot.uno.integrations.facebook.graphapi.GraphApiFailureException;
import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.models.FBUser;
import co.unobot.uno.integrations.facebook.models.message.outgoing.FBOutgoingMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Shyam Anand (shyamwdr@gmail.com)
 *         19/04/17
 */
@Service
public class MessengerSender {

    private static final Logger logger = LoggerFactory.getLogger(MessengerSender.class);

    @Autowired
    private Facebook facebook;

    @Autowired
    private FBPagesService pages;

    public void send(String messageText, FBUser user, FBPage page) {
        FBOutgoingMessage outgoingMessage = new FBOutgoingMessage(user.getId(), messageText);

        try {
            facebook.sendMessage(outgoingMessage, page.getAccessToken());
        } catch (GraphAPIError error) {
            if (error.getCode() == GraphAPIErrorCode.CODE_INVALID_ACCESS_TOKEN.code()
                    && error.getSubCode() == GraphAPIErrorCode.SUBCODE_SESSION_EXPIRED.code()) {
                page = pages.renewAccessToken(page);
                send(messageText, user, page);
            }
        } catch (GraphApiFailureException e) {
            logger.error("Message sending failed: " + e.getMessage());
        }
    }
}
