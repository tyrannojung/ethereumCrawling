package kr.ne.abc.template.common.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;


// 연결과, 비연결시 실행되는 eventListener
@Component
@RequiredArgsConstructor
public class ChatEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ChatEventListener.class);
    
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
        
    }


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    	logger.info("web socket disconnection");
    	
    }
    
}