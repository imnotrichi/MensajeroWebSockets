package org.itson.servidorwebsockets.socket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/echo")
public class WSocket {
    
    private static List<Session> sessions = new LinkedList<>();

    @OnOpen
    public void open(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void close(Session session) {
        try {
            sessions.get(sessions.indexOf(session)).close();
            sessions.remove(sessions.indexOf(session));
        } catch (IOException ex) {
            Logger.getLogger(WSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            for (Session sesion : sessions) {
                sesion.getBasicRemote().sendText("ECHO: " + message);
            }
        } catch (IOException ex) {
            Logger.getLogger(WSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
