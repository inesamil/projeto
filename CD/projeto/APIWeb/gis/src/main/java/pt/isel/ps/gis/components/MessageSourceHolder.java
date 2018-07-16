package pt.isel.ps.gis.components;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceHolder implements MessageSourceAware {

    private static MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        if (MessageSourceHolder.messageSource == null) {
            MessageSourceHolder.messageSource = messageSource;
        }
    }

    public static MessageSource getMessageSource() {
        return messageSource;
    }
}
