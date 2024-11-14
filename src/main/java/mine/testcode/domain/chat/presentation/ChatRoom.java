package mine.testcode.domain.chat.presentation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import mine.testcode.domain.chat.presentation.dto.ChatMessageDto;
import mine.testcode.domain.chat.service.ChatService;
import mine.testcode.global.handler.types.MessageType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table(name = "oring_chatroom")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class ChatRoom {
    @Id
    private String chatRoomId; // 채팅방 식별 아이디

    private String chatRoomName; // 채팅방 이름

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<WebSocketSession> sessions = new HashSet<>();

    public void init() {
        this.sessions = new HashSet<>();
    }

    public void handlerActions(WebSocketSession session, ChatMessageDto chatMessage, ChatService chatService) {
        this.init();

        if (chatMessage.getMessageType().equals(MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.initMessage(chatMessage.getSenderId() + "님이 입장하셨습니다");
        }

        sendMessage(chatMessage, chatService);
    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }
}
