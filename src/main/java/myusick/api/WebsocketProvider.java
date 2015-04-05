package myusick.api;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.ws.rs.core.MultivaluedHashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 03/04/2015.
 */
public class WebsocketProvider {
    private static WebsocketProvider INSTANCE = new WebsocketProvider();


    private static final MultivaluedHashMap<Integer, Session> onlineClients
            = new MultivaluedHashMap<>();
    
    private WebsocketProvider(){}
    
    public static WebsocketProvider getInstance(){
        return INSTANCE;
    }
    
    public static synchronized void registerOnline(int id, Session session){
        onlineClients.add(id, session);
    }
    
    public static synchronized void removeOnline(int id, Session session){
        List<Session> sessions = onlineClients.get(id);
        if (sessions != null) {
            sessions.remove(session);
        }   
    }
}
