package app.web;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class HelloWebSocketHandler extends TextWebSocketHandler{

  Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
  
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    sessions.put(session.getId(), session);
  }
  
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    session.sendMessage(message);
  }
  
  @Scheduled(fixedRate = 5000)
  public void broadcast() throws Exception {
    for (String id : sessions.keySet()) {
      sessions.get(id).sendMessage(new TextMessage(new Date().toString()));
    }
  }
}
