package mine.testcode.domain.chat.presentation;

import lombok.RequiredArgsConstructor;
import mine.testcode.domain.chat.presentation.dto.ChatMessage;
import mine.testcode.domain.chat.types.MessageType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatPublisher {
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(ChatMessage chatMessage) {
        if (MessageType.ENTER.equals(chatMessage.getMessageType())) {
            chatMessage.initMessage(chatMessage.getSenderId() + " 님이 채팅을 시작합니다!");
        }
        messagingTemplate.convertAndSend("/sub/chat/room" + chatMessage.getChatRoomId(), chatMessage);
    }
}
