package org.itson.servidorwebsockets.socket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/echo")
public class WSocket {
    
    private static Map<String, Session> sessions = new HashMap<>(); 

    @OnOpen
    public void open(Session session) {
        sessions.put(session.getId(), session); 
    }

    @OnClose
    public void close(Session session) throws IOException {
        String alias = getAliasBySession(session);
        if (!alias.equals(session.getId())) {
            sessions.remove(alias);
            broadcast("DISCONNECT:" + alias, null); 
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            if (message.startsWith("REGISTER:")) {
                String alias = message.substring(9);
                sessions.remove(session.getId());
                sessions.put(alias, session);
                broadcast("CONNECT:" + alias, session); 
                StringBuilder userList = new StringBuilder("USERS:");
                for (String existingAlias : sessions.keySet()) {
                    if (!existingAlias.equals(alias)) {
                        userList.append(existingAlias).append(",");
                    }
                }
                session.getBasicRemote().sendText(userList.toString());
            } else if (message.startsWith("TO:")) {
                String[] parts = message.split(":", 3);
                if (parts.length == 3) {
                    String destinatario = parts[1];
                    String contenido = parts[2];
                    Session targetSession = sessions.get(destinatario);
                    if (targetSession != null && targetSession.isOpen()) {
                        String remitente = getAliasBySession(session);
                        targetSession.getBasicRemote().sendText(remitente + ": " + contenido);
                    }
                }
            } else {
                String remitente = getAliasBySession(session);
                for (Session sesion : sessions.values()) {
                    if (sesion.isOpen()) {
                        sesion.getBasicRemote().sendText(remitente + ": " + message);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void broadcast(String message, Session excludeSession) throws IOException {
        for (Session sesion : sessions.values()) {
            if (sesion.isOpen() && (excludeSession == null || !sesion.equals(excludeSession))) {
                sesion.getBasicRemote().sendText(message);
            }
        }
    }

    private String getAliasBySession(Session session) {
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            if (entry.getValue().equals(session)) {
                return entry.getKey();
            }
        }
        return session.getId(); 
    }
}