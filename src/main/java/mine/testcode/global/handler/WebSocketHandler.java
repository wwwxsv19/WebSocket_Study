package mine.testcode.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.chat.ChatMessage;
import mine.testcode.domain.chat.ChatRoom;
import mine.testcode.domain.chat.service.ChatService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper; // payload -> ChatMessage
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("{}", payload);

        ChatMessage chatMessage = mapper.readValue(payload, ChatMessage.class);

        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getChatRoomId());

        chatRoom.handlerActions(session, chatMessage, chatService);
    }
}
