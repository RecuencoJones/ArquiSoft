package myusick.api;

import java.net.URI;
import java.util.ArrayList;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by david on 03/04/2015.
 */
@ServerEndpoint(value = "/websockets/{id}")
public class WebsocketServer {
    
    private Session userSession = null;
    private MessageHandler messageHandler;
    
    private int id;
    private URI uri;
    private int[] subscribers;

    public WebsocketServer(int id, int[] subscribers) {
        try {
            this.id = id;
            this.uri = new URI("wss://localhost:8025/websockets/"+this.id);
            this.subscribers = subscribers;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @OnOpen
    public void onOpen(Session userSession){
        WebsocketProvider.getInstance().registerOnline(id, this.userSession);
    }
    
    @OnClose
    public void onClose(Session userSession, CloseReason reason){
        this.userSession = null;
        WebsocketProvider.getInstance().removeOnline(this.id, this.userSession);
    }
    
    @OnError
    public void onError(Session userSession, Throwable t){
        this.userSession = null;
        WebsocketProvider.getInstance().removeOnline(this.id, this.userSession);
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public int[] getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int[] subscribers) {
        this.subscribers = subscribers;
    }

    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    public void sendMessage(String message) {
        ArrayList<WebsocketServer> onlineSubscribers = new ArrayList<>();
        //map this.subscribers with WebsocketService.onlineClients
        for(WebsocketServer wsc : onlineSubscribers){
            wsc.sendMessage(message);
        }
        this.userSession.getAsyncRemote().sendText(message);
    }

    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}
