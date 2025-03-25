package org.itson.clientewebsockets;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import java.net.URI;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ClientEndpoint
public class WSEndpoint {

    private URI serverURI;
    private Session session;

    public WSEndpoint() {
        serverURI = URI.create("ws://localhost:8080/ServidorWebSockets/echo");

        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(this, serverURI);
        } catch (DeploymentException | IOException ex) {
            Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Ya me conecté");
        try {
            session.getBasicRemote().sendText(session.getId() + ": Ya me conecté");
        } catch (IOException ex) {
            Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @OnMessage
    public void onMessage(String mensaje, Session session) {
        System.out.println("El servidor dice: " + mensaje);
    }

    public void enviarMensaje(String mensaje) {
        try {
            session.getBasicRemote().sendText(session.getId() + ": " + mensaje);
        } catch (IOException e) {
            Logger.getLogger(WSEndpoint.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
