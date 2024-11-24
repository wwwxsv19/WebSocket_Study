package mine.testcode.domain.chat.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.chat.ChatRoom;
import mine.testcode.domain.chat.presentation.dto.ChatMessage;
import mine.testcode.domain.chat.service.ChatService;
import mine.testcode.domain.chat.types.MessageType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatPublisher {
    private final ChatService chatService;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/Messaging")
    public void message(ChatMessage chatMessage) {
        log.info("Message : {}", chatMessage);

        if (MessageType.ENTER.equals(chatMessage.getMessageType())) {
            chatMessage.initMessage(chatMessage.getSenderId() + " 님이 채팅을 시작합니다!");
        }

        ChatRoom chatRoom = chatService.saveChatMessage(chatMessage);

        messagingTemplate.convertAndSend("/sub/chatRoom/" + chatMessage.getChatRoomId(), chatMessage);
    }
}
