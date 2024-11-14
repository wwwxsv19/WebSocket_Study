package mine.testcode.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.chat.presentation.dto.ChatMessageDto;
import mine.testcode.domain.chat.presentation.ChatRoom;
import mine.testcode.domain.chat.service.ChatService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper; // payload -> ChatMessage
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("session : {}, payLoad : {}", session, payload);

        ChatMessageDto chatMessage = mapper.readValue(payload, ChatMessageDto.class);

        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getChatRoomId());

        chatRoom.handlerActions(session, chatMessage, chatService);
    }
}
