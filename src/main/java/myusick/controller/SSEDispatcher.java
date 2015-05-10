package myusick.controller;

import myusick.controller.dto.PostDTO;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.ws.rs.core.MediaType;

/**
 * Created by david on 30/04/2015.
 */
public class SSEDispatcher {

    public void dispatch(PostDTO message, String listener) {
        SseBroadcaster b = SSEProvider.getListenerMap().get(listener);
        if(b != null){
            System.out.println("To: "+listener+";; Who: "+message.getId()+";; Text: "+message.getContent());
            OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
            OutboundEvent event = eventBuilder.name("message")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(PostDTO.class, message)
                    .build();

            b.broadcast(event);
        }
    }
}
