package co.unobot.uno.integrations.facebook.services;

import co.unobot.uno.integrations.facebook.Facebook;
import co.unobot.uno.integrations.facebook.graphapi.GraphAPIError;
import co.unobot.uno.integrations.facebook.graphapi.GraphAPIErrorCode;
import co.unobot.uno.integrations.facebook.graphapi.GraphApiFailureException;
import co.unobot.uno.integrations.facebook.models.FBPage;
import co.unobot.uno.integrations.facebook.models.FBUser;
import co.unobot.uno.integrations.facebook.models.message.Attachment;
import co.unobot.uno.integrations.facebook.models.message.AttachmentType;
import co.unobot.uno.integrations.facebook.models.message.outgoing.FBOutgoingMessage;
import co.unobot.uno.integrations.facebook.models.message.outgoing.Message;
import co.unobot.uno.integrations.facebook.models.message.templates.Template;
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

    public void sendTextMessage(String messageText, FBUser user, FBPage page) {
        logger.info("[Sending] {} {} {}", user.getId(), page.getId(), messageText);
        FBOutgoingMessage outgoingMessage = new FBOutgoingMessage(user.getId(), messageText);

        try {
            facebook.sendMessage(outgoingMessage, page.getAccessToken());
        } catch (GraphAPIError error) {
            if (error.getCode() == GraphAPIErrorCode.CODE_INVALID_ACCESS_TOKEN.code()
                    && error.getSubCode() == GraphAPIErrorCode.SUBCODE_SESSION_EXPIRED.code()) {
                page = pages.renewAccessToken(page);
                sendTextMessage(messageText, user, page);
            }
        } catch (GraphApiFailureException e) {
            logger.error("Message sending failed: " + e.getMessage());
        }
    }

    public void sendTemplateMessage(Template template, FBUser user, FBPage page) {

        Attachment<Template> attachment = new Attachment<>();
        attachment.setType(AttachmentType.TEMPLATE);
        attachment.setPayload(template);

        Message message = new Message();
        message.setAttachment(attachment);

        FBOutgoingMessage outgoingMessage = new FBOutgoingMessage();
        outgoingMessage.setMessage(message);
    }
}
