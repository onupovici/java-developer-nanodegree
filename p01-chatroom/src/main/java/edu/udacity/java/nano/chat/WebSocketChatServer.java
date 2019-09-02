package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat/{username}")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) throws IOException {
        for (Session session : onlineSessions.values()) {
            session.getBasicRemote().sendText(msg);
        }
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        onlineSessions.put(username, session);

        Message message = new Message(username, username + " joined the chat", onlineSessions.size(), "LOGIN");
        sendMessageToAll(JSON.toJSONString(message));
        System.out.println(username + " joined the chat");
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) throws IOException {
        Message message = JSON.parseObject(jsonStr, Message.class);
        message.setType("SPEAK");
        message.setOnlineCount(onlineSessions.size());
        jsonStr = JSON.toJSONString(message);
        sendMessageToAll(jsonStr);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        String username = session.getPathParameters().get("username");
        onlineSessions.remove(username);

        Message message = new Message(username, username + " left the chat", onlineSessions.size(), "LOGOUT");
        sendMessageToAll(JSON.toJSONString(message));
        System.out.println(username + " left the chat");
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
