package myusick.api;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by david on 01/05/2015.
 */
@Path("ws")
public class WebsocketProvider {

    private static ConcurrentHashMap<String,SseBroadcaster> listenerMap = new ConcurrentHashMap<>();
    private static WebsocketDispatcher websocketDispatcher = new WebsocketDispatcher();

    @Path("/sub/{id}")
    @GET
    public EventOutput subscribe(@PathParam("id") String id){
//        Client client = ClienbtBuilder.newBuilder().register(SseFeature.class).build();
        SseBroadcaster b = new SseBroadcaster();
        final EventOutput eventOutput = new EventOutput();
        b.add(eventOutput);
//        WebSocketBroadcaster b = new WebSocketBroadcaster();
        listenerMap.put(id,b);
        return eventOutput;
    }

    @Path("/unsub/{id}")
    @GET
    public String unsubscribe(@PathParam("id") String id){
        SseBroadcaster b = listenerMap.remove(id);
        b.closeAll();
        return "Unsubscribed from broker";
    }

    public static WebsocketDispatcher getWebsocketDispatcher() {
        return websocketDispatcher;
    }

    protected static ConcurrentHashMap<String, SseBroadcaster> getListenerMap() {
        return listenerMap;
    }
}
